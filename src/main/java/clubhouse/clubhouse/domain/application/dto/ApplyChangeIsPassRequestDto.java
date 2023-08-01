package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplyChangeIsPassRequestDto {
    private Long member_id;
    private String is_pass;
}
