package clubhouse.clubhouse.domain.application.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ApplyRequestDto {
    private Long formId;
    private List<String> answers;
}
