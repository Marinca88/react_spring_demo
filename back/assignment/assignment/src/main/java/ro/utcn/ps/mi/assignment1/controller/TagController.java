package ro.utcn.ps.mi.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.ps.mi.assignment1.dto.QuestionDto;
import ro.utcn.ps.mi.assignment1.dto.TagDto;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.service.QuestionService;
import ro.utcn.ps.mi.assignment1.service.TagService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(value = "/stackoverflow")
public class TagController {
    private  final TagService tagService;
    private final QuestionService questionService;
    @GetMapping("/sercahTag")
    public List<TagDto> getTag(@RequestParam("text") String text){
        List<TagDto> tags =tagService.findFromText(text);
        return tags;
    }

    @GetMapping("/allTags")
    public ArrayList<TagDto> getAllTags(){
        return (ArrayList<TagDto>) tagService.findAll();
    }

    @PostMapping("/createTag")
    public List<TagDto> createTag(@RequestBody TagDto tag){
        tagService.save(tag);
        return tagService.findAll();
    }
    @PutMapping(value = "/addTag{id}")
    public String addAddTag(@RequestBody Tag tag,@PathVariable("id") Integer id){
        QuestionDto question = questionService.findById(id);
        List<String> tags = question.getTags();
        tags.add(tag.getText());
        question.setTags(tags);
        questionService.save(question,id);
        return "updated";
    }
    @PutMapping(value = "/removeTag{id}")
    public String remove(@RequestBody Tag tag,@PathVariable("id") Integer id){
        QuestionDto question = questionService.findById(id);
        List<String> tags = new ArrayList<String>();
        for(String t: question.getTags())
        /*   if(t)
                tags.add(t);*/
        question.setTags(tags);
       /* questionService.save(question);*/
        return "updated";
    }
}
