package clubhouse.clubhouse.domain.club.dto;

import clubhouse.clubhouse.domain.club.entity.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class ClubRequestDto {
    String clubName;
    String categoryName;
    String clubAdminId;
    String clubIntro;
}
