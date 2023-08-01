package clubhouse.clubhouse.domain.application.repository;

import clubhouse.clubhouse.domain.application.entity.Answer;
import clubhouse.clubhouse.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByApplication(Application application);
}
