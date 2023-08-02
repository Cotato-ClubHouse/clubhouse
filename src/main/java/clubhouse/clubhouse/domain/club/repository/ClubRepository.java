package clubhouse.clubhouse.domain.club.repository;

import clubhouse.clubhouse.domain.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    boolean existsByName(String name);
}
