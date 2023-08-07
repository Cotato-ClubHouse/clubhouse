package clubhouse.clubhouse.domain.member.repository;

import java.util.List;
import java.util.Optional;

import clubhouse.clubhouse.domain.member.entity.MemberClub;
import org.springframework.data.jpa.repository.JpaRepository;

import clubhouse.clubhouse.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String Email);

    Optional<Member> findByRefreshToken(String RefreshToken);

    boolean existsByEmail(String email); //이민영이 추가한 메소드
}

