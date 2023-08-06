package clubhouse.clubhouse.domain.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationDetailResponseDto {
    private boolean isEditable;

    private String formName;

    private UserInfoForm userInfoForm;

    private List<QNA> qnaList;

}
