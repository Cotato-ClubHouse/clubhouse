package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;

@Data
public class ApplicationEditDetailRequestDto {
    private Long applicationId;

    private String memberEmail;
}
