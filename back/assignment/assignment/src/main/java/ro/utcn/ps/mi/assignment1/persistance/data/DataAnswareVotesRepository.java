package ro.utcn.ps.mi.assignment1.persistance.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import ro.utcn.ps.mi.assignment1.entity.AnswareVotes;
import ro.utcn.ps.mi.assignment1.persistance.api.AnswareVotesRepository;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionVotesRepository;


public interface DataAnswareVotesRepository extends AnswareVotesRepository, Repository<AnswareVotes,Integer> {
}
