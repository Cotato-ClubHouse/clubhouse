package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;

@Data
public class ApplyListRequestDto {
    private String memberEmail;
    private Long formId;
}
