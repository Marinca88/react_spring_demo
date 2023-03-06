package ro.utcn.ps.mi.assignment1.persistance.memory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.persistance.api.AnswareRepository;
import ro.utcn.ps.mi.assignment1.service.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class InMemoryAnswareReposytory implements AnswareRepository {

    private volatile int currentId = 1;
    private final Map<Integer, Answare> data = new HashMap<>();
    private  final  InMemoryQuestionRepository inMemoryQuestionRepository;

    public InMemoryAnswareReposytory(InMemoryQuestionRepository inMemoryQuestionRepository){
        this.inMemoryQuestionRepository = inMemoryQuestionRepository;
    }

    @Override
    public synchronized Answare save(Answare answare) {
        Question question = inMemoryQuestionRepository.findById(answare.getQuestion_id()).orElseThrow(UserNotFoundException::new);
        List<Answare> answares = question.getAnswares();
        answares.add(answare);
        question.setAnswares(answares);
        inMemoryQuestionRepository.save(question);
        if (answare.getId() != null) {
            data.put(answare.getId(), answare);
        } else {
            answare.setId(currentId++);
            data.put(answare.getId(), answare);
        }

        return answare;
    }

    @Override
    public synchronized void remove(Answare answare) {
        data.remove(answare.getId());
    }

    @Override
    public Optional<Answare> findById(Integer integer) {
        return Optional.ofNullable(data.get(integer));
    }

    @Override
    public List<Answare> findAll() {
        return null;
    }
}
