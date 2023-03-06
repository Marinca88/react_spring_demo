package ro.utcn.ps.mi.assignment1.persistance.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.QuestionVotes;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionVotesRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
public class HibernateQuestionVotesRepository implements QuestionVotesRepository {
    private final EntityManager entityManager;
    @Override
    public QuestionVotes save(QuestionVotes vote) {
        if(vote.getId() != null) {
            // update
            return vote;
        } else {
            entityManager.persist(vote);
        }
        return vote;
    }

    @Override
    public List<QuestionVotes> findAll() {
        Query query = entityManager.createNativeQuery("SELECT * FROM question_votes");
        return query.getResultList();
    }
}
