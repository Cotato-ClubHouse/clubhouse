package clubhouse.clubhouse.domain.form.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FormExceptionHandler {

    @ExceptionHandler(FormAppException.class)
    public ResponseEntity<?> formCustomException(FormAppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode()+" "+e.getErrorCode().getMessage());
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<?> imageExceptionHandler(ImageException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode()+" "+e.getErrorCode().getMessage());
    }
}
