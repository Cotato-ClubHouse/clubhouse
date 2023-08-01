package clubhouse.clubhouse.domain.application.service;

import clubhouse.clubhouse.domain.application.entity.Answer;
import clubhouse.clubhouse.domain.application.entity.Application;
import clubhouse.clubhouse.domain.application.repository.AnswerRepository;
import clubhouse.clubhouse.domain.form.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService{
    private final AnswerRepository answerRepository;

    @Override
    public Answer saveAnswer(Application application, Question question, String answerContent) {

        return answerRepository.save(Answer.createAnswer(answerContent, application, question));
    }

    @Override
    public List<Answer> changeAnswer(Application application, List<String> answerContent) {
        List<Answer> answerList = answerRepository.findAllByApplication(application);
        int i=0;
        for (String content : answerContent) {
            Answer answer = answerList.get(i).changeAnswer(content);
            answerRepository.save(answer);
            i++;
        }

        return answerList;
    }
}
