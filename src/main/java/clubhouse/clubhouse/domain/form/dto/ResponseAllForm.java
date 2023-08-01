package clubhouse.clubhouse.domain.form.dto;

import clubhouse.clubhouse.domain.club.entity.CategoryName;
import clubhouse.clubhouse.domain.form.entity.FormStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class ResponseAllForm {

    private CategoryName categoryName;
    private String photoUrl;
    private Long remainDay;

    private String clubName;
    private FormStatus formStatus;

    private String content;
}
