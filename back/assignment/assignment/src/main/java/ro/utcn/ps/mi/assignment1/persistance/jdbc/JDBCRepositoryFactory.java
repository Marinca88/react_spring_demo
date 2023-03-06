package ro.utcn.ps.mi.assignment1.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ro.utcn.ps.mi.assignment1.persistance.api.*;

@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "repository-type", havingValue = "JDBC")
public class JDBCRepositoryFactory implements RepositoryFactory{

    private final JdbcTemplate template;

    @Override
    public QuestionRepository createQuestionRepository() {
        return new JDBCQuestionRepository(template);
    }

    @Override
    public AnswareRepository createAnswareRepository() {
        return new JDBCAnswareRepository(template);
    }

    @Override
    public TagRepository createTagReposytory() {
        return new JDBCTTagRepository(template);
    }

    @Override
    public UserRepository createUserReposytory() {
        return new JDBCUserRepository(template);
    }

    @Override
    public QuestionVotesRepository createQuestionVotesRepository() {
        return new JDBCQuestionVotesRepository(template);
    }

    @Override
    public AnswareVotesRepository createAnswareVotesRepository() {
        return new JDBCAnswareVotesRepository(template);
    }

}
