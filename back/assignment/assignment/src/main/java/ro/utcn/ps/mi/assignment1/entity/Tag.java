package ro.utcn.ps.mi.assignment1.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString(of={"id","text"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    @ManyToMany(mappedBy = "tags",fetch = FetchType.EAGER)
    private List<Question> questions;

    public Tag(Integer id,String text){
        this.text=text;
        this.id=id;
    }
    public Tag(String text){
        this.text=text;
    }

}
