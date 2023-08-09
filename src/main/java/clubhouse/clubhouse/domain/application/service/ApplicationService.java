package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.request.*;
import clubhouse.clubhouse.domain.application.dto.response.ApplicationDetailResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.ApplicationEditDetailResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.ApplyListResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.MyPageResponseDto;
import org.springframework.security.core.Authentication;

public interface ApplicationService {
    void apply(ApplyRequestDto applyRequestDto, Authentication authentication) throws IllegalAccessException;

    void patchApply(ApplyRequestDto applyRequestDto, Long applicationId, Authentication authentication) throws IllegalAccessException;

    ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto, Long clubId ,Authentication authentication) throws IllegalAccessException;

    void changeIsPass(ApplyChangeIsPassRequestDto requestDto, Long clubId , Long applicationId, Authentication authentication);

    MyPageResponseDto getMyPage(Authentication authentication);

    ApplicationEditDetailResponseDto getApplicationEditDetail(ApplicationEditDetailRequestDto requestDto, ApplicationEditDetailResponseDto responseDto, Authentication authentication) throws IllegalAccessException;

    ApplicationDetailResponseDto getFormQuestion(ApplicationDetailRequestDto requestDto, ApplicationDetailResponseDto responseDto, Long clubId, Authentication authentication) throws IllegalAccessException;

    ApplicationEditDetailResponseDto getApplicationDetail(ApplicationEditDetailRequestDto requestDto, ApplicationEditDetailResponseDto responseDto,Long clubId, Authentication authentication) throws IllegalAccessException;
}
