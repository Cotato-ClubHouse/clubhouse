package clubhouse.clubhouse.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import clubhouse.clubhouse.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String Email);

	Optional<Member> findByRefreshToken(String RefreshToken);
}
