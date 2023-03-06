package ro.utcn.ps.mi.assignment1.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.AppUserRole;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JDBCUserRepository  implements UserRepository {
    private final JdbcTemplate template;
    @Override
    public User save(User user) {
        if (user.getId() != null) {
            update(user);
        } else {
            Integer id = insert(user);
            user.setId(id);
        }

        return user;
    }

    @Override
    public void remove(User user) {
        template.update("DELETE FROM user WHERE id = ?", user.getId());
    }

    @Override
    public Optional<User> findByUsername(String string) {

        List<User> user = template.query("SELECT * FROM user WHERE username=?", ((resultSet, i) -> new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("app_user_role").equals("USER") ? AppUserRole.USER : AppUserRole.MODERATOR,
                        resultSet.getBoolean("locked"),
                        resultSet.getBoolean("enabled"),
                        resultSet.getInt("score"))),
               string);
        if (user.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(user.get(0));
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        List<User> user = template.query("SELECT * FROM user WHERE id=?", ((resultSet, i) -> new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("app_user_role").equals("USER") ? AppUserRole.USER : AppUserRole.MODERATOR,
                        resultSet.getBoolean("locked"),
                        resultSet.getBoolean("enabled"),
                        resultSet.getInt("score"))),
                id);
        if (user.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(user.get(0));
        }
    }

    private void update(User user) {
        template.update("UPDATE user SET email = ?, name = ?, password =?, score=? WHERE id= ?",
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getScore(),
                user.getId());
        template.update("DELETE FROM answares_voted_by WHERE voted_by_id = ?",user.getId());
    }

    private int insert(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("user");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> data = new HashMap<>();
        data.put("app_user_role", user.getAppUserRole().name());
        data.put("email",user.getEmail());
        data.put("enabled",user.getEnabled());
        data.put("locked",user.getLocked());
        data.put("name",user.getName());
        data.put("password",user.getPassword());
        data.put("score",user.getScore());
        data.put("username",user.getUsername());
        return insert.executeAndReturnKey(data).intValue();
    }
}

