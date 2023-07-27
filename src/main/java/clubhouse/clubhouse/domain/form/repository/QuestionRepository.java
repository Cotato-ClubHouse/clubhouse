package clubhouse.clubhouse.domain.form.repository;

import clubhouse.clubhouse.domain.form.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByFormId(Long formId);
}
