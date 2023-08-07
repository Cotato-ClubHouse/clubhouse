package clubhouse.clubhouse.domain.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends IllegalArgumentException {
	private ErrorCode errorCode;
	private String message;
}
