package ro.utcn.ps.mi.assignment1.persistance.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateQuestionRepository implements QuestionRepository {
    private  final EntityManager entityManager;
    @Override
    public Question save(Question question) {
        if(question.getQuestionId() != null) {
            // update
            entityManager.merge(question);
        } else {
            // insert
            entityManager.persist(question);
        }

        return question;

    }

    @Override
    public List<Question> findByText(String text) {
        List<Question> questions = findAll();
        List<Question> result = new ArrayList<Question>();
        for(Question q :questions){
            if(q.getBody().contains(text)||q.getTitle().contains(text)){
                result.add(q);
            }
        }
        return result;
    }

    @Override
    public Optional<Question> findById(Integer id) {

            return Optional.ofNullable(entityManager.find(Question.class, id));

    }

    @Override
    public List<Question> findByAuthor(User user) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        Root<Question> question = query.from(Question.class);
        query.select(question);
        query.where(builder.equal(question.get("author"),user));
        return entityManager.createQuery(query).getResultList();


    }

    @Override
    public void remove(Question question) {
        entityManager.remove(question);
    }

    @Override
    public List<Question> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        query.select(query.from(Question.class));

        return entityManager.createQuery(query).getResultList();
    }

}
