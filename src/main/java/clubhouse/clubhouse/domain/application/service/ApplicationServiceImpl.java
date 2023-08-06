package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.*;
import clubhouse.clubhouse.domain.application.entity.Answer;
import clubhouse.clubhouse.domain.application.entity.Application;
import clubhouse.clubhouse.domain.application.repository.ApplicationRepository;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.FormStatus;
import clubhouse.clubhouse.domain.form.entity.Question;
import clubhouse.clubhouse.domain.form.service.FormService;
import clubhouse.clubhouse.domain.member.MemberRepository;
import clubhouse.clubhouse.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    
    //지원서 제출(사용자)
    @Override
    @Transactional
    public void apply(ApplyRequestDto applyRequestDto) throws IllegalAccessException {
        Long formId = applyRequestDto.getForm_id();
        List<Question> questions = formService.findAllQuestions(formId); //여기서 순서 맞춰줘야함
        List<String> answers = applyRequestDto.getAnswers();
        
        //해당 form 가져오기
        Form form = formService.findById(formId)
                .orElseThrow(() -> new IllegalArgumentException("form이 없습니다"));

        checkFormStatusClose(form);

        Member member = findMemberById(applyRequestDto.getMember_id());

        //이미 지원한 신청서가 있을 때는 에러
        Optional<Application> findAlreadyExistMember = applicationRepository.findByMemberAndForm(member, form);
        if (findAlreadyExistMember.isPresent()) {
            throw new IllegalArgumentException("이미 신청서가 존재합니다");
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
    public void patchApply(ApplyRequestDto applyRequestDto, Long applicationId) throws IllegalAccessException {
        List<String> answers = applyRequestDto.getAnswers();

        //지원서 찾기
        Application application = findApplicationById(applicationId);
        
        //이미 지난 공고인지 확인
        checkFormStatusClose(application.getForm());

        //멤버 찾기
        Member member = findMemberById(applyRequestDto.getMember_id());

        //member 같은지 확인
        if (application.getMember() != member) {
            throw new IllegalAccessException("본인 지원서 외는 수정할 수 없습니다");
        }

        //값이 없는 답변이 있으면 예외 처리
        checkAllAnswerInput(answers);

        answerService.changeAnswer(application, answers);
    }


    //IsPass 바꾸기
    @Override
    @Transactional
    public void changeIsPass(ApplyChangeIsPassRequestDto requestDto,Long clubId,Long applicationId) {
        /**
         * member_id가 클럽의 회장인지 확인해야한다 TODO
         */

        Application application = findApplicationById(applicationId);
        boolean isPass = Boolean.parseBoolean(requestDto.getIs_pass());
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
    public ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto) throws IllegalAccessException {
        Long formId = requestDto.getForm_id();
        log.info("getApplicationList Start");

        Form form = formService.findById(formId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 form이 없습니다"));

        List<Application> applyList = applicationRepository.findAllByForm(form); //0이상의 지원서

        ApplyListResponseDto responseDto = new ApplyListResponseDto();
        responseDto.setHttpStatus(HttpStatus.OK);


        List<ApplyListResponseForm> responseFormList = new ArrayList<>();
        for (Application apply : applyList){
            Member member = apply.getMember();
            String name = member.getName();
            Long age = member.getAge();
            String univ = member.getUniv();

            ApplyListResponseForm applyForm = new ApplyListResponseForm(name,age,univ,false, apply.getId());

            responseFormList.add(applyForm);

        }
        responseDto.setFormName(form.getTitle());
        responseDto.setApplication_list(responseFormList);
        log.info("GET ApplyList Complete");

        return responseDto;
    }

    //myPage 정보 가져오기
    @Override
    public MyPageResponseDto getMyPage(MyPageRequestDto requestDto) {

        Member member = findMemberByEmail(requestDto.getMemberEmail());

        MyPageResponseDto responseDto = new MyPageResponseDto();
        responseDto.setMemberName(member.getName());
        responseDto.setSimpleIntroduction(member.getUniv()); //학과 추가돼면 바꿔야함 TODO

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
    public ApplicationDetailResponseDto getApplicationDetail(ApplicationDetailRequestDto requestDto, ApplicationDetailResponseDto responseDto) throws IllegalAccessException {
        Application application = findApplicationById(requestDto.getApplicationId());
        Form form = application.getForm();
        Member member = findMemberByEmail(requestDto.getMemberEmail());

        if (application.getMember() != member) {
            throw new IllegalAccessException("해당 지원서의 작성자가 아닙니다");
        }

        responseDto.setFormName(form.getTitle());

        List<Question> questionList = formService.findAllQuestions(form.getId());

        List<QNA> qnaList = new ArrayList<>();
        for (Question question : questionList) {
            linkQuestionAndAnswer(application, question,qnaList);
        }
        responseDto.setQnaList(qnaList);

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

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 잘못됐습니다"));
    }

    private Member findMemberByEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail)
                .orElseThrow(()-> new IllegalArgumentException("회원정보가 잘못됐습니다"));
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
                throw new IllegalArgumentException("입력되지 않은 질문이 있습니다("+index+"번째 칠문)");
            }
            index++;
        }
    }

    private Application findApplicationById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지원서가 없습니다"));
        return application;
    }


    private static void checkFormStatusClose(Form form) throws IllegalAccessException {
        if (form.getFormStatus()== FormStatus.CLOSING) {
            throw new IllegalAccessException("종료됐습니다");
        }
    }
}