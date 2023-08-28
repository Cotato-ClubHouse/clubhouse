package clubhouse.clubhouse.domain.member.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	EMAIL_DUPLICATED(HttpStatus.CONFLICT, ""),
	EMAIL_NOTFOUND(HttpStatus.NOT_FOUND, ""),
	INVAILD_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
	MEMBER_NOTFOUND(HttpStatus.NOT_FOUND, ""),
	TOKEN_INVAILD(HttpStatus.UNAUTHORIZED, "");

	private HttpStatus httpStatus;
	private String message;
}
