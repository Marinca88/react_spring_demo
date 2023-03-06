package ro.utcn.ps.mi.assignment1.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.ps.mi.assignment1.entity.*;
import ro.utcn.ps.mi.assignment1.persistance.api.AnswareVotesRepository;

import java.util.*;

@RequiredArgsConstructor
public class JDBCAnswareVotesRepository implements AnswareVotesRepository {
    private final JdbcTemplate template;

    @Override
    public AnswareVotes save(AnswareVotes vote) {
        if (vote.getId() != null) {
            return vote;
        } else {
            Integer id = insert(vote);
            vote.setId(id);
        }

        return vote;
    }


    private int insert(AnswareVotes vote) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answare_votes");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", vote.getUser().getId());
        data.put("answare_id", vote.getAnsware().getId());
        data.put("type", VotesType.QUESTION.name().toLowerCase());
        return insert.executeAndReturnKey(data).intValue();
    }

    @Override
    public List<AnswareVotes> findAll() {

        List<Vote> votes =template.query("SELECT * FROM question_votes",
                (resultSet, i) ->  new Vote(resultSet.getInt("vote_id"),resultSet.getInt("user_id"),resultSet.getInt("question_question_id"),resultSet.getString("type")));
        ArrayList<AnswareVotes> questions = new ArrayList<AnswareVotes>();
        for(Vote v: votes){
            questions.add(v.answareVotesFromVote(v,template));
        }
        return questions;
    }

    private class Vote {
        private Integer id;
        private  Integer user_id;
        private Integer answare_id;
        private  String type;

        public Vote(Integer id, Integer user_id, Integer answare_id, String type) {
            this.id = id;
            this.user_id = user_id;
            this.answare_id = answare_id;
            this.type = type;
        }



        public AnswareVotes answareVotesFromVote(Vote vote, JdbcTemplate template){
            AnswareVotes answareVotes = new AnswareVotes();
            List<User> users = template.query("SELECT * FROM user WHERE user_id= ?",(resultSet, i) -> new User(resultSet.getInt("id"), resultSet.getString("name"),resultSet.getString("username"), resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("app_uesre_role").equals("User") ? AppUserRole.USER: AppUserRole.MODERATOR, resultSet.getBoolean("locked"),resultSet.getBoolean("enabled"),resultSet.getInt("score")),vote.getUser_id());
            if(!users.isEmpty()){
                answareVotes.setUser(users.get(0));
            }
            List<Answare> answares = template.query("SELECT * FROM answare where id=?",((resultSet, i) ->new Answare(resultSet.getInt("id"),
                    resultSet.getInt("upvotes"), resultSet.getInt("downvotes"), resultSet.getString("text"), resultSet.getTimestamp("creation_date").toLocalDateTime())),this.id);
            if (!answares.isEmpty()) {
                List<User> user = template.query("SELECT * FROM user LEFT JOIN answare ON author_id=user.id WHERE answare.id=?", ((resultSet, i) -> new User(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("app_user_role").equals("USER") ? AppUserRole.USER : AppUserRole.MODERATOR,
                        resultSet.getBoolean("locked"),
                        resultSet.getBoolean("enabled"),
                        resultSet.getInt("score"))), answares.get(0).getId());
                answares.get(0).setAuthor(user.get(0));
            }
            answareVotes.setAnsware(answares.get(0));
            answareVotes.setType("answare");
            answareVotes.setId(vote.getId());
            return  answareVotes;
        }

        public Integer getId() {
            return id;
        }

        public Integer getUser_id() {
            return user_id;
        }


        public String getType() {
            return type;
        }
    }
}
