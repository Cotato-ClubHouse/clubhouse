package clubhouse.clubhouse.club.domain;

import java.util.ArrayList;
import java.util.List;

import clubhouse.clubhouse.category.domain.Category;
import clubhouse.clubhouse.memberclub.domain.MemberClub;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

	//@Column(name = "club_name")
	private String name;

	//@Column(name = "club_admin_id")
	private Long admin_id;

	@OneToOne(fetch = FetchType.LAZY)
	private Category category;

	@OneToMany(mappedBy = "club")
	private List<MemberClub> memberClubs = new ArrayList<>();

}
