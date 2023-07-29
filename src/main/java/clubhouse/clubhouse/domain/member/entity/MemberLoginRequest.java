package clubhouse.clubhouse.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberLoginRequest {
	private String email;
	private String password;

	@Builder
	public MemberLoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
