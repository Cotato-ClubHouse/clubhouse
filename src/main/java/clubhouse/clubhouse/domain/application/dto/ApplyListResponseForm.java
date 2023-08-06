package clubhouse.clubhouse.domain.application.dto;


import lombok.Data;

@Data
public class ApplyListResponseForm {

    private String name;
    private Long age;
    private String school;
    private boolean isPass;
    private Long applicationId;

    public ApplyListResponseForm(String name, Long age, String school, boolean is_pass, Long application_id) {
        this.name = name;
        this.age = age;
        this.school = school;
        this.isPass = is_pass;
        this.applicationId = application_id;
    }
}
