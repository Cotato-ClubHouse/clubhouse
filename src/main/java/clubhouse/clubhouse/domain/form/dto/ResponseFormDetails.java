package clubhouse.clubhouse.domain.form.dto;

import clubhouse.clubhouse.domain.club.entity.CategoryName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseFormDetails {

    private String title;
    private String content;
    private Long remainDays;
    private LocalDateTime deadLine;
    private String photoUrl;
    private CategoryName categoryName;
    private String clubName;
}
