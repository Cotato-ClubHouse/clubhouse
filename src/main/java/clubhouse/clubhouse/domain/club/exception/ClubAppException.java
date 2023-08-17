package clubhouse.clubhouse.domain.club.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClubAppException extends IllegalArgumentException{
    private ErrorCode errorCode;
    private String message;
}
