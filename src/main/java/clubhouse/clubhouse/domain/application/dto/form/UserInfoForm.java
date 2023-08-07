package clubhouse.clubhouse.domain.application.dto.form;

import clubhouse.clubhouse.domain.member.entity.Gender;
import lombok.Data;

@Data
public class UserInfoForm {
    private String name;
    private Gender gender;
    private String birthDay; //출생연도 엔티티 바뀌면 수정해야함 TODO
    private String phoneNum;
    private String univAndMajor;
}
