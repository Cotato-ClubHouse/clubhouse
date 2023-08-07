package clubhouse.clubhouse.domain.application.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApplicationDetailResponseDto {
    private HttpStatus httpStatus;
    private String formName;
    private List<String> qeustionList;

    private Long formId;
}
