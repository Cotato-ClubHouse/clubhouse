package clubhouse.clubhouse.domain.application.dto.request;

import lombok.Data;

@Data
public class ApplyListRequestDto {
    private String memberEmail;
    private Long formId;
}
