package clubhouse.clubhouse.domain.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clubhouse.clubhouse.domain.member.entity.MemberJoinRequest;
import clubhouse.clubhouse.domain.member.entity.MemberLoginRequest;
import clubhouse.clubhouse.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody MemberJoinRequest memberJoinRequest) {
		String result = memberService.join(memberJoinRequest.getEmail(), memberJoinRequest.getPassword());
		return ResponseEntity.ok().body(result);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody MemberLoginRequest memberLoginRequest,
		HttpServletResponse httpServletResponse) {
		Map<String, String> result = memberService.login(memberLoginRequest.getEmail(),
			memberLoginRequest.getPassword());
		httpServletResponse.addHeader("accessToken", result.get("accessToken"));

		Cookie cookie = new Cookie("refreshToken", result.get("refreshToken"));
		cookie.setPath("/");
		cookie.setMaxAge(3000000);
		cookie.isHttpOnly();
		cookie.setSecure(true);

		httpServletResponse.addCookie(cookie);

		return ResponseEntity.ok().body("로그인에 성공했습니다.");
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> reissueToken(@CookieValue(name = "refreshToken", required = false) String refreshToken,
		HttpServletResponse httpServletResponse) {

		if (refreshToken == null || refreshToken.isEmpty()) {
			return ResponseEntity.ok().body("리프레쉬 토큰이 존재하지 않습니다. 다시 로그인 해주세요");
		}

		log.info("refreshToken = {}", refreshToken);
		Map<String, String> result = memberService.reissue(refreshToken);
		httpServletResponse.addHeader("accessToken", result.get("accessToken"));

		Cookie cookie = new Cookie("refreshToken", result.get("refreshToken"));
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24);
		cookie.isHttpOnly();
		cookie.setSecure(true);

		httpServletResponse.addCookie(cookie);

		return ResponseEntity.ok().body("토큰 재발급 성공");
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(Authentication authentication, HttpServletResponse httpServletResponse) {

		memberService.logout(authentication.getName());

		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);

		httpServletResponse.addCookie(cookie);

		return ResponseEntity.ok().body(authentication.getName() + "님 로그아웃에 성공했습니다.");
	}

	@PostMapping("/form")
	public ResponseEntity<String> writeForm(Authentication authentication) {
		return ResponseEntity.ok().body(authentication.getName() + "님의 공고 등록이 완료 되었습니다.");
	}
}
