package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.ApplyListRequestDto;
import clubhouse.clubhouse.domain.application.dto.ApplyListResponseDto;
import clubhouse.clubhouse.domain.application.dto.ApplyRequestDto;

public interface ApplicationService {
    void apply(ApplyRequestDto applyRequestDto) throws IllegalAccessException;

    void patchApply(ApplyRequestDto applyRequestDto, Long applicationId) throws IllegalAccessException;

    ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto) throws IllegalAccessException;
}
