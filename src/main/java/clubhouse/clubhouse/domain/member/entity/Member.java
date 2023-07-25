package clubhouse.clubhouse.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(name = "member_name")
	private String name;

	@Column(name = "member_email")
	private String email;//email을 id로 사용 예정

	@Column(name = "member_pw")
	private String password;

	@Column(name = "member_phone")
	private String phone;

	@Column(name = "member_univ")
	private String univ;

	@Column(name = "member_age")
	private Long age;

	@OneToMany(mappedBy = "member")
	private List<MemberClub> memberClubs = new ArrayList<>();

	@Column(name = "member_gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

}
