package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;

@Data
public class ApplyChangeIsPassRequestDto {
    private String memberEmail;
    private String isPass;
}
