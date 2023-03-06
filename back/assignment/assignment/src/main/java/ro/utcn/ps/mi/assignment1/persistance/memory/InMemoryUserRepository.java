package ro.utcn.ps.mi.assignment1.persistance.memory;

import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private volatile int currentId = 1;
    private final Map<Integer, User> data = new HashMap<>();

    @Override
    public synchronized User save(User user) {
        if (user.getId() != null) {
            data.put(user.getId(), user);
        } else {
            user.setId(currentId++);
            data.put(user.getId(), user);
        }

        return user;
    }

    @Override
    public synchronized void remove(User user) {
        data.remove(user.getId());
    }

    @Override
    public Optional<User> findByUsername(String string) {
        User result = null;
        for(User user: data.values()){
            if(user.getUsername().equals(string)) {
                result= user;
            }
        }
       return  Optional.ofNullable(result);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(data.get(id));
    }
}
