package clubhouse.clubhouse.domain.application.dto.response;

import clubhouse.clubhouse.domain.application.dto.form.QNA;
import clubhouse.clubhouse.domain.application.dto.form.UserInfoForm;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApplicationEditDetailResponseDto {
    private HttpStatus httpStatus;

    private boolean isEditable;

    private String formName;

    private UserInfoForm userInfoForm;

    private List<QNA> qnaList;

}
