package clubhouse.clubhouse.domain.form.service;

import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.club.repository.ClubRepository;
import clubhouse.clubhouse.domain.form.dto.*;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;
import clubhouse.clubhouse.domain.form.exception.ErrorCode;
import clubhouse.clubhouse.domain.form.exception.FormAppException;
import clubhouse.clubhouse.domain.form.exception.ImageException;
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
@Transactional(readOnly = true)
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
    @Transactional
    public ResponseForm createForm(RequestFormDto formDto) throws ImageException {
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
        Form form = formRepository.findById(formId)
                .orElseThrow(()->new FormAppException(ErrorCode.FORM_NOT_FOUND));

        Club club = clubRepository.findById(form.getClub().getId())
                .orElseThrow(()->new FormAppException(ErrorCode.CLUB_NOT_FOUND));

        log.info("formPhotoUrl {}",form.getPhotoUrl());

        LocalDateTime currentDay = LocalDateTime.now();
        LocalDateTime endDay = form.getDeadline();

        Long remainDays = ChronoUnit.DAYS.between(currentDay,endDay);

        return ResponseFormDetails.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .deadLine(form.getDeadline())
                .photoUrl(form.getPhotoUrl())
                .clubName(club.getName())
                .categoryName(club.getCategoryName())
                .remainDays(remainDays)
                .clubIntro(club.getIntro())
                .build();
    }

    @Override
    @Transactional
    public void deleteForm(Long formId) {

        questionService.deleteAllQuesByFormId(formId);
        formRepository.deleteById(formId);


    }

    @Override
    @Transactional
    public ResponsePatchForm patchForm(Long formId, RequestPatchForm requestPatchForm) {
        Form formValue = formRepository.findById(formId)
                .orElseThrow(()->new FormAppException(ErrorCode.FORM_NOT_FOUND));

        
        if(requestPatchForm.getTitle()==null){
            requestPatchForm.setTitle(formValue.getTitle());
        }
        if(requestPatchForm.getContent()==null){
            requestPatchForm.setContent(formValue.getContent());
        }
        if(requestPatchForm.getFormStatus()==null){
            requestPatchForm.setFormStatus(formValue.getFormStatus());
        }

        formValue.update(requestPatchForm.getTitle(),requestPatchForm.getContent(),requestPatchForm.getFormStatus());

        //반환값만들기

        return ResponsePatchForm.builder()
                .title(formValue.getTitle())
                .content(formValue.getContent())
                .formStatus(formValue.getFormStatus())
                .build();
    }

    @Override
    public List<Question> findAllQuestions(Long formId) {
        return questionService.findAllQuesByFormId(formId);
    }
}
