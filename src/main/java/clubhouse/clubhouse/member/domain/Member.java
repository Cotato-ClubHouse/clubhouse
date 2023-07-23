package clubhouse.clubhouse.member.domain;

import java.util.ArrayList;
import java.util.List;

import clubhouse.clubhouse.memberclub.domain.MemberClub;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	private String name;

	private String email;

	private String password;

	private String phone;

	private String univ;

	private Long age;

	@OneToMany(mappedBy = "member")
	private List<MemberClub> memberClubs = new ArrayList<>();

}
