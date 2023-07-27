package clubhouse.clubhouse.domain.form.service;

import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;
import clubhouse.clubhouse.domain.form.repository.FormRepository;
import clubhouse.clubhouse.domain.form.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;


    @Override
    public Optional<Form> findById(Long formId) {
        Optional<Form> form = formRepository.findById(formId);
        return form;
    }

    @Override
    public List<Question> findAllQuestions(Long formId) {

        List<Question> questionList = questionRepository.findByFormId(formId);
        return questionList;

    }
}
