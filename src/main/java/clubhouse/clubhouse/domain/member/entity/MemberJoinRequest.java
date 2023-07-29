package clubhouse.clubhouse.domain.member.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

	@NotBlank(message = "이메일을 입력해주세요")
	private String email;

	@NotBlank(message = "비밀번호를 입력해주세요")
	private String password;

	@Builder
	public MemberJoinRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
