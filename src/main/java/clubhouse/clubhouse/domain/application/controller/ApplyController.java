package clubhouse.clubhouse.domain.application.controller;

import clubhouse.clubhouse.domain.application.dto.*;
import clubhouse.clubhouse.domain.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApplyResponseDto> saveAnswer(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyRequestDto requestDto) throws IllegalAccessException {

        applicationService.apply(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApplyResponseDto(HttpStatus.CREATED));
    }

    //공고 수정 Patch(남기훈)
    @PatchMapping("{club_id}/apply/{application_id}")
    public ResponseEntity<ApplyResponseDto> patchAnswer(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyRequestDto requestDto,
            @PathVariable("application_id") Long applicationId) throws IllegalAccessException {
        applicationService.patchApply(requestDto, applicationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApplyResponseDto(HttpStatus.OK));
    }

    @PatchMapping("{club_id}/applyList/{application_id}")
    public ResponseEntity<ApplyResponseDto> changeIsPass(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyChangeIsPassRequestDto requestDto,
            @PathVariable("application_id") Long applicationId) {

        applicationService.changeIsPass(requestDto, clubId, applicationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApplyResponseDto(HttpStatus.OK));
    }


    //공고 등록된 지원서들 Get(남기훈)
    @GetMapping("/{club_id}/applyList")
    public ResponseEntity<ApplyListResponseDto> getApplyList(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyListRequestDto requestDto) throws IllegalAccessException {

        ApplyListResponseDto responseDto = applicationService.getApplicationList(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }


    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> getUserApplyList(
            @RequestBody MyPageRequestDto requestDto) {
        MyPageResponseDto responseDto = applicationService.getMyPage(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    //사용자가 수정하는 지원서 세부내용
    @GetMapping("/mypage/getApplicationEditDetail")
    public ResponseEntity<ApplicationDetailResponseDto> getApplicationDetail(
            @RequestBody ApplicationDetailRequestDto requestDto) throws IllegalAccessException {
        ApplicationDetailResponseDto responseDto = new ApplicationDetailResponseDto();
        responseDto.setEditable(true);
        responseDto = applicationService.getApplicationDetail(requestDto, responseDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
