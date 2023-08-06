package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplyChangeIsPassRequestDto {
    private String memberEmail;
    private String isPass;
}
