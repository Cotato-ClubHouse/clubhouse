package clubhouse.clubhouse.domain.application.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApplyResponseDto {

    HttpStatus httpStatus;

    public ApplyResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
