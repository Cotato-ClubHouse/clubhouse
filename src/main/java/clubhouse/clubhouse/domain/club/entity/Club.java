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
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "club_name")
    private String name;

    @Column(name = "club_admin_id")
    private String adminId;

    @Column(name = "club_cat")
    @Enumerated(EnumType.STRING)
    private CategoryName categoryName;

    @Column(name = "club_intro")
    private String intro;

    @OneToMany(mappedBy = "club")
    private List<MemberClub> memberClubs = new ArrayList<>();

    public Club(String name, CategoryName categoryName, String adminId, String intro) {
        this.name = name;
        this.categoryName = categoryName;
        this.adminId = adminId;
        this.intro = intro;
    }

    //	public Club(Long id, String name, Long adminId, CategoryName categoryName){
//		this.id = id;
//		this.name = name;
//		this.adminId = adminId;
//		this.categoryName = categoryName;
//	}

}
