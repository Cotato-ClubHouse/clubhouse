package clubhouse.clubhouse.domain.application.dto;

import clubhouse.clubhouse.domain.application.entity.Application;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApplyResponseDto {

    HttpStatus httpStatus;

    public ApplyResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
