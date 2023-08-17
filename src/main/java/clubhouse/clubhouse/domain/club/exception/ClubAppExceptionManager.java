package clubhouse.clubhouse.domain.club.exception;

import clubhouse.clubhouse.domain.member.exception.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClubAppExceptionManager {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(AppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode() + " " + e.getMessage());
    }
}
