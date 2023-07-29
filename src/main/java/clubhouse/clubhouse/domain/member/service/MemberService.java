package clubhouse.clubhouse.domain.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.exception.AppException;
import clubhouse.clubhouse.domain.member.exception.ErrorCode;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final BCryptPasswordEncoder encoder;

	public String join(String email, String password){
		//중복 체크
		memberRepository.findByEmail(email)
			.ifPresent(member -> {
				throw new AppException(ErrorCode.EMAIL_DUPLICATED, email + "는 이미 존재하는 이메일입니다.");
			});

		Member newMember = Member.builder()
			.email(email)
			.password(encoder.encode(password))
			.build();

		memberRepository.save(newMember);

		return "회원가입에 성공했습니다.";
	}

}
