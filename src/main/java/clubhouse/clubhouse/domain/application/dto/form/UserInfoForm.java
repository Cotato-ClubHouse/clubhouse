package clubhouse.clubhouse.domain.application.dto.form;

import clubhouse.clubhouse.domain.member.entity.Gender;
import lombok.Data;

@Data
public class UserInfoForm {
    private String name;
    private Gender gender;
    private int age;
    private String phoneNum;
    private String univAndMajor;
}
