package clubhouse.clubhouse.domain.form.entity;

import clubhouse.clubhouse.domain.club.entity.Club;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long id;

    @Column(name = "form_title")
    private String title;

    @Column(name = "form_deadline")
    private LocalDateTime deadline;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
}
