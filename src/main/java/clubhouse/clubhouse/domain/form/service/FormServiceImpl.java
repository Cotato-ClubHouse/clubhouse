package clubhouse.clubhouse.domain.form.service;

import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.club.repository.ClubRepository;
import clubhouse.clubhouse.domain.form.dto.*;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.repository.FormRepository;
import clubhouse.clubhouse.global.S3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final S3Uploader s3Uploader;
    private final FormRepository formRepository;
    private final QuestionService questionService;
//    private final QuestionRepository questionRepository;
    /**
     * ToDo clubService로 리팩토링해야함
     */
    private final ClubRepository clubRepository;

    @Override
    public ResponseForm createForm(RequestFormDto formDto) throws IOException {
        Optional<Club> club = clubRepository.findById(formDto.getClubId());

        log.info("form service");
        String imageUrl = null;
        if(!(formDto.getFormImage().isEmpty())){
            imageUrl=s3Uploader.uploadFiles(formDto.getFormImage(),"form");
        }

        Form form = Form.builder()
                .title(formDto.getTitle())
                .content(formDto.getContent())
                .club(club.get())
                .deadline(formDto.getDeadLine())
                .photoUrl(imageUrl)
                .build();

        Form form1 = formRepository.save(form);// 공고 생성완료
        log.info("new form created");

        for (RequestQuestionDto quesContent :formDto.getQuesList()) {
            questionService.createQuestion(quesContent,form1);
        }

        ResponseForm responseForm = ResponseForm.builder()
                .content(form1.getContent())
                .title(form1.getTitle())
                .build();

        return responseForm;
    }
    
    
    

    @Override
    public Optional<Form> findById(Long formId) {
        Optional<Form> form = formRepository.findById(formId);
        return form;
    }

    @Override
    public List<ResponseAllForm> getAllFormInfo() {
        List<Form> allForms = formRepository.findAll();
        List<ResponseAllForm> result = new ArrayList<>();

        for(Form allForm : allForms){
            LocalDateTime currentDay = LocalDateTime.now();
            LocalDateTime endDay = allForm.getDeadline();

            Long remainDays = ChronoUnit.DAYS.between(currentDay,endDay);

            ResponseAllForm responseAllForm = ResponseAllForm.builder()
                    .formStatus(allForm.getFormStatus())
                    .categoryName(allForm.getClub().getCategoryName())
                    .clubName(allForm.getClub().getName())
                    .remainDay(remainDays)
                    .photoUrl(allForm.getPhotoUrl())
                    .content(allForm.getContent())
                    .build();
            result.add(responseAllForm);
        }
        return result;
    }

    @Override
    public ResponseFormDetails getFormDetails(Long formId) {
        Form form = formRepository.findById(formId).get();
        Optional<Club> club = clubRepository.findById(form.getClub().getId());

        log.info("formPhotoUrl {}",form.getPhotoUrl());

        LocalDateTime currentDay = LocalDateTime.now();
        LocalDateTime endDay = form.getDeadline();

        Long remainDays = ChronoUnit.DAYS.between(currentDay,endDay);

        ResponseFormDetails result = ResponseFormDetails.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .deadLine(form.getDeadline())
                .photoUrl(form.getPhotoUrl())
                .clubName(club.get().getName())
                .categoryName(club.get().getCategoryName())
                .remainDays(remainDays)
                .build();

        return result;
    }

    @Override
    public void deleteForm(Long formId) {

        questionService.deleteAllQuesByFormId(formId);
        formRepository.deleteById(formId);

    }
}
