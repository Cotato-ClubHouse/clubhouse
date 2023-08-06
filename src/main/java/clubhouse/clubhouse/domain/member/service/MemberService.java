package clubhouse.clubhouse.domain.member.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.entity.MemberJoinRequest;
import clubhouse.clubhouse.domain.member.exception.AppException;
import clubhouse.clubhouse.domain.member.exception.ErrorCode;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;
import clubhouse.clubhouse.utils.JwtUtil;
import clubhouse.clubhouse.utils.Token;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final BCryptPasswordEncoder encoder;

	private final JwtUtil jwtUtil;

	public String join(MemberJoinRequest memberJoinRequest){
		//중복 체크
		memberRepository.findByEmail(memberJoinRequest.getEmail())
			.ifPresent(member -> {
				throw new AppException(ErrorCode.EMAIL_DUPLICATED, memberJoinRequest.getEmail() + "는 이미 존재하는 이메일입니다.");
			});

		Member newMember = Member.builder()
			.name(memberJoinRequest.getName())
			.email(memberJoinRequest.getEmail())
			.password(encoder.encode(memberJoinRequest.getPassword()))
			.phone(memberJoinRequest.getPhone())
			.univ(memberJoinRequest.getUniv())
			.major(memberJoinRequest.getMajor())
			.birthDate(memberJoinRequest.getBirthDate())
			.gender(memberJoinRequest.getGender())
			.build();

		memberRepository.save(newMember);

		return "회원가입에 성공했습니다.";
	}

	@Transactional
	public Token login(String email, String password) {
		// email 없음
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() ->new AppException(ErrorCode.EMAIL_NOTFOUND, email + "이 존재하지 않습니다."));
		//password 틀림
		if(!encoder.matches(password, findMember.getPassword())){
			throw new AppException(ErrorCode.INVAILD_PASSWORD, "패스워드가 일치하지 않습니다.");
		}

		Token token = jwtUtil.createToken(email);
		findMember.updateRefreshToken(token.getRefreshToken());

		return token;
	}

	@Transactional
	public Token reissue(String refreshToken){

		if(jwtUtil.isExpired(refreshToken)){
			throw new RuntimeException("토큰 만료");
		}

		Member findMember = memberRepository.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOTFOUND, "멤버가 존재하지 않습니다."));

		Token token = jwtUtil.createToken(findMember.getEmail());

		findMember.updateRefreshToken(token.getRefreshToken());

		return token;
	}

	@Transactional
	public void logout(String email) {
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOTFOUND, "해당 멤버가 존재하지 않습니다."));
		findMember.invaildRefreshToken();
	}
}
