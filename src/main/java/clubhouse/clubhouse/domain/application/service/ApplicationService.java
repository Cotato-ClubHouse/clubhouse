package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.dto.*;
import org.springframework.http.HttpStatus;

public interface ApplicationService {
    void apply(ApplyRequestDto applyRequestDto) throws IllegalAccessException;

    void patchApply(ApplyRequestDto applyRequestDto, Long applicationId) throws IllegalAccessException;

    ApplyListResponseDto getApplicationList(ApplyListRequestDto requestDto) throws IllegalAccessException;

    void changeIsPass(ApplyChangeIsPassRequestDto requestDto,Long clubId ,Long applicationId);

    MyPageResponseDto getMyPage(MyPageRequestDto requestDto);
}
