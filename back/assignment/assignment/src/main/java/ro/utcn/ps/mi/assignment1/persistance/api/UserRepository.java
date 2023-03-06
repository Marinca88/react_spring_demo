package ro.utcn.ps.mi.assignment1.persistance.api;

import org.springframework.stereotype.Repository;
import ro.utcn.ps.mi.assignment1.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository {
    public User save(User user);
    public void remove(User user);
    public Optional<User> findByUsername(String string);
    public  Optional<User> findById(Integer id);
}
