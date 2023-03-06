package ro.utcn.ps.mi.assignment1.persistance.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.AnswareRepository;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateAnswareRepository implements AnswareRepository {
    private final EntityManager entityManager;

    @Override
    public Answare save(Answare answare) {
        if(answare.getId() != null) {
            // update
            entityManager.merge(answare);
        } else {
            // insert
            entityManager.persist(answare);
        }

        return answare;
    }

    @Override
    public void remove(Answare answare) {
        entityManager.remove(answare);
    }

    @Override
    public Optional<Answare> findById(Integer integer) {
        return Optional.ofNullable(entityManager.find(Answare.class, integer));
    }

    @Override
    public List<Answare> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Answare> query = builder.createQuery(Answare.class);
        query.select(query.from(Answare.class));

        return entityManager.createQuery(query).getResultList();
    }
}
