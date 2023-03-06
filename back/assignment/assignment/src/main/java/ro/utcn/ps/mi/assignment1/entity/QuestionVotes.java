package ro.utcn.ps.mi.assignment1.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuestionVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private  Question question;
    private  String type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private  User user;



}
