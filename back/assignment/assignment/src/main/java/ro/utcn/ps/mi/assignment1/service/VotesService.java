package ro.utcn.ps.mi.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ro.utcn.ps.mi.assignment1.dto.QuestionDto;
import ro.utcn.ps.mi.assignment1.dto.VotesDto;
import ro.utcn.ps.mi.assignment1.entity.*;
import ro.utcn.ps.mi.assignment1.event.QuestionUpdatedEvent;
import ro.utcn.ps.mi.assignment1.persistance.api.AnswareVotesRepository;
import ro.utcn.ps.mi.assignment1.persistance.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotesService {
    private final RepositoryFactory repositoryFactory;
    @Transactional
    public void saveAnswareVote(VotesDto vote){
        User user = repositoryFactory.createUserReposytory().findByUsername(vote.getUser()).orElseThrow(UserNotFoundException::new);
        Answare answare = repositoryFactory.createAnswareRepository().findById(vote.getId()).orElseThrow(AnswareNotFoundException::new);
        AnswareVotes a = new AnswareVotes(null,answare,"answare",user);
        repositoryFactory.createAnswareVotesRepository().save(a);

    }
    @Transactional
    public List<VotesDto> findAllAnswares(){
        ArrayList<VotesDto> votesDtos = new ArrayList<VotesDto>();
        ArrayList<AnswareVotes> votes = (ArrayList<AnswareVotes>) repositoryFactory.createAnswareVotesRepository().findAll();
        for(AnswareVotes v : votes){
            User user = repositoryFactory.createUserReposytory().findById(v.getUser().getId()).orElseThrow(UserNotFoundException::new);
            votesDtos.add(VotesDto.votesDtoFromAnswareVote(v,user.getUsername()));
        }
        return  votesDtos;
    }

    @Transactional
    public void saveQuestionVote(VotesDto vote){
        User user = repositoryFactory.createUserReposytory().findByUsername(vote.getUser()).orElseThrow(UserNotFoundException::new);
        Question question = repositoryFactory.createQuestionRepository().findById(vote.getId()).orElseThrow(QuestionNotFoundException::new);
        QuestionVotes a = new QuestionVotes(null,question,"question",user);
        repositoryFactory.createQuestionVotesRepository().save(a);


    }
    @Transactional
    public List<VotesDto> findAllQuestions(){
        ArrayList<VotesDto> votesDtos = new ArrayList<VotesDto>();
        ArrayList<QuestionVotes> votes = (ArrayList<QuestionVotes>) repositoryFactory.createQuestionVotesRepository().findAll();
        for(QuestionVotes v : votes){
            User user = repositoryFactory.createUserReposytory().findById(v.getUser().getId()).orElseThrow(UserNotFoundException::new);
            votesDtos.add(VotesDto.votesDtoFromQuestionVote(v, user.getUsername()));
        }
        return  votesDtos;
    }
}
