package clubhouse.clubhouse.domain.application.entity;

import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;
import clubhouse.clubhouse.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(name = "answer_content")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private Answer(String contents, Application application, Question question) {
        this.contents = contents;
        this.application = application;
        this.question = question;
    }

    public static Answer createAnswer(String contents, Application application, Question question) {
        return new Answer(contents, application, question);
    }

    public Answer changeAnswer(String content) {
        contents = content;
        return this;
    }

}
