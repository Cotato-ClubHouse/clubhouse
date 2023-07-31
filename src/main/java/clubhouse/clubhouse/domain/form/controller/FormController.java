package clubhouse.clubhouse.domain.form.controller;

import clubhouse.clubhouse.domain.form.dto.RequestFormDto;
import clubhouse.clubhouse.domain.form.dto.ResponseForm;
import clubhouse.clubhouse.domain.form.service.FormService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/forms")
public class FormController {

    private final FormService formService;


    @GetMapping("/check")
    public ResponseEntity check(HttpServletRequest request){
//        request.getRequestURL();
        return ResponseEntity.status(HttpStatus.OK).body(request.getRequestURL());

    }
    @PostMapping("/create")
    public ResponseEntity<ResponseForm> createForm(@RequestBody RequestFormDto formDto){


        //formDto 를 바탕으로 새 form을 생성하고 저장하는 api
        ResponseForm form = formService.createForm(formDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(form);
    }
}
