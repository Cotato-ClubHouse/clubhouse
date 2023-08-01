package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.*;
import clubhouse.clubhouse.domain.application.entity.Answer;
import clubhouse.clubhouse.domain.application.entity.Application;
import clubhouse.clubhouse.domain.application.repository.ApplicationRepository;
import clubhouse.clubhouse.domain.form.entity.Form;
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


    /**
     * TODO
     * 지원서 작성과 수정은 시간 비교해야함
     */
    
    
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
        
        Member member = memberRepository.findById(applyRequestDto.getMember_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 member가 없습니다"));//임시 데이터

        //이미 지원한 신청서가 있을 때는 에러
        Optional<Application> findAlreadyExistMember = applicationRepository.findByMemberAndForm(member, form);
        if (findAlreadyExistMember.isPresent()) {
            throw new IllegalArgumentException("이미 신청서가 존재합니다");
        }

        //값이 없는 답변이 있으면 예외 처리
        int index=1;
        for (String answer : answers) {
            if (answer.trim().equals("")) {
                throw new IllegalArgumentException("입력되지 않은 질문이 있습니다("+index+"번째 칠문)");
            }
            index++;
        }

        
        //답변이 다 있으면 지원서 만들기
        Application newApplication = applicationRepository.save(Application.createApplication(LocalDateTime.now(), member, form, false));

        int i=0;
        for (String answerContent : answers) {
            answerService.saveAnswer(newApplication, questions.get(i), answerContent);
            log.info("answer {}={}",questions.get(i), answerContent);
            i++;
        }

    }

    @Override
    @Transactional
    public void patchApply(ApplyRequestDto applyRequestDto, Long applicationId) throws IllegalAccessException {
        List<String> answers = applyRequestDto.getAnswers();

        //지원서 찾기
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지원서가 없습니다"));

        //멤버 찾기
        Member member = memberRepository.findById(applyRequestDto.getMember_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다"));

        //member 같은지 확인
        if (application.getMember() != member) {
            throw new IllegalAccessException("본인 지원서 외는 수정할 수 없습니다");
        }

        //값이 없는 답변이 있으면 예외 처리
        int index=1;
        for (String answer : answers) {
            if (answer.trim().equals("")) {
                throw new IllegalArgumentException("입력되지 않은 질문이 있습니다("+index+"번째 칠문)");
            }
            index++;
        }

        answerService.changeAnswer(application, answers)
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
        responseDto.setApplication_list(responseFormList);
        log.info("GET ApplyList Complete");

        return responseDto;
    }
}
