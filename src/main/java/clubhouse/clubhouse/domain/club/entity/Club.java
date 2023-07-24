package clubhouse.clubhouse.domain.club.entity;

import java.util.ArrayList;
import java.util.List;

import clubhouse.clubhouse.domain.member.entity.MemberClub;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_id")
	private Long id;

	@Column(name = "club_name")
	private String name;

	@Column(name = "club_admin_id")
	private Long adminId;

	@Column(name = "club_cat")
	@Enumerated(EnumType.STRING)
	private CategoryName categoryName;

	@OneToMany(mappedBy = "club")
	private List<MemberClub> memberClubs = new ArrayList<>();

}
