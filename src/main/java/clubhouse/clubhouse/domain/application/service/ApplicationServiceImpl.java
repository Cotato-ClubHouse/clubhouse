package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.form.ApplyListResponseForm;
import clubhouse.clubhouse.domain.application.dto.form.QNA;
import clubhouse.clubhouse.domain.application.dto.form.UserApplyListForm;
import clubhouse.clubhouse.domain.application.dto.form.UserInfoForm;
import clubhouse.clubhouse.domain.application.dto.request.*;
import clubhouse.clubhouse.domain.application.dto.response.ApplicationDetailResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.ApplicationEditDetailResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.ApplyListResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.MyPageResponseDto;
import clubhouse.clubhouse.domain.application.entity.Answer;
import clubhouse.clubhouse.domain.application.entity.Application;
import clubhouse.clubhouse.domain.application.exception.ApplicationAppException;
import clubhouse.clubhouse.domain.application.exception.ErrorCode;
import clubhouse.clubhouse.domain.application.repository.ApplicationRepository;
import clubhouse.clubhouse.domain.club.service.ClubService;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.FormStatus;
import clubhouse.clubhouse.domain.form.entity.Question;
import clubhouse.clubhouse.domain.form.service.FormService;
import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final AnswerService answerService;
    private final ApplicationRepository applicationRepository;

    private final FormService formService;

    private final MemberRepository memberRepository;

    private final ClubService clubService;

    
    //지원서 제출(사용자)
    @Override
    @Transactional
    public void apply(ApplyRequestDto applyRequestDto, Authentication authentication){
        Long formId = applyRequestDto.getFormId();
        List<Question> questions = formService.findAllQuestions(formId); //여기서 순서 맞춰줘야함
        List<String> answers = applyRequestDto.getAnswers();
        
        //해당 form 가져오기
        Form form = findFormById(formId);

        checkFormStatusClose(form);

        Member member = findMemberByEmail(authentication.getName());

        //이미 지원한 신청서가 있을 때는 에러
        Optional<Application> findAlreadyExistMember = applicationRepository.findByMemberAndForm(member, form);
        if (findAlreadyExistMember.isPresent()) {
            throw new ApplicationAppException(ErrorCode.APPLICATION_EXIST, "이미 신청서가 존재합니다");
        }

        //값이 없는 답변이 있으면 예외 처리
        checkAllAnswerInput(answers); //모든 질문에 대한 답이 필수인지에 대해 결정나면 바꿔야 할수도 있음 TODO(현재는 모든 값 필수)

        //답변이 다 있으면 지원서 만들기
        Application newApplication = applicationRepository.save(Application.createApplication(LocalDateTime.now(), member, form, false));

        //지원서에 answer 저장
        makeAnswer(questions, answers, newApplication);

    }


    //지원서 수정
    @Override
    @Transactional
    public void patchApply(ApplyRequestDto applyRequestDto, Long applicationId, Authentication authentication) {
        List<String> answers = applyRequestDto.getAnswers();

        //지원서 찾기
        Application application = findApplicationById(applicationId);
        
        //이미 지난 공고인지 확인
        checkFormStatusClose(application.getForm());

        //멤버 찾기
        Member member = findMemberByEmail(authentication.getName());

        //member 같은지 확인
        if (application.getMember() != member) {
            throw new ApplicationAppException(ErrorCode.NOT_OWNER, "본인 지원서 외는 수정할 수 없습니다");
        }

        //값이 없는 답변이 있으면 예외 처리
        checkAllAnswerInput(answers);

        answerService.changeAnswer(application, answers);
    }


    //IsPass 바꾸기
    @Override
    @Transactional
    public void changeIsPass(ApplyChangeIsPassRequestDto requestDto, Long clubId, Long applicationId, Authentication authentication) {
        isAdmin(clubId, authentication);

        Application application = findApplicationById(applicationId);
        boolean isPass = Boolean.parseBoolean(requestDto.getIsPass());
        Application changedApplication = application.changeIsPass(isPass);

        /**
         * true일때 club에 멤버 넣기, false일때 멤버빼기 TODO
         */
        //clubService.changeMemberStatus(application.getMember(), clubId, isPass);

        applicationRepository.save(changedApplication);
    }



    //리스트 출력
    @Override
    @Transactional
    public ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto, Long clubId,Authentication authentication) {
        isAdmin(clubId, authentication);

        Long formId = requestDto.getFormId();
        log.info("getApplicationList Start");

        Form form = findFormById(formId);

        List<Application> applyList = applicationRepository.findAllByForm(form); //0이상의 지원서

        ApplyListResponseDto responseDto = new ApplyListResponseDto();
        responseDto.setHttpStatus(HttpStatus.OK);


        List<ApplyListResponseForm> responseFormList = new ArrayList<>();
        for (Application apply : applyList){
            Member member = apply.getMember();
            String name = member.getName();
            int age = member.getAge();
            String univ = member.getUniv();

            ApplyListResponseForm applyForm = new ApplyListResponseForm(name,age,univ,false, apply.getId());

            responseFormList.add(applyForm);

        }
        responseDto.setFormName(form.getTitle());
        responseDto.setApplicationList(responseFormList);
        log.info("GET ApplyList Complete");

        return responseDto;
    }



    //myPage 정보 가져오기
    @Override
    public MyPageResponseDto getMyPage(Authentication authentication) {

        Member member = findMemberByEmail(authentication.getName());

        MyPageResponseDto responseDto = new MyPageResponseDto();
        responseDto.setMemberName(member.getName());
        responseDto.setSimpleIntroduction(member.getUniv()+" "+member.getMajor());

        List<Application> applicationList = applicationRepository.findAllByMember(member);

        List<UserApplyListForm> applyListFormList = new ArrayList<>();
        for (Application application : applicationList) {
            Form form = application.getForm();
            String clubName = form.getClub().getName();
            applyListFormList.add(new UserApplyListForm(clubName, application.getId()));
        }

        responseDto.setApplicationList(applyListFormList);

        return responseDto;
    }

    @Override
    @Transactional
    public ApplicationEditDetailResponseDto getApplicationEditDetail(ApplicationEditDetailRequestDto requestDto, ApplicationEditDetailResponseDto responseDto, Authentication authentication){
        Application application = findApplicationById(requestDto.getApplicationId());
        Form form = application.getForm();
        Member member = findMemberByEmail(authentication.getName());

        if (application.getMember() != member) {
            throw new ApplicationAppException(ErrorCode.NOT_OWNER, "해당 지원서의 작성자가 아닙니다");
        }

        setQNALink(responseDto, application, form);

        return responseDto;
    }

    //동아리 회장이 신청서의 세부목록을 보여준다
    @Override
    public ApplicationEditDetailResponseDto getApplicationDetail(ApplicationEditDetailRequestDto requestDto, ApplicationEditDetailResponseDto responseDto, Long clubId,Authentication authentication){
        isAdmin(clubId, authentication);

        Application application = findApplicationById(requestDto.getApplicationId());
        Member member = application.getMember();

        //회원 정보 작성
        responseDto.setUserInfoForm(setUserInfoForm(member));
        
        //QNA 링크하기
        setQNALink(responseDto,application,application.getForm());

        responseDto.setHttpStatus(HttpStatus.OK);

        return responseDto;

    }

    //질문 리스트 출력
    @Override
    public ApplicationDetailResponseDto getFormQuestion(ApplicationDetailRequestDto requestDto, ApplicationDetailResponseDto responseDto, Long clubId, Authentication authentication) {
        //멤버가 클럽에 속해있는지 확인해야한다. TODO

        List<String> questionContentList = new ArrayList<>();
        List<Question> questionList = formService.findAllQuestions(requestDto.getFormId());
        responseDto.setFormName(findFormById(requestDto.getFormId()).getTitle());

        for (Question q : questionList) {
            questionContentList.add(q.getContents());
        }
        responseDto.setQeustionList(questionContentList);
        responseDto.setFormId(requestDto.getFormId());
        responseDto.setHttpStatus(HttpStatus.OK);


        return responseDto;
    }

    private void linkQuestionAndAnswer(Application application, Question question, List<QNA> qnaList) {
        QNA qna = new QNA();
        qna.setQuestion(question.getContents());
        Optional<Answer> answerOptional = answerService.findAnswerWithQuestion(application, question);
        if (answerOptional.isEmpty()) {
            qna.setAnswer("");
        } else {
            qna.setAnswer(answerOptional.get().getContents());
        }
        qnaList.add(qna);
    }

    private Member findMemberByEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail)
                .orElseThrow(()-> new ApplicationAppException(ErrorCode.MEMBER_NOTFOUND,"멤버가 존재하지 않습니다"));
    }

    private void makeAnswer(List<Question> questions, List<String> answers, Application newApplication) {
        int i=0;
        for (String answerContent : answers) {
            answerService.saveAnswer(newApplication, questions.get(i), answerContent);
            log.info("answer {}={}", questions.get(i), answerContent);
            i++;
        }
    }

    private void checkAllAnswerInput(List<String> answers) {
        int index=1;
        for (String answer : answers) {
            if (answer.trim().equals("")) {
                throw new ApplicationAppException(ErrorCode.BLANK_EXIST, "입력되지 않은 질문이 있습니다(" + index + "번째 칠문)");
            }
            index++;
        }
    }

    private Application findApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationAppException(ErrorCode.APPLICATION_NOTFOUND, "지원서가 존재하지 않습니다"));
    }


    private static void checkFormStatusClose(Form form){
        if (form.getFormStatus()== FormStatus.CLOSING) {
            throw new ApplicationAppException(ErrorCode.FORM_CLOSED,"지원시간이 종료됐습니다");
        }
    }

    private UserInfoForm setUserInfoForm(Member member) {
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.setAge(member.getAge());
        userInfoForm.setName(member.getName());
        userInfoForm.setGender(member.getGender());
        userInfoForm.setPhoneNum(member.getPhone());
        userInfoForm.setUnivAndMajor(member.getUniv()+" "+member.getMajor());

        return userInfoForm;
    }

    private Form findFormById(Long formId) {
        return formService.findById(formId)
                .orElseThrow(() -> new ApplicationAppException(ErrorCode.FORM_NOTFOUND,"해당 공고를 찾을 수 없습니다"));
    }

    private void setQNALink(ApplicationEditDetailResponseDto responseDto, Application application, Form form) {
        responseDto.setFormName(form.getTitle());

        List<Question> questionList = formService.findAllQuestions(form.getId());

        List<QNA> qnaList = new ArrayList<>();
        for (Question question : questionList) {
            linkQuestionAndAnswer(application, question,qnaList);
        }
        responseDto.setQnaList(qnaList);
        responseDto.setHttpStatus(HttpStatus.OK);
    }

    //ToDO 수정해야함
    private void isAdmin(Long clubId, Authentication authentication) {
        //boolean isAdmin = clubService.isAdmin(clubId, authentication.getName());
        boolean isAdmin=false; //바꿔야함
        if(!isAdmin) {
            //throw new exception 
        }
    }
}