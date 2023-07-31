package clubhouse.clubhouse.domain.application.dto;


import lombok.Data;

@Data
public class ApplyListResponseForm {

    private String name;
    private Long age;
    private String school;
    private boolean is_pass;
    private Long application_id;

    public ApplyListResponseForm(String name, Long age, String school, boolean is_pass, Long application_id) {
        this.name = name;
        this.age = age;
        this.school = school;
        this.is_pass = is_pass;
        this.application_id = application_id;
    }
}
