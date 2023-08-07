package clubhouse.clubhouse.domain.club.dto;

import clubhouse.clubhouse.domain.form.entity.FormStatus;
import lombok.Data;

@Data
public class ClubFormsForm {
    private String photoUrl;
    private String title;
    private FormStatus formStatus;

    public ClubFormsForm(String photoUrl, String title, FormStatus formStatus){
        this.photoUrl = photoUrl;
        this.title = title;
        this.formStatus = formStatus;
    }
}
