package clubhouse.clubhouse.domain.application.dto.response;

import clubhouse.clubhouse.domain.application.dto.form.ApplyListResponseForm;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApplyListResponseDto {
    String formName;
    List<ApplyListResponseForm> applicationList;
}
