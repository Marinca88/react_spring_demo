package ro.utcn.ps.mi.assignment1.persistance.api;

import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.User;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question question);

    List<Question> findByText(String text);

    Optional<Question> findById(Integer integer);

    List<Question> findByAuthor(User user);

    void remove(Question question);

    List<Question> findAll();


}
