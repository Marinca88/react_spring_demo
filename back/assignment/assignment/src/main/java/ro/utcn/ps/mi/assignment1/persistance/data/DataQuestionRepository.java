package ro.utcn.ps.mi.assignment1.persistance.data;

import org.springframework.data.repository.Repository;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;


public interface DataQuestionRepository extends QuestionRepository, Repository<Question, Integer> {



    @Override
    default public List<Question> findByText(String text) {
        List<Question> questions = new ArrayList<Question>();
        for(Question q: findAll()){
            if(q.getBody().contains(text)||q.getTitle().contains(text)){
                questions.add(q);
            }
        }
        return questions;
    }


    void delete(Question question);

    @Override
    public default void remove(Question question) {
        delete(question);
    }

}
