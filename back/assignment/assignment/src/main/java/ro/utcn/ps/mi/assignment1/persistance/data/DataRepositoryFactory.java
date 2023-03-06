package ro.utcn.ps.mi.assignment1.persistance.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.ps.mi.assignment1.persistance.api.*;

@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "repository-type", havingValue = "DATA")
public class DataRepositoryFactory implements RepositoryFactory {
    private final DataQuestionRepository dataQuestionRepository;
    private final DataAnswareRepository dataAnswareRepository;
    private final  DataTagRepository dataTagRepository;
    private final  DataUserRepository dataUserRepository;
    private final DataQuestionVotesRepository dataVotesRepository;
    private final  DataAnswareVotesRepository dataAnswareVotesRepository;


    @Override
    public DataQuestionRepository createQuestionRepository() {
        return dataQuestionRepository;
    }

    @Override
    public AnswareRepository createAnswareRepository() {
        return dataAnswareRepository;
    }

    @Override
    public TagRepository createTagReposytory() {
        return dataTagRepository;
    }

    @Override
    public UserRepository createUserReposytory() {
        return dataUserRepository;
    }

    @Override
    public QuestionVotesRepository createQuestionVotesRepository() {
        return  dataVotesRepository;
    }

    @Override
    public AnswareVotesRepository createAnswareVotesRepository() {
        return dataAnswareVotesRepository;
    }


}
