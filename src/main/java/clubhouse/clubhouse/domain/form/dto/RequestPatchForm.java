package clubhouse.clubhouse.domain.form.dto;

import clubhouse.clubhouse.domain.form.entity.FormStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPatchForm {

    /**
     * 공고 수정
     * 제목, 내용, 모집 상태, 사진 수정?
     */
    
    @Size(min = 2, message = "title should be longer than 2")
    private String title;

    
    @Size(min = 2, message = "content should be longer than 2")
    private String content;


//    private LocalDateTime deadLine;

    private FormStatus formStatus;
    
//    private MultipartFile formImage;
}
