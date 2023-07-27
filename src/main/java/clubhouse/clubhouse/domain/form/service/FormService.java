package clubhouse.clubhouse.domain.form.service;


import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;

import java.util.List;
import java.util.Optional;

public interface FormService {

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
    List<Question> findAllQuestions(Long formId);
}
