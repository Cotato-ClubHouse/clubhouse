package clubhouse.clubhouse.domain.form.service;

import clubhouse.clubhouse.domain.form.dto.RequestQuestionDto;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;
import clubhouse.clubhouse.domain.form.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;

    @Override
    public Question createQuestion(RequestQuestionDto question, Form form) {

        Question result = Question.builder()
                .contents(question.getContent())
                .questionNum(question.getQuesNum())
                .form(form)
                .build();
        log.info("new form created");
        return questionRepository.save(result);

    }

    @Override
    public List<Question> findAllQuesByFormId(Long formId) {
        return null;
    }
}
