package ro.utcn.ps.mi.assignment1.persistance.memory;

import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionRepository;

import java.util.*;

public class InMemoryQuestionRepository implements QuestionRepository {

    private volatile int currentId = 1;
    private final Map<Integer, Question> data = new HashMap<>();

    @Override
    public synchronized Question save(Question question) {
        if (question.getQuestionId() != null) {
            data.put(question.getQuestionId(), question);
        } else {
            question.setQuestionId(currentId++);
            data.put(question.getQuestionId(), question);
        }

        return question;

    }


    @Override
    public List<Question> findByText(String text) {
        List<Question> result = new ArrayList<Question>();
        for(Question question: data.values()){
            if(question.getBody().contains(text)||question.getTitle().contains(text)){
                result.add(question);
            }
        }
        return result;
    }

    @Override
    public Optional<Question> findById(Integer integer) {
        return Optional.ofNullable(data.get(integer));
    }

    @Override
    public List<Question> findByAuthor(User user) {
        List<Question> result = new ArrayList<Question>();
        for(Question question: data.values()){
            if(question.getAuthor().getUsername().equals(user.getUsername())){
                result.add(question);
            }
        }
        return result;
    }

    @Override
    public synchronized void remove(Question question) {
        data.remove(question.getQuestionId());
    }


    @Override
    public List<Question> findAll() {
        return new ArrayList<>(data.values());
    }


}
