package clubhouse.clubhouse.domain.form.entity;

import clubhouse.clubhouse.domain.club.entity.Club;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "form")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert//columndefault 설정 관련
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long id;

    @Column(name = "form_title",nullable = false)
    private String title;

    @Column(name = "form_content",nullable = false)
    private String content;

    @Column(name = "form_deadline",nullable = false)
    private LocalDateTime deadline;

    @Column(name = "photo_url",nullable = false)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;


    @Column(name = "form_status")
    @ColumnDefault(value = "'RECRUITING'")
    @Enumerated(EnumType.STRING)
    private FormStatus formStatus;

    @Builder
    public Form(String title,String content,Club club,LocalDateTime deadline,String photoUrl){
        this.title = title;
        this.content=content;
        this.club = club;
        this.deadline=deadline;
        this.photoUrl=photoUrl;
    }


    public void update(String title,String content,FormStatus formStatus){
        this.title = title;
        this.content=content;
        this.formStatus=formStatus;
    }



}
