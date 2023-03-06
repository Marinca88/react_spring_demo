package ro.utcn.ps.mi.assignment1.persistance.api;


import ro.utcn.ps.mi.assignment1.entity.QuestionVotes;

import java.util.List;

public interface QuestionVotesRepository {
    public QuestionVotes save(QuestionVotes vote);
    public List<QuestionVotes> findAll();
}
