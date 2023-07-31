package clubhouse.clubhouse.domain.application.dto;

import clubhouse.clubhouse.domain.application.entity.Application;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApplyListResponseDto {

    HttpStatus httpStatus;
    List<ApplyListResponseForm> application_list;
}
