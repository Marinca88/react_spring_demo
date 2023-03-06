package ro.utcn.ps.mi.assignment1.persistance.data;

import org.springframework.data.repository.Repository;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.persistance.api.AnswareRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface DataAnswareRepository extends AnswareRepository, Repository<Answare, Integer> {

    void delete(Answare answare);

    @Override
    public default void remove(Answare answare) {
        delete(answare);
    }
    
}
