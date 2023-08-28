package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.request.*;
import clubhouse.clubhouse.domain.application.dto.response.ApplicationDetailResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.ApplicationEditDetailResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.ApplyListResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.MyPageResponseDto;
import org.springframework.security.core.Authentication;

public interface ApplicationService {
    void apply(ApplyRequestDto applyRequestDto, Authentication authentication);

    void patchApply(ApplyRequestDto applyRequestDto, Long applicationId, Authentication authentication);

    ApplyListResponseDto getApplicationList(Long formId, Long clubId ,Authentication authentication);

    void changeIsPass(ApplyChangeIsPassRequestDto requestDto, Long clubId , Long applicationId, Authentication authentication);

    MyPageResponseDto getMyPage(Authentication authentication);

    ApplicationEditDetailResponseDto getApplicationEditDetail(Long applicationId, ApplicationEditDetailResponseDto responseDto, Authentication authentication);

    ApplicationDetailResponseDto getFormQuestion(Long formId, ApplicationDetailResponseDto responseDto, Long clubId, Authentication authentication);

    ApplicationEditDetailResponseDto getApplicationDetail(Long applicationId, ApplicationEditDetailResponseDto responseDto,Long clubId, Authentication authentication);
}
