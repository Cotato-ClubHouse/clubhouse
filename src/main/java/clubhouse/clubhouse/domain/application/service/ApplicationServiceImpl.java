package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.*;
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
     * 나중에 MemberService 완성되면 만들어줘야함 TODO
     */
    //private final MemberService memberService;

    
    //지원서 제출(사용자)
    @Override
    public ApplyResponseDto apply(ApplyRequestDto applyRequestDto) throws IllegalAccessException {
        Long formId = applyRequestDto.getForm_id();
        /**
         * clubID에 해당하는 질문 목록을 찾아야함 COMPLETE
         * return List<Question>
         */
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
                throw new IllegalAccessException("입력되지 않은 질문이 있습니다("+index+"번째 칠문)");
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

        /**
         * TODO
         */
        return new ApplyResponseDto(); //임시 returnDto
    }


    //리스트 출력
    @Override
    public ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto) throws IllegalAccessException {
        Long formId = requestDto.getForm_id();
        log.info("getApplicationList Start");

        Form form = formService.findById(formId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 form이 없습니다"));

        List<Application> applyList = applicationRepository.findAllByForm(form); //0이상의 지원서

        ApplyListResponseDto responseDto = new ApplyListResponseDto();
        responseDto.setHttpStatus(HttpStatus.OK);

        /**
         * findMemberBy id TODO
         */

        List<ApplyListResponseForm> responseFormList = new ArrayList<>();
        for (Application apply : applyList){
            Member member = apply.getMember();
            String name = member.getName(); //member.service하기 전까지는 못함 TODO
            Long age = member.getAge();
            String univ = member.getUniv();
            /**
             * 임시데이터임 나중에 memberService개발 후 바꿔줘야함 TODO
             *  ApplyListResponseForm applyForm = new ApplyListResponseForm(name,age,univ,false, apply.getId());
             */
            ApplyListResponseForm applyForm = new ApplyListResponseForm(name,age,univ,false, apply.getId());
            //ApplyListResponseForm applyForm = new ApplyListResponseForm(name,age,univ,false, apply.getId()); 임시 데이터

            responseFormList.add(applyForm);

        }
        responseDto.setApplication_list(responseFormList);
        log.info("GET ApplyList Complete");

        return responseDto;
    }
}
