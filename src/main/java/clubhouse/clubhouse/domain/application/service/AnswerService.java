package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.entity.Answer;
import clubhouse.clubhouse.domain.application.entity.Application;
import clubhouse.clubhouse.domain.form.entity.Question;

public interface AnswerService {
    Answer saveAnswer(Application application, Question question, String answerContent);
}