package ro.utcn.ps.mi.assignment1.persistance.data;

import org.springframework.data.repository.Repository;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.UserRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface DataUserRepository extends UserRepository, Repository<User,Integer> {
    void delete(User user);

    @Override
    public default void remove(User user) {
        delete(user);
    }

    Optional<User> findByUsername(String s);
}
