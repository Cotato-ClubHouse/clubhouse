package clubhouse.clubhouse.domain.application.entity;

import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "application")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Column(name = "application_created_at")
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    @JoinColumn(name = "is_pass")
    private boolean isPass;

    private Application(LocalDateTime localDateTime, Member member, Form form, boolean isPass) {
        this.localDateTime = localDateTime;
        this.member = member;
        this.form = form;
        this.isPass = isPass;
    }

    public static Application createApplication(LocalDateTime localDateTime,Member member,Form form, boolean isPass) {
        return new Application(localDateTime,member,form, isPass);
    }

}
