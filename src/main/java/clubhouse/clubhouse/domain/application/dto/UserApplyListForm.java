package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;

@Data
public class UserApplyListForm {
    String clubName;

    Long applicationId;

    public UserApplyListForm(String club_name, Long application_id) {
        this.clubName = club_name;
        this.applicationId = application_id;
    }
}
