package clubhouse.clubhouse.domain.form.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestFormDto {

    private Long clubId;

    @NotBlank(message = "title cannot be null")
    @Size(min = 2, message = "title should be longer than 2")
    private String title;

    @NotBlank(message = "content cannot be null")
    @Size(min = 2, message = "content should be longer than 2")
    private String content;

    @NotBlank(message = "deadLine cannot be null")
    private LocalDateTime deadLine;

//    private String photoUrl;
    private MultipartFile formImage;

//    @NotNull(message = "You should ask at least 1 question")
    private List<RequestQuestionDto> quesList;
}
