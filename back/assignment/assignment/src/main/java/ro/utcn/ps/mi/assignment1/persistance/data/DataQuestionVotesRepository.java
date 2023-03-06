package ro.utcn.ps.mi.assignment1.persistance.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import ro.utcn.ps.mi.assignment1.entity.QuestionVotes;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionVotesRepository;

public interface DataQuestionVotesRepository extends QuestionVotesRepository, Repository<QuestionVotes, Integer> {
}
