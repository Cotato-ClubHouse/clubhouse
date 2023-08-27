package clubhouse.clubhouse.domain.club.service;

import clubhouse.clubhouse.domain.club.dto.ClubInfoDto;
import clubhouse.clubhouse.domain.club.dto.ClubListDto;

public interface MypageService {
    ClubListDto getClubList(String email);

    ClubInfoDto getClubInfo(Long clubId);
}
