package clubhouse.clubhouse.domain.form.repository;

import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.form.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form,Long> {
    List<Form> findAllByClub(Club club);
}
