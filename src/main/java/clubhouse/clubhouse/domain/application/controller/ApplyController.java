package clubhouse.clubhouse.domain.application.controller;

import clubhouse.clubhouse.domain.application.dto.request.*;
import clubhouse.clubhouse.domain.application.dto.response.*;
import clubhouse.clubhouse.domain.application.service.ApplicationService;
import clubhouse.clubhouse.domain.club.dto.ClubListDto;
import clubhouse.clubhouse.domain.club.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@CrossOrigin
public class ApplyController {

    private final ApplicationService applicationService;

    private final MypageService mypageService;

    //처음 답변 작성하는 폼
    @GetMapping("/{club_id}/{form_id}/apply")
    public ResponseEntity<ApplicationDetailResponseDto> getApplicationDetail(
            @PathVariable("form_id") Long formId,
            @PathVariable("club_id") Long clubId,
            Authentication authentication){
        ApplicationDetailResponseDto responseDto = new ApplicationDetailResponseDto();
        responseDto = applicationService.getFormQuestion(formId, responseDto, clubId, authentication);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    //답변 데이터가 들어왔을 때 answer 저장 + application 저장(남기훈)
    @PostMapping("/{club_id}/apply")
    public ResponseEntity<String> saveAnswer(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyRequestDto requestDto,
            Authentication authentication){

        applicationService.apply(requestDto, authentication);

        return ResponseEntity.ok("application created");
    }

    //공고 수정하는 지원서 세부내용 GET
    @GetMapping("/mypage/{application_id}/getApplicationEditDetail")
    public ResponseEntity<ApplicationEditDetailResponseDto> getApplicationEditDetail(
            @PathVariable("application_id") Long applicationId,
            Authentication authentication){
        ApplicationEditDetailResponseDto responseDto = new ApplicationEditDetailResponseDto();
        responseDto.setEditable(true);
        responseDto = applicationService.getApplicationEditDetail(applicationId, responseDto, authentication);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    //지원서 수정 Patch(남기훈)
    @PatchMapping("{club_id}/apply/{application_id}")
    public ResponseEntity<String> patchAnswer(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyRequestDto requestDto,
            @PathVariable("application_id") Long applicationId,
            Authentication authentication){
        applicationService.patchApply(requestDto, applicationId,authentication);

        return ResponseEntity.ok("application fixed");
    }



    //지원자의 상태를 변경
    @PatchMapping("{club_id}/applyList/{application_id}")
    public ResponseEntity<String> changeIsPass(
            @PathVariable("club_id") Long clubId,
            @RequestBody ApplyChangeIsPassRequestDto requestDto,
            @PathVariable("application_id") Long applicationId,
            Authentication authentication) {

        applicationService.changeIsPass(requestDto, clubId, applicationId, authentication);

        return ResponseEntity.ok("status changed");
    }


    //공고 등록된 전체 지원서들 Get(남기훈)
    @GetMapping("/{club_id}/{form_id}/applyList")
    public ResponseEntity<ApplyListResponseDto> getApplyList(
            Authentication authentication,
            @PathVariable("club_id") Long clubId,
            @PathVariable("form_id") Long form_id){

        ApplyListResponseDto responseDto = applicationService.getApplicationList(form_id,clubId ,authentication);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    //동아리 회장이 application 세부사항 보기
    @GetMapping("/{club_id}/{application_id}/applyDetail")
    public ResponseEntity<ApplicationEditDetailResponseDto> getApplyList(
            @PathVariable("club_id") Long clubId,
            @PathVariable("application_id") Long applicationId,
            Authentication authentication){
        ApplicationEditDetailResponseDto responseDto = new ApplicationEditDetailResponseDto();
        responseDto.setEditable(false);
        applicationService.getApplicationDetail(applicationId, responseDto, clubId,authentication);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    //My Page
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> getUserApplyList(
            Authentication authentication){
        MyPageResponseDto responseDto = applicationService.getMyPage(authentication);
        ClubListDto clubList = mypageService.getClubList();
        responseDto.setClubList(clubList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

}
