package clubhouse.clubhouse.domain.form.dto;

import clubhouse.clubhouse.domain.form.entity.FormStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponsePatchForm {

    private String title;
    private String content;
//    private LocalDateTime localDateTime;
    private FormStatus formStatus;
}
