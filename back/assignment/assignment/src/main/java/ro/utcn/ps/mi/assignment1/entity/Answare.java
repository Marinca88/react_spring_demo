package ro.utcn.ps.mi.assignment1.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;



@ToString(of={"id","upvotes","downvotest","text"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Answare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer upvotes;
    private Integer downvotes;
    private String text;
    private LocalDateTime creation_date;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn
    private User author;
    @Column(name="answares_question_id")
    private Integer question_id;


    public Answare(Integer id,Integer upvotes,Integer downvotes,String text,LocalDateTime time){
        this.upvotes =upvotes;
        this.downvotes=downvotes;
        this.text= text;
        this.creation_date= time;
        this.id= id;
    }
    public Answare(Integer upvotes,Integer downvotes,String text,LocalDateTime time){
        this.upvotes =upvotes;
        this.downvotes=downvotes;
        this.text= text;
        this.creation_date= time;
    }
    public Answare(String text){
        this.text = text;
    }
    public Integer getId(){
        return  this.id;
    }

}
