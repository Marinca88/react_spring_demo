package ro.utcn.ps.mi.assignment1.persistance.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.ps.mi.assignment1.entity.AnswareVotes;
import ro.utcn.ps.mi.assignment1.persistance.api.AnswareVotesRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
@RequiredArgsConstructor
public class HiberanteAnswareVotesRepository implements AnswareVotesRepository {
    private final EntityManager entityManager;
    @Override
    public AnswareVotes save(AnswareVotes vote) {
        if(vote.getId() != null) {
            // update
            return vote;
        } else {
            entityManager.persist(vote);
        }
        return vote;
    }

    @Override
    public List<AnswareVotes> findAll() {
        Query query = entityManager.createNativeQuery("SELECT * FROM answare_votes");
        return query.getResultList();
    }
}
