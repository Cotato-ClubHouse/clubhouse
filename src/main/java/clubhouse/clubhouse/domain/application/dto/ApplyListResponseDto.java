package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApplyListResponseDto {
    String formName;

    HttpStatus httpStatus;
    List<ApplyListResponseForm> applicationList;
}
