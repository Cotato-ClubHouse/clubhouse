package clubhouse.clubhouse.domain.form.dto;

import lombok.Data;

@Data
public class RequestQuestionDto {

    private Long quesNum;
    private String content;
    private Long charLimit;//글자수 제한
//    private Form form;



}
