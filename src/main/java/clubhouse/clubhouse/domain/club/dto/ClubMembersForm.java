package clubhouse.clubhouse.domain.club.dto;

import lombok.Data;

@Data
public class ClubMembersForm {
    private String name;
    private Long age;
    private String univ;
    private String major;

    public ClubMembersForm(String name, Long age, String univ, String major) {
        this.name = name;
        this.age = age;
        this.univ = univ;
        this.major = major;
    }

}
