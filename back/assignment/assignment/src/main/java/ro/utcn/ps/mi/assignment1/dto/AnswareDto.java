package ro.utcn.ps.mi.assignment1.dto;

import lombok.Data;
import ro.utcn.ps.mi.assignment1.entity.Answare;

import java.time.LocalDateTime;

@Data
public class AnswareDto {
    private Integer upvotes;
    private Integer downvotes;
    private String text;
    private String creation_date;
    private Integer question_id;
    private String author;

    public static AnswareDto answareDtoFromAnsware (Answare answare){
        AnswareDto answareDto = new AnswareDto();
        answareDto.setUpvotes(answare.getUpvotes());
        answareDto.setDownvotes(answare.getDownvotes());
        answareDto.setCreation_date(answare.getCreation_date().toString());
        answareDto.setText(answare.getText());
        answareDto.setQuestion_id(answare.getQuestion_id());
        answareDto.setAuthor(answare.getAuthor().getUsername());
        return answareDto;
    }
}
