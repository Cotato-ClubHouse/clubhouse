package clubhouse.clubhouse.domain.club.service;

import clubhouse.clubhouse.domain.club.dto.ClubRequestDto;
import clubhouse.clubhouse.domain.club.entity.CategoryName;
import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    public Club saveClub(ClubRequestDto requestDto) {
        if (!clubRepository.existsByName(requestDto.getClubName())) {
            Club club = new Club(requestDto.getClubName(), requestDto.getCategoryName());
            return clubRepository.save(club);
        }
        else return null;
    }

    public void deleteClub(long clubId) {
        clubRepository.deleteById(clubId);
    }


}
