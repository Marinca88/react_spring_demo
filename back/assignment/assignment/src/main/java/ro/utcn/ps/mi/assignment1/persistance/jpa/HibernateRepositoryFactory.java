package ro.utcn.ps.mi.assignment1.persistance.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.ps.mi.assignment1.persistance.api.*;
import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "repository-type", havingValue = "HIBERNATE")
public class HibernateRepositoryFactory implements RepositoryFactory {
    private final EntityManager entityManager;
    @Override
    public QuestionRepository createQuestionRepository() {
        return new HibernateQuestionRepository(entityManager);
    }

    @Override
    public AnswareRepository createAnswareRepository() {
        return  new HibernateAnswareRepository(entityManager);
    }

    @Override
    public TagRepository createTagReposytory() {
        return new HibernateTagRepository(entityManager);
    }

    @Override
    public UserRepository createUserReposytory() {
        return new HibernateUserRepository(entityManager);
    }

    @Override
    public QuestionVotesRepository createQuestionVotesRepository() {
        return new HibernateQuestionVotesRepository(entityManager);
    }

    @Override
    public AnswareVotesRepository createAnswareVotesRepository() {
        return new HiberanteAnswareVotesRepository(entityManager);
    }


}
