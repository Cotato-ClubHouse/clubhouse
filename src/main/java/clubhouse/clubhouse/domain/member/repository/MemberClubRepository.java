package clubhouse.clubhouse.domain.member.repository;

import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.entity.MemberClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberClubRepository extends JpaRepository<MemberClub, Long> {
    List<Member> findAllByClub(Club club);

}
