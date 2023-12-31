package clubhouse.clubhouse.domain.club.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubInfoDto {
    private String name;
    private String intro;
    private List<ClubFormsForm> formList;
    private List<ClubMembersForm> memberList;

}
