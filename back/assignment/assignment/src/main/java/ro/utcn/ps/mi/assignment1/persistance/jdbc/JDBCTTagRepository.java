package ro.utcn.ps.mi.assignment1.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.persistance.api.TagRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JDBCTTagRepository implements TagRepository {

    private final JdbcTemplate template;
    @Override
    public Tag save(Tag tag) {
        if (tag.getId() != null) {
            update(tag);
        } else {
            Integer id = insert(tag);
            tag.setId(id);
        }

        return tag;
    }

    @Override
    public void remove(Tag tag) {
        template.update("DELETE FROM tag WHERE id = ?", tag.getId());
    }

    @Override
    public List<Tag> findByText(String text) {
        List<Tag> result = new ArrayList<Tag>();
        List<Tag> tags = template.query("SELECT * FROM tag",((resultSet, i) ->new Tag(resultSet.getInt("id"), resultSet.getString("text"))));
        for(Tag t:tags){
            if(t.getText().contains(text)){
                result.add(t);
            }
        }
        for(Tag t : result){
            List<Question> questions =template.query("SELECT * FROM question LEFT JOIN tags ON question_id=questions_question_id WHERE tags_id=?",
                    (resultSet, i) -> new Question(resultSet.getInt("question_id"),
                            resultSet.getString("title"),
                            resultSet.getString("body"),
                            resultSet.getTimestamp("create_time").toLocalDateTime()),t.getId());
            if(questions.isEmpty()){
                t.setQuestions(new ArrayList<>());
            }else{
                t.setQuestions(questions);
            }
        }
        return result;
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = template.query("SELECT * FROM tag",((resultSet, i) ->new Tag(resultSet.getInt("id"), resultSet.getString("text"))));
        return tags;
    }


    private void update(Tag tag) {
        template.update("UPDATE tag SET text = ? WHERE id= ?",
                tag.getText(),
                tag.getId());
    }

    private int insert(Tag tag) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("tag");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> data = new HashMap<>();
        data.put("text", tag.getText());
        return insert.executeAndReturnKey(data).intValue();

    }
}
