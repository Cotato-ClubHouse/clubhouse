package clubhouse.clubhouse.domain.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import clubhouse.clubhouse.domain.member.entity.MemberJoinRequest;
import clubhouse.clubhouse.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/v1/users/join")
	public String join(@RequestBody MemberJoinRequest memberJoinRequest){
		return memberService.join(memberJoinRequest.getEmail(), memberJoinRequest.getPassword());
	}
}
