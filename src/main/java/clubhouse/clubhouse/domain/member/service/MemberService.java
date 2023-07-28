package clubhouse.clubhouse.domain.member.service;

import org.springframework.stereotype.Service;

import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public String join(String email, String password){
		//중복 체크
		memberRepository.findByEmail(email)
			.ifPresent(user -> {
				throw new RuntimeException(email + "는 이미 존재합니다.");
			});

		Member newMember = Member.builder()
			.email(email)
			.password(password)
			.build();

		memberRepository.save(newMember);

		return "회원가입에 성공했습니다.";
	}

}
