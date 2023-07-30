package clubhouse.clubhouse.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clubhouse.clubhouse.domain.member.entity.MemberJoinRequest;
import clubhouse.clubhouse.domain.member.entity.MemberLoginRequest;
import clubhouse.clubhouse.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/join")
	public String join(@RequestBody MemberJoinRequest memberJoinRequest){
		return memberService.join(memberJoinRequest.getEmail(), memberJoinRequest.getPassword());
	}

	@PostMapping("/login")
	public String login(@RequestBody MemberLoginRequest memberLoginRequest){
		return memberService.login(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());
	}

	@PostMapping("/form")
	public ResponseEntity<String> writeForm(Authentication authentication){
		return ResponseEntity.ok().body(authentication.getName() + "님의 공고 등록이 완료 되었습니다.");
	}
}
