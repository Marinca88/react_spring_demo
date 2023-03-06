package ro.utcn.ps.mi.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ro.utcn.ps.mi.assignment1.dto.AnswareDto;
import ro.utcn.ps.mi.assignment1.dto.QuestionDto;
import ro.utcn.ps.mi.assignment1.dto.UserDto;
import ro.utcn.ps.mi.assignment1.dto.VotesDto;
import ro.utcn.ps.mi.assignment1.entity.*;
import ro.utcn.ps.mi.assignment1.event.QuestionUpdatedEvent;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionVotesRepository;
import ro.utcn.ps.mi.assignment1.persistance.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final RepositoryFactory factory;
    private  final ApplicationEventPublisher eventPublisher;
    private  final UDetailsService uDetailsService;

    @Transactional
    public void save(QuestionDto question, Integer id) {
            Question question1 = new Question(question.getTitle(), question.getBody(), question.getCreateTime());
            User  author = uDetailsService.loadCurrentUser();
            question1.setQuestionId(id);
            question1.setAuthor(author);
            List<Tag> tags = new ArrayList<Tag>();
            for( String s : question.getTags()){
                Tag  t  =  factory.createTagReposytory().findByText(s).get(0);
                tags.add(t);
            }
            question1.setTags(tags);
            question1.setAnswares(new ArrayList<Answare>());
            question.setUpvotes(0);
            question.setDownvotes(0);
            factory.createQuestionRepository().save(question1);
    }


    @Transactional
    public String upVote(QuestionDto questionDto){
        System.out.println(questionDto.getTitle());
        ArrayList<QuestionVotes> v = (ArrayList<QuestionVotes>) factory.createQuestionVotesRepository().findAll();
        User user = uDetailsService.loadCurrentUser();
        ArrayList<Question> questions = (ArrayList<Question>) factory.createQuestionRepository().findAll();
        Integer id=0;
        for(Question q : questions){
            if(q.getBody().equals(questionDto.getBody())&& q.getTitle().equals(questionDto.getTitle())){
                id = q.getQuestionId();
            }
        }

        Boolean var = false;
        for(QuestionVotes vote : v){
            if(vote.getQuestion().getQuestionId()==id && vote.getUser().getUsername().equals(user.getUsername())){
                var= true;
            }
        }
        if( var==false){
            Question question = null;
            for(Question q: questions){
                if(q.getQuestionId()== id){
                    question = q;
                }
            }
            QuestionVotes questionVotes = new QuestionVotes(null,question,"question",user);
            factory.createQuestionVotesRepository().save(questionVotes);
           question.setUpvotes(question.getUpvotes()+1);
           factory.createQuestionRepository().save(question);
           QuestionDto d= QuestionDto.questionDtoFromQuestion(question);
           eventPublisher.publishEvent(new QuestionUpdatedEvent(d));
           return "Voted";
        }else{
            return "Rejected";
        }
    }

    @Transactional
    public String downVote(QuestionDto questionDto){
        ArrayList<QuestionVotes> v = (ArrayList<QuestionVotes>) factory.createQuestionVotesRepository().findAll();
        User user = uDetailsService.loadCurrentUser();
        ArrayList<Question> questions = (ArrayList<Question>) factory.createQuestionRepository().findByAuthor(user);
        Integer id=0;
        for(Question q : questions){
            if(q.getBody().equals(questionDto.getBody())&& q.getTitle().equals(questionDto.getTitle())){
                id = q.getQuestionId();
            }
        }

        Boolean var = false;
        for(QuestionVotes vote : v){
            if(vote.getQuestion().getQuestionId()==id && vote.getUser().getUsername().equals(user.getUsername())){
                var= true;
            }
        }
        if( var==false){
            Question question = null;
            for(Question q: questions){
                if(q.getQuestionId()== id){
                    question = q;
                }
            }
            QuestionVotes questionVotes = new QuestionVotes(null,question,"question",user);
            factory.createQuestionVotesRepository().save(questionVotes);
            question.setDownvotes(question.getDownvotes()+1);
            factory.createQuestionRepository().save(question);
            QuestionDto d= QuestionDto.questionDtoFromQuestion(question);
            eventPublisher.publishEvent(new QuestionUpdatedEvent(d));
            return "Voted";
        }else{
            return "Rejected";
        }
    }

    @Transactional
    public List<QuestionDto> findFromText(String substring) {
        return factory.createQuestionRepository().findByText(substring).stream().map(QuestionDto::questionDtoFromQuestion).collect(Collectors.toList());
    }

    @Transactional
    public List<QuestionDto> findFromAuthor(UserDto user, String username) {
        if(user==null){
            User us=factory.createUserReposytory().findByUsername(username).orElseThrow(QuestionNotFoundException::new);
            return factory.createQuestionRepository().findByAuthor(us).stream().map(QuestionDto::questionDtoFromQuestion).collect(Collectors.toList());

        }else {
            User us=factory.createUserReposytory().findByUsername(user.getUsername()).orElseThrow(QuestionNotFoundException::new);
            return factory.createQuestionRepository().findByAuthor(us).stream().map(QuestionDto::questionDtoFromQuestion).collect(Collectors.toList());
        }
    }

    @Transactional
    public List<QuestionDto> findAll() {
        return factory.createQuestionRepository().findAll().stream().map(QuestionDto::questionDtoFromQuestion).collect(Collectors.toList());
    }

    @Transactional
    public QuestionDto findById(Integer id) {
        Question question =factory.createQuestionRepository().findById(id).orElseThrow(QuestionNotFoundException::new);
        return QuestionDto.questionDtoFromQuestion(question);
    }

    @Transactional
    public void remove(QuestionDto question, Integer id) {
        if(question==null) {
            Question q = factory.createQuestionRepository().findById(id).orElseThrow(QuestionNotFoundException::new);
            factory.createQuestionRepository().remove(q);
        }else{
            ArrayList<Question> questions = (ArrayList<Question>) factory.createQuestionRepository().findAll();
            for(Question q : questions){
                if(q.getBody().equals(question.getBody()) && q.getAuthor().getUsername()==question.getAuthor()){
                    factory.createQuestionRepository().remove(q);
                    break;
                }
            }
        }
    }

    @Transactional
    public ArrayList<AnswareDto> findAnswaresForQuestion(Integer id){
        System.out.println(id);
        Question q = factory.createQuestionRepository().findById(id).orElseThrow(QuestionNotFoundException::new);
        return (ArrayList<AnswareDto>) q.getAnswares().stream().map(AnswareDto::answareDtoFromAnsware).collect(Collectors.toList());

    }
}
