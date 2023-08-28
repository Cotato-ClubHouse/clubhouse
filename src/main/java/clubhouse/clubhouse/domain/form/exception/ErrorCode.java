package clubhouse.clubhouse.domain.form.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    IMAGE_PROCESSING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"이미지 처리에 실패했습니다."),//변환 실패
    FORM_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 공고를 찾을 수 없습니다."),
    CLUB_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 공고를 등록한 클럽을 찾을 수 없습니다."),
    CANNOT_PATCH(HttpStatus.NOT_ACCEPTABLE,"해당 항목은 수정할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
