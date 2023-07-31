package clubhouse.clubhouse.domain.form.entity;

import clubhouse.clubhouse.domain.club.entity.Club;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long id;

    @Column(name = "form_title",nullable = false)
    private String title;

    @Column(name = "form_content")
    private String content;

    @Column(name = "form_deadline")
    private LocalDateTime deadline;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @CreatedDate
    @Column(name = "form_status")
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




}
