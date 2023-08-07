package clubhouse.clubhouse.domain.application.dto.request;

import lombok.Data;

@Data
public class ApplyChangeIsPassRequestDto {
    private String memberEmail;
    private String isPass;
}
