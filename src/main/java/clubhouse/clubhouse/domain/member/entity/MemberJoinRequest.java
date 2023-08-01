package clubhouse.clubhouse.domain.member.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

	@NotBlank(message = "이름을 입력해주세요")
	private String name;

	@NotBlank(message = "이메일을 입력해주세요")
	private String email;

	@NotBlank(message = "비밀번호를 입력해주세요")
	private String password;

	@NotBlank(message = "전화번호를 입력해주세요")
	private String phone;

	@NotBlank(message = "대학교를 입력해주세요")
	private String univ;

	@NotBlank(message = "나이를 입력해주세요")
	private Long age;

	@NotBlank(message = "성별 선택해주세요")
	private Gender gender;

	@Builder
	public MemberJoinRequest(String name, String email, String password, String phone, String univ, Long age,
		Gender gender) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.univ = univ;
		this.age = age;
		this.gender = gender;
	}
}
