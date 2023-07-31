package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.ApplyListRequestDto;
import clubhouse.clubhouse.domain.application.dto.ApplyListResponseDto;
import clubhouse.clubhouse.domain.application.dto.ApplyRequestDto;
import clubhouse.clubhouse.domain.application.dto.ApplyResponseDto;

public interface ApplicationService {
    ApplyResponseDto apply(ApplyRequestDto applyRequestDto) throws IllegalAccessException;

    ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto) throws IllegalAccessException;
}
