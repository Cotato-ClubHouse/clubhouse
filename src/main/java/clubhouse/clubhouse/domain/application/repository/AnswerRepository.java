package clubhouse.clubhouse.domain.application.repository;

import clubhouse.clubhouse.domain.application.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
