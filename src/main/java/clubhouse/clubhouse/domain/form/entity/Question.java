package clubhouse.clubhouse.domain.form.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@NoArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "question_content")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "form_id")
    private Form form;

    @Column(name = "question_num")
    private Long questionNum;

    @Column(name = "question_char_limit")
    private Long charLimit;

    @Builder
    public Question(String contents, Form form,Long questionNum, Long quesCharLimit){
        this.contents=contents;
        this.form=form;
        this.questionNum=questionNum;
        this.charLimit = quesCharLimit;
    }

}
