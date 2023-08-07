package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.request.*;
import clubhouse.clubhouse.domain.application.dto.response.ApplicationDetailResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.ApplicationEditDetailResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.ApplyListResponseDto;
import clubhouse.clubhouse.domain.application.dto.response.MyPageResponseDto;

public interface ApplicationService {
    void apply(ApplyRequestDto applyRequestDto) throws IllegalAccessException;

    void patchApply(ApplyRequestDto applyRequestDto, Long applicationId) throws IllegalAccessException;

    ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto) throws IllegalAccessException;

    void changeIsPass(ApplyChangeIsPassRequestDto requestDto, Long clubId , Long applicationId);

    MyPageResponseDto getMyPage(MyPageRequestDto requestDto);

    ApplicationEditDetailResponseDto getApplicationEditDetail(ApplicationEditDetailRequestDto requestDto, ApplicationEditDetailResponseDto responseDto) throws IllegalAccessException;

    ApplicationDetailResponseDto getFormQuestion(ApplicationDetailRequestDto requestDto, ApplicationDetailResponseDto responseDto, Long clubId) throws IllegalAccessException;

    ApplicationEditDetailResponseDto getApplicationDetail(ApplicationEditDetailRequestDto requestDto, ApplicationEditDetailResponseDto responseDto) throws IllegalAccessException;
}
