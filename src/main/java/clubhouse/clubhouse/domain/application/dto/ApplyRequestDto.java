package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplyRequestDto {
    private Long member_id;
    private Long form_id;
    private List<String> answers;
}
