package clubhouse.clubhouse.domain.member.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.exception.AppException;
import clubhouse.clubhouse.domain.member.exception.ErrorCode;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;
import clubhouse.clubhouse.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final BCryptPasswordEncoder encoder;

	@Value("${JWT_SECRET_KEY}")
	private String secretKey;

	private Long expiredMs = 1000 * 60 * 60L;

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

	@Transactional
	public Map<String, String> login(String email, String password) {
		// email 없음
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() ->new AppException(ErrorCode.EMAIL_NOTFOUND, email + "이 존재하지 않습니다."));
		//password 틀림
		if(!encoder.matches(password, findMember.getPassword())){
			throw new AppException(ErrorCode.INVAILD_PASSWORD, "패스워드가 일치하지 않습니다.");
		}

		String accessToken = JwtUtil.createAccessToken(findMember.getEmail(), secretKey, expiredMs);
		String refreshToken = JwtUtil.createRefreshToken(findMember.getEmail(), secretKey, expiredMs);

		findMember.updateRefreshToken(refreshToken);

		Map<String, String> token = new HashMap<>();
		token.put("accessToken", accessToken);
		token.put("refreshToken", refreshToken);

		return token;
	}

	@Transactional
	public Map<String, String> reissue(String getRefreshToken){

		if(JwtUtil.isExpired(getRefreshToken, secretKey)){
			throw new RuntimeException("토큰 만료");
		}

		Member findMember = memberRepository.findByRefreshToken(getRefreshToken)
			.orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOTFOUND, "멤버가 존재하지 않습니다."));

		String accessToken = JwtUtil.createAccessToken(findMember.getEmail(), secretKey, expiredMs);
		String refreshToken = JwtUtil.createRefreshToken(findMember.getEmail(), secretKey, expiredMs);

		findMember.updateRefreshToken(refreshToken);

		Map<String, String> token = new HashMap<>();
		token.put("accessToken", accessToken);
		token.put("refreshToken", refreshToken);

		return token;
	}

	@Transactional
	public void logout(String email) {
		Member findMember = memberRepository.findByEmail(email)
			.orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOTFOUND, "해당 멤버가 존재하지 않습니다."));
		findMember.invaildRefreshToken();
	}
}
