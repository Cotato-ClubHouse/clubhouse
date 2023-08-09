package clubhouse.clubhouse.domain.member.entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

	@Column(name = "member_major")
	private String major;

	@Column(name = "member_birthDate")
	private LocalDate birthDate;

	@Column(name = "member_gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@OneToMany(mappedBy = "member")
	private List<MemberClub> memberClubs = new ArrayList<>();

	@Column(name = "refreshToken")
	private String refreshToken;

	@Builder
	public Member(String name, String email, String password, String phone, String univ, String major,
		LocalDate birthDate,
		Gender gender) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.univ = univ;
		this.major = major;
		this.birthDate = birthDate;
		this.gender = gender;
	}
	public void updateRefreshToken(String refreshToken){
		this.refreshToken = refreshToken;
	}

	public void invaildRefreshToken() {
		this.refreshToken = null;
	}
	// 나이 계산
	public int getAge(){
		LocalDate today = LocalDate.now();
		Period period = Period.between(today, this.birthDate);
		return period.getYears();
	}
}
