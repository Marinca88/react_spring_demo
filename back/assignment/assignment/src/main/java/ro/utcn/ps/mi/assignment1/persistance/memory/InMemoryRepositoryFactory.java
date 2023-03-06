package ro.utcn.ps.mi.assignment1.persistance.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.ps.mi.assignment1.persistance.api.*;

@Component
@ConditionalOnProperty(name = "repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {

    private final InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
    private  final InMemoryAnswareReposytory inMemoryAnswareReposytory = new InMemoryAnswareReposytory(inMemoryQuestionRepository);
    private final InMemoryTagReposytory inMemoryTagReposytory = new InMemoryTagReposytory();
    private  final InMemoryUserRepository inMemoryUserRepository= new InMemoryUserRepository();


    @Override
    public QuestionRepository createQuestionRepository() {
        return inMemoryQuestionRepository;
    }

    @Override
    public AnswareRepository createAnswareRepository() {
        return inMemoryAnswareReposytory;
    }

    @Override
    public TagRepository createTagReposytory() {
        return inMemoryTagReposytory;
    }

    @Override
    public UserRepository createUserReposytory() {
        return inMemoryUserRepository;
    }

    @Override
    public QuestionVotesRepository createQuestionVotesRepository() {
        return null;
    }

    @Override
    public AnswareVotesRepository createAnswareVotesRepository() {
        return null;
    }

}

