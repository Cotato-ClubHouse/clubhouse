package clubhouse.clubhouse.domain.form.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FormAppException extends RuntimeException  {
    ErrorCode errorCode;
//    String message;
}
