package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.ApplyChangeIsPassRequestDto;
import clubhouse.clubhouse.domain.application.dto.ApplyListRequestDto;
import clubhouse.clubhouse.domain.application.dto.ApplyListResponseDto;
import clubhouse.clubhouse.domain.application.dto.ApplyRequestDto;
import org.springframework.http.HttpStatus;

public interface ApplicationService {
    void apply(ApplyRequestDto applyRequestDto) throws IllegalAccessException;

    void patchApply(ApplyRequestDto applyRequestDto, Long applicationId) throws IllegalAccessException;

    ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto) throws IllegalAccessException;

    void changeIsPass(ApplyChangeIsPassRequestDto requestDto,Long clubId ,Long applicationId);
}
