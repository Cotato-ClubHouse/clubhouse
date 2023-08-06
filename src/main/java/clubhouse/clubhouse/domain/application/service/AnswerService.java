package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.entity.Answer;
import clubhouse.clubhouse.domain.application.entity.Application;
import clubhouse.clubhouse.domain.form.entity.Question;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    Answer saveAnswer(Application application, Question question, String answerContent);

    List<Answer> changeAnswer(Application application, List<String> answerContent);

    Optional<Answer> findAnswerWithQuestion(Application application, Question question);
}
