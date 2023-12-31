package clubhouse.clubhouse.domain.form.controller;

import clubhouse.clubhouse.domain.form.dto.*;
import clubhouse.clubhouse.domain.form.exception.ImageException;
import clubhouse.clubhouse.domain.form.service.FormService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/forms")
@CrossOrigin
public class FormController {

    private final FormService formService;


    @GetMapping("/check")
    public ResponseEntity check(HttpServletRequest request){
//        request.getRequestURL();
        return ResponseEntity.status(HttpStatus.OK).body(request.getRequestURL());

    }

    @GetMapping("")
    public ResponseEntity<List<ResponseAllForm>> getAllForms(){

        List<ResponseAllForm> result = formService.getAllFormInfo();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Todo 예외처리 리스트
     * 해당 공고 중복확인
     * 이미지 업로드 하지 않는 문제 
     */
    @PostMapping(value = "/create",consumes = "multipart/form-data")
    public ResponseEntity<ResponseForm> createForm(@ModelAttribute RequestFormDto formDto) throws ImageException {
        //formDto 를 바탕으로 새 form을 생성하고 저장하는 api
        ResponseForm form = formService.createForm(formDto);
        log.info("form controller");
        return ResponseEntity.status(HttpStatus.CREATED).body(form);
    }

    @GetMapping("/{formId}")
    public ResponseEntity<ResponseFormDetails> getFormDetails(@PathVariable("formId") Long formId){

        ResponseFormDetails result = formService.getFormDetails(formId);

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }


    @DeleteMapping("/{formId}")
    public ResponseEntity<Long> deleteForm(@PathVariable("formId") Long formId){
        formService.deleteForm(formId);

        return ResponseEntity.status(HttpStatus.OK).body(formId);
    }

    @PatchMapping("/{formId}")
    public ResponseEntity<ResponsePatchForm> patchForm(@PathVariable("formId")Long formId,
                                                       @RequestBody RequestPatchForm requestPatchForm){
        ResponsePatchForm responsePatchForm = formService.patchForm(formId, requestPatchForm);
        return ResponseEntity.status(HttpStatus.OK).body(responsePatchForm);
    }


}
