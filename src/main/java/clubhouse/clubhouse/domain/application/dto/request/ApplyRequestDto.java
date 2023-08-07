package clubhouse.clubhouse.domain.application.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ApplyRequestDto {
    private String memberEmail;
    private Long formId;
    private List<String> answers;
}
