package ro.utcn.ps.mi.assignment1.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnswareVotes{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private  Answare answare;
    private  String type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private  User user;
}
