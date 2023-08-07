package clubhouse.clubhouse.domain.form.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    IMAGE_PROCESSING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"이미지 처리에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
