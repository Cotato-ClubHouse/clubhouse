package clubhouse.clubhouse.domain.member.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	EMAIL_DUPLICATED(HttpStatus.CONFLICT, "");

	private HttpStatus httpStatus;
	private String message;
}
