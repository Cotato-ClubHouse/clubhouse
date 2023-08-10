package clubhouse.clubhouse.domain.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	//기훈 만든 ErrorCode
	APPLICATION_EXIST(HttpStatus.CONFLICT,""),
	APPLICATION_NOTFOUND(HttpStatus.NOT_FOUND, ""),
	FORM_NOTFOUND(HttpStatus.NOT_FOUND, ""),
	FORM_CLOSED(HttpStatus.CONFLICT, ""),
	NOT_OWNER(HttpStatus.UNAUTHORIZED, ""),
	BLANK_EXIST(HttpStatus.CONFLICT, ""),


	
	//여기까지 남기훈 제작
	EMAIL_DUPLICATED(HttpStatus.CONFLICT, ""),
	EMAIL_NOTFOUND(HttpStatus.NOT_FOUND, ""),
	INVAILD_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
	MEMBER_NOTFOUND(HttpStatus.NOT_FOUND, "");

	private HttpStatus httpStatus;
	private String message;
}
