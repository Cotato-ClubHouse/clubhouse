package clubhouse.clubhouse.domain.application.dto.response;

import clubhouse.clubhouse.domain.application.dto.form.UserApplyListForm;
import clubhouse.clubhouse.domain.club.dto.ClubInfoDto;
import clubhouse.clubhouse.domain.club.dto.ClubListDto;
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

    //가입 클럽 정보
    ClubListDto clubListDto;

}
