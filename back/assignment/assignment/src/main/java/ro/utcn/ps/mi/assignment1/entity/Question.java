package ro.utcn.ps.mi.assignment1.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString(of = {"questionId", "title", "body"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;
    private String title;
    private String body;
    private LocalDateTime createTime;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn
    private User author;
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn
    private List<Answare> answares;
    @ManyToMany
    @JoinTable(name="tags",joinColumns = {@JoinColumn(name="questions_question_id")}, inverseJoinColumns = {@JoinColumn(name="tags_id")})
    private  List<Tag> tags;
    private Integer upvotes;
    private  Integer downvotes;

    public Question(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Question(String title, String body, LocalDateTime now) {
        this.title = title;
        this.body = body;
        this.createTime=now;
    }
    public Question(Integer id,String title, String body,LocalDateTime date) {
        this.title = title;
        this.body = body;
        this.questionId=id;
        this.createTime=date;
    }


}
