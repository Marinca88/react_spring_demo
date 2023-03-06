package ro.utcn.ps.mi.assignment1.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.AppUserRole;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.AnswareRepository;

import java.util.*;

@RequiredArgsConstructor
public class JDBCAnswareRepository implements AnswareRepository {

    private final JdbcTemplate template;

    @Override
    public Answare save(Answare answare) {
        if (answare.getId() != null) {
            update(answare);
        } else {
            Integer id = insert(answare);
            answare.setId(id);
        }

        return answare;
    }

    @Override
    public void remove(Answare answare) {
        template.update("DELETE FROM answare WHERE id = ?", answare.getId());
    }

    @Override
    public Optional<Answare> findById(Integer integer) {
        System.out.println(integer);
         List<Answare> answares = template.query("SELECT * FROM answare where id=?",((resultSet, i) ->new Answare(resultSet.getInt("id"),
                 resultSet.getInt("upvotes"), resultSet.getInt("downvotes"), resultSet.getString("text"), resultSet.getTimestamp("creation_date").toLocalDateTime())),integer);
        if (answares.isEmpty()) {
            return Optional.empty();
        } else {
            for(Answare a : answares){
                List<User> user = template.query("SELECT * FROM user LEFT JOIN answare ON author_id=user.id WHERE answare.id=?", ((resultSet, i) -> new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("app_user_role").equals("USER") ? AppUserRole.USER : AppUserRole.MODERATOR,
                        resultSet.getBoolean("locked"),
                        resultSet.getBoolean("enabled"),
                        resultSet.getInt("score"))),a.getId());
                if(!user.isEmpty()){
                  a.setAuthor(user.get(0));
                }
            }
            return Optional.of(answares.get(0));
        }

    }

    @Override
    public List<Answare> findAll() {
        List<Answare> answares = template.query("SELECT * FROM answare ",((resultSet, i) ->new Answare(resultSet.getInt("id"),
                resultSet.getInt("upvotes"), resultSet.getInt("downvotes"), resultSet.getString("text"), resultSet.getTimestamp("creation_date").toLocalDateTime())));
            for(Answare a : answares){
                List<User> user = template.query("SELECT * FROM user LEFT JOIN answare ON author_id=user.id WHERE answare.id=?", ((resultSet, i) -> new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("app_user_role").equals("USER") ? AppUserRole.USER : AppUserRole.MODERATOR,
                        resultSet.getBoolean("locked"),
                        resultSet.getBoolean("enabled"),
                        resultSet.getInt("score"))),a.getId());
                if(!user.isEmpty()){
                    a.setAuthor(user.get(0));
                }
            }
            return answares;
    }


    private void update(Answare answare) {
        template.update("UPDATE answare SET upvotes = ?, downvotes = ?, text = ? WHERE id= ?",
                answare.getUpvotes(),
                answare.getDownvotes(),
                answare.getText(),
                answare.getId());
    }

    private int insert(Answare answare) {
        System.out.println(answare.getAuthor().getName());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answare");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> data = new HashMap<>();
        data.put("upvotes", answare.getUpvotes());
        data.put("downvotes", answare.getDownvotes());
        data.put("text",answare.getText());
        data.put("creation_date",answare.getCreation_date());
        data.put("author_id",answare.getAuthor().getId());
        data.put("answares_question_id",answare.getQuestion_id());
        return insert.executeAndReturnKey(data).intValue();
    }
}
