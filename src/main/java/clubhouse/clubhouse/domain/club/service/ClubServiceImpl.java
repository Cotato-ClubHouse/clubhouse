package clubhouse.clubhouse.domain.club.service;

import clubhouse.clubhouse.domain.club.dto.ClubRequestDto;
import clubhouse.clubhouse.domain.club.entity.CategoryName;
import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.club.repository.ClubRepository;
import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    // TODO: 추후에 이메일을 jwt에서 가져오게끔 수정할 예정
    @Transactional
    public Club saveClub(ClubRequestDto requestDto) {
        if (!clubRepository.existsByName(requestDto.getClubName())
                && memberRepository.existsByEmail(requestDto.getClubAdminId())) {
            Club club = new Club(requestDto.getClubName(), requestDto.getCategoryName(), requestDto.getClubAdminId(), requestDto.getClubIntro());
            return clubRepository.save(club);
        } else return null;
    }

    public void deleteClub(long clubId) {
        clubRepository.deleteById(clubId);
    }


}
