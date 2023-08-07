package clubhouse.clubhouse.domain.application.dto.response;

import clubhouse.clubhouse.domain.application.dto.form.UserApplyListForm;
import lombok.Data;

import java.util.List;

@Data
public class MyPageResponseDto {
    
    //user 이름
    String memberName;

    //한줄 소개
    String simpleIntroduction;

    //지원서 목록 list
    List<UserApplyListForm> applicationList;
}
