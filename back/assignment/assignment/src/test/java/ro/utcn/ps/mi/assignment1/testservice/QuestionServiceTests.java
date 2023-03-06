package ro.utcn.ps.mi.assignment1.testservice;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ro.utcn.ps.mi.assignment1.entity.AppUserRole;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.RepositoryFactory;
import ro.utcn.ps.mi.assignment1.persistance.memory.InMemoryRepositoryFactory;

import java.time.LocalDateTime;

public class QuestionServiceTests {

    private static RepositoryFactory createMockedFactory() {
        RepositoryFactory factory = new InMemoryRepositoryFactory();
        Question q1=new Question( "Title1", "Body1");
        Question g2 = new Question( "Title2", "Body2");
        factory.createUserReposytory().save(new User("Vasile","vasile","vasile1p@yahoo.com","password", AppUserRole.USER,false,true,0));
        q1.setAuthor(factory.createUserReposytory().findByUsername("vasile").get());
        g2.setAuthor(factory.createUserReposytory().findByUsername("vasile").get());
        factory.createQuestionRepository().save(q1);
        factory.createQuestionRepository().save(g2);
        return factory;
    }

    @Test
    public void addQuestionTest() {
        RepositoryFactory factory = createMockedFactory();

        Question question = new Question(3, "Title3", "Body3", LocalDateTime.now());
        factory.createQuestionRepository().save(new Question( "Title3", "Body3"));

        Question foundQuestion = factory.createQuestionRepository().findById(3).get();

        Assert.assertEquals(foundQuestion, question);
    }

    @Test
    public void findAllTest() {
        RepositoryFactory factory = createMockedFactory();

       Assert.assertEquals(factory.createQuestionRepository().findAll().size(), 2);
    }

    @Test
    public void findByText() {
        RepositoryFactory factory = createMockedFactory();

        Assert.assertEquals(factory.createQuestionRepository().findByText("tle").size(), 2);
    }

    @Test
    public void findFromUser() {
        RepositoryFactory factory = createMockedFactory();

        Assert.assertEquals(factory.createQuestionRepository().findByAuthor(factory.createUserReposytory().findByUsername("vasile").get()).size(), 2);
    }
}