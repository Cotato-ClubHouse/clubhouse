package clubhouse.clubhouse.domain.application.repository;

import clubhouse.clubhouse.domain.application.entity.Application;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByForm(Form formId);

    List<Application> findAllByMember(Member member);

    Optional<Application> findByMemberAndForm(Member member, Form form);

}
