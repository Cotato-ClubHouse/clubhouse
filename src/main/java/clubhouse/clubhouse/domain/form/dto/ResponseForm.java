package clubhouse.clubhouse.domain.form.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//등록여부 확인


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForm {

    private String title;
    private String content;


}
