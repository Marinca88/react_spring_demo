package ro.utcn.ps.mi.assignment1.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDto {
    private String title;
    private String body;
    private LocalDateTime createTime;
    private String author;
    private  List<String> tags;
    private  Integer upvotes;
    private  Integer downvotes;

    public static QuestionDto questionDtoFromQuestion(Question question){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setTitle(question.getTitle());
        questionDto.setBody(question.getBody());
        questionDto.setCreateTime(question.getCreateTime());
        questionDto.setAuthor(question.getAuthor().getUsername());
        questionDto.setUpvotes(question.getUpvotes());
        questionDto.setDownvotes(question.getDownvotes());
        if(!CollectionUtils.isEmpty(question.getTags())){
            questionDto.setTags(question.getTags().stream().map(Tag::getText).collect(Collectors.toList()));
        }else{
            questionDto.setTags(new ArrayList<String>());
        }
        return  questionDto;
    }
}
