package ro.utcn.ps.mi.assignment1.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.ps.mi.assignment1.entity.*;
import ro.utcn.ps.mi.assignment1.persistance.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
// The Order ensures that this CommandLineRunner is ran first (before the ConsoleController if you implement that one too)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Seed{/*implements CommandLineRunner {

    private final RepositoryFactory factory;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        QuestionRepository repository = factory.createQuestionRepository();
        TagRepository tagRepository = factory.createTagReposytory();
        UserRepository userRepository = factory.createUserReposytory();
        AnswareRepository answareRepository = factory.createAnswareRepository();
        PasswordEncoder p = new PasswordEncoder();
        userRepository.save(new User(
                "user1",
                "username1",
                "user1@yahoo..com",
                 p.bCryptPasswordEncoder().encode("password"),
                AppUserRole.USER,
                false,
                true,
                0));

    }
    */
}
