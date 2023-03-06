package ro.utcn.ps.mi.assignment1.persistance.jdbc;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.ps.mi.assignment1.entity.*;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionRepository;

import java.sql.ResultSet;
import java.util.*;

@RequiredArgsConstructor
public class JDBCQuestionRepository implements QuestionRepository {

    private final JdbcTemplate template;
    @Override
    public Question save(Question question) {
        if (question.getQuestionId() != null) {
            update(question);
        } else {
            Integer id = insert(question);
            question.setQuestionId(id);
        }

        return question;
    }

    private  List<Question> getExtraData(List<Question> questions){
        for(Question q: questions) {
            List<User> author = template.query("SELECT * FROM user LEFT JOIN question ON id=author_id WHERE question_id=?", ((resultSet, i) -> new User(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("app_user_role").equals("USER") ? AppUserRole.USER : AppUserRole.MODERATOR,
                            resultSet.getBoolean("locked"),
                            resultSet.getBoolean("enabled"),
                            resultSet.getInt("score"))),
                    q.getQuestionId());
            if (!author.isEmpty()) {
                q.setAuthor(author.get(0));
            }
            List<Answare> answares = template.query("SELECT * FROM answare LEFT JOIN question ON answares_question_id=question_id WHERE question_id=?",((resultSet, i) ->new Answare(resultSet.getInt("id"),
                    resultSet.getInt("upvotes"), resultSet.getInt("downvotes"), resultSet.getString("text"), resultSet.getTimestamp("creation_date").toLocalDateTime())),q.getQuestionId());
            for(Answare a: answares){
                List<User> athor = template.query("SELECT * FROM user LEFT JOIN answare ON author_id=user.id WHERE answare.id=?", ((resultSet, i) -> new User(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("app_user_role").equals("USER") ? AppUserRole.USER : AppUserRole.MODERATOR,
                                resultSet.getBoolean("locked"),
                                resultSet.getBoolean("enabled"),
                                resultSet.getInt("score"))),
                        a.getId());
                if (!author.isEmpty()) {
                    a.setAuthor(author.get(0));
                }
            }
            q.setAnswares(answares);
            List<Tag> tags =template.query("SELECT * FROM  tag LEFT JOIN tags ON id=tags_id WHERE questions_question_id=?",((resultSet, i) ->new Tag(resultSet.getInt("id"), resultSet.getString("text"))),q.getQuestionId());
                q.setTags(tags);
        }
        return questions;
    }

    @Override
    public List<Question> findByText(String text) {
        ArrayList<Question> questions = new ArrayList<Question>();
       for(Question q:findAll()){
           if(q.getBody().contains(text)||q.getTitle().contains(text)){
               questions.add(q);
           }
       }
       return questions;
    }

    @Override
    public Optional<Question> findById(Integer integer) {
        List<Question> questions = template.query("SELECT * FROM question WHERE question_id = ?",
                ((resultSet, i) -> new Question(resultSet.getInt("question_id"),
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getTimestamp("create_time").toLocalDateTime())),
                integer);

        if (questions.isEmpty()) {
            return Optional.empty();
        } else {
            List<Question> q=this.getExtraData(questions);
            return Optional.of(q.get(0));
        }

    }

    @Override
    public List<Question> findByAuthor(User user) {
        List<Question> questions = template.query("SELECT * FROM question WHERE author_id = ?",
                ((resultSet, i) -> new Question(resultSet.getInt("question_id"),
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getTimestamp("create_time").toLocalDateTime())),
                user.getId());
        List<Question> q= this.getExtraData(questions);
        return  q;
    }

    @Override
    public void remove(Question question) {
        template.update("DELETE FROM question WHERE question_id = ?", question.getQuestionId());
    }


    @Override
    public List<Question> findAll() {
        List<Question> questions =template.query("SELECT * FROM question",
                (resultSet, i) -> new Question(resultSet.getInt("question_id"),
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getTimestamp("create_time").toLocalDateTime()));
        return this.getExtraData(questions);
    }


    private void update(Question question) {
        template.update("UPDATE question SET title = ?, body = ?, upvotes = ?, downvotes = ? WHERE question_id = ?",
                question.getTitle(),
                question.getBody(),
                question.getQuestionId(),
                question.getUpvotes(),
                question.getDownvotes()
                );
        template.update("DELETE FROM tags WHERE questions_question_id=?",question.getQuestionId());
        SimpleJdbcInsert insert1 = new SimpleJdbcInsert(template);
        insert1.setTableName("tags");
        Map<String, Object> data1 = new HashMap<>();
        Integer id = question.getQuestionId();
        if(question.getTags()!=null) {
            for (Tag tag : question.getTags()) {
                data1.put("questions_question_id", id);
                data1.put("tags_id", tag.getId());
                insert1.execute(data1);
                data1.clear();
            }
        }

    }

    private int insert(Question question) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question");
        insert.usingGeneratedKeyColumns("question_id");
        Map<String, Object> data = new HashMap<>();
        data.put("title", question.getTitle());
        data.put("body", question.getBody());
        data.put("creation_date",question.getCreateTime());
        data.put("author_id",question.getAuthor().getId());
        data.put("upvotes",question.getUpvotes());
        data.put("downvotes",question.getDownvotes());
       SimpleJdbcInsert insert1 = new SimpleJdbcInsert(template);
        insert1.setTableName("tags");
        Map<String, Object> data1 = new HashMap<>();
        Integer id = insert.executeAndReturnKey(data).intValue();
        if(question.getTags()!=null) {
            for (Tag tag : question.getTags()) {
                data1.put("questions_question_id", id);
                data1.put("tags_id", tag.getId());
                insert1.execute(data1);
                data1.clear();
            }
        }
        return id;
    }


}
