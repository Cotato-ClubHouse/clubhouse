package clubhouse.clubhouse.domain.form.service;


import clubhouse.clubhouse.domain.form.dto.RequestFormDto;
import clubhouse.clubhouse.domain.form.dto.ResponseForm;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface FormService {

    //일단 클럽id로 생성하게 설정 추후에
    ResponseForm createForm(RequestFormDto formDto);

    /**
     * id로 하나 찾기
     * @param formId
     * @return
     */
    Optional<Form> findById(Long formId);
    /**
     * 해당 공고에 등록된 모든 질문을 가져옴
     * @param formId
     * @return
     */
//    List<Question> findAllQuestions(Long formId);

}
