package clubhouse.clubhouse.domain.application.controller;

import clubhouse.clubhouse.domain.application.dto.ApplyListRequestDto;
import clubhouse.clubhouse.domain.application.dto.ApplyListResponseDto;
import clubhouse.clubhouse.domain.application.dto.ApplyRequestDto;
import clubhouse.clubhouse.domain.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplicationService applicationService;

    //답변 데이터가 들어왔을 때 answer 저장 + application 저장(남기훈)
    @PostMapping("/{club_id}/apply")
    public ResponseEntity<HttpStatus> saveAnswer(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyRequestDto requestDto) throws IllegalAccessException {

        applicationService.apply(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(HttpStatus.CREATED);
    }

    @PatchMapping("{club_id}/apply/{application_id}")
    public ResponseEntity<HttpStatus> patchAnswer(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyRequestDto requestDto,
            @PathVariable("application_id") Long applicationId) throws IllegalAccessException {
        applicationService.patchApply(requestDto,applicationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(HttpStatus.OK);
    }


    
    //공고 등록된 지원서들 Get(남기훈)
    @GetMapping("/{club_id}/applyList")
    public ResponseEntity<ApplyListResponseDto> getApplyList(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyListRequestDto requestDto) throws IllegalAccessException{

        ApplyListResponseDto responseDto = applicationService.getApplicationList(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto); //임시 ApplyListResponseDto
    }


}
