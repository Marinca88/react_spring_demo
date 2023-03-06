package ro.utcn.ps.mi.assignment1.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.ps.mi.assignment1.entity.*;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionVotesRepository;
import java.util.*;

@RequiredArgsConstructor
public class JDBCQuestionVotesRepository implements QuestionVotesRepository {

    private final JdbcTemplate template;

    @Override
    public QuestionVotes save(QuestionVotes vote) {
        if (vote.getId() != null) {
            return vote;
        } else {
            Integer id = insert(vote);
            vote.setId(id);
        }

        return vote;
    }


    private int insert(QuestionVotes vote) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question_votes");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", vote.getUser().getId());
            data.put("question_question_id", vote.getQuestion().getQuestionId());
            data.put("type", VotesType.QUESTION.name().toLowerCase());
        return insert.executeAndReturnKey(data).intValue();
    }

    @Override
    public List<QuestionVotes> findAll() {

        List<Vote> votes =template.query("SELECT * FROM question_votes",
                (resultSet, i) ->  new Vote(resultSet.getInt("vote_id"),resultSet.getInt("user_id"),resultSet.getInt("question_question_id"),resultSet.getString("type")));
        ArrayList<QuestionVotes> questions = new ArrayList<QuestionVotes>();
        for(Vote v: votes){
            questions.add(v.questionVotesFromVote(v,template));
        }
        return questions;
    }

    private class Vote {
        private Integer id;
        private  Integer user_id;
        private Integer question_id;
        private  String type;

        public Vote(Integer id, Integer user_id, Integer question_id, String type) {
            this.id = id;
            this.user_id = user_id;
            this.question_id = question_id;
            this.type = type;
        }



        public QuestionVotes questionVotesFromVote(Vote vote,JdbcTemplate template){
            QuestionVotes questionVotes = new QuestionVotes();
            List<User> users = template.query("SELECT * FROM user WHERE user_id= ?",(resultSet,i) -> new User(resultSet.getInt("id"), resultSet.getString("name"),resultSet.getString("username"), resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("app_uesre_role").equals("User") ? AppUserRole.USER: AppUserRole.MODERATOR, resultSet.getBoolean("locked"),resultSet.getBoolean("enabled"),resultSet.getInt("score")),vote.getUser_id());
            if(!users.isEmpty()){
                questionVotes.setUser(users.get(0));
            }
            List<Question> questions = template.query("SELECT * FROM question WHERE question_id = ?",
                    ((resultSet, i) -> new Question(resultSet.getInt("question_id"),
                            resultSet.getString("title"),
                            resultSet.getString("body"),
                            resultSet.getTimestamp("create_time").toLocalDateTime())),
                    vote.getQuestion_id());
            List<User> author = template.query("SELECT * FROM user LEFT JOIN question ON id=author_id WHERE question_id=?", ((resultSet, i) -> new User(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("app_user_role").equals("USER") ? AppUserRole.USER : AppUserRole.MODERATOR,
                            resultSet.getBoolean("locked"),
                            resultSet.getBoolean("enabled"),
                            resultSet.getInt("score"))),
                    questions.get(0).getQuestionId());
                    questions.get(0).setAuthor(author.get(0));
                    questionVotes.setQuestion(questions.get(0));
                    questionVotes.setType("question");
                    questionVotes.setId(vote.getId());
                    return  questionVotes;
        }

        public Integer getId() {
            return id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public Integer getQuestion_id() {
            return question_id;
        }

        public String getType() {
            return type;
        }
    }
}
