package clubhouse.clubhouse.domain.form.service;

import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.club.repository.ClubRepository;
import clubhouse.clubhouse.domain.form.dto.RequestFormDto;
import clubhouse.clubhouse.domain.form.dto.RequestQuestionDto;
import clubhouse.clubhouse.domain.form.dto.ResponseForm;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;
import clubhouse.clubhouse.domain.form.repository.FormRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final QuestionService questionService;
//    private final QuestionRepository questionRepository;
    /**
     * clubService로 리팩토링해야함
     * ToDo
     */
    private final ClubRepository clubRepository;



    @Override
    public ResponseForm createForm(RequestFormDto formDto) {
        Optional<Club> club = clubRepository.findById(formDto.getClubId());

//        Club club = new Club();
//        clubRepository.save(club.get());

        Form form = Form.builder()
                .title(formDto.getTitle())
                .content(formDto.getContent())
                .club(club.get())
                .deadline(formDto.getDeadLine())
                .photoUrl(formDto.getPhotoUrl())
                .build();

        Form form1 = formRepository.save(form);// 공고 생성완료
        log.info("new form created");

        for (RequestQuestionDto quesContent :formDto.getQuesList()) {
            //            이 작업을 createQuestion에서 해야함.
            questionService.createQuestion(quesContent,form1);
        }

        ResponseForm responseForm = new ResponseForm();

        responseForm.setTitle(form.getTitle());
        responseForm.setContent(form.getContent());

        return responseForm;
    }
    
    
    

    @Override
    public Optional<Form> findById(Long formId) {
        Optional<Form> form = formRepository.findById(formId);
        return form;
    }


}
