package clubhouse.clubhouse.domain.club.repository;

import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.member.entity.MemberClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    boolean existsByName(String name);
    Club findByAdminId(String email);
    List<Club> findAllByAdminId(String email);
    List<MemberClub> findAllById(Long id);
}
