package clubhouse.clubhouse.domain.form.service;

import clubhouse.clubhouse.domain.form.dto.RequestQuestionDto;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;

import java.util.List;

public interface QuestionService {

    Question createQuestion(RequestQuestionDto question, Form form);
    List<Question> findAllQuesByFormId(Long formId);
}
