package clubhouse.clubhouse.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionManager {
	@ExceptionHandler(AppException.class)
	public ResponseEntity<?> illegalArgumentExceptionHandler(AppException e){
		return ResponseEntity.status(e.getErrorCode().getHttpStatus())
			.body(e.getErrorCode() + " " + e.getMessage());
	}
}
