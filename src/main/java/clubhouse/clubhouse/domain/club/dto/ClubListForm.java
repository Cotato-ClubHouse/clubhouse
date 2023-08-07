package clubhouse.clubhouse.domain.club.dto;

import lombok.Data;

@Data
public class ClubListForm {

    private Long clubId;
    private String clubName;

    public ClubListForm(Long clubId, String clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }
}