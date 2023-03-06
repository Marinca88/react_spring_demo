package ro.utcn.ps.mi.assignment1.persistance.api;

import ro.utcn.ps.mi.assignment1.entity.AnswareVotes;

import java.util.List;

public interface AnswareVotesRepository {
    public AnswareVotes save(AnswareVotes vote);
    public List<AnswareVotes> findAll();
}
