package clubhouse.clubhouse.domain.club.service;

import clubhouse.clubhouse.domain.club.dto.ClubRequestDto;
import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.member.entity.Member;

public interface ClubService {
    Club saveClub(ClubRequestDto requestDto);

    void deleteClub(long clubId);

    void changeMemberStatus(Member member, Long clubId, boolean memberStatus);

    boolean isAdmin(Long clubId, String memberEmail);
}
