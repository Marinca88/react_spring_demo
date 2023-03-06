package ro.utcn.ps.mi.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.ps.mi.assignment1.dto.AnswareDto;
import ro.utcn.ps.mi.assignment1.dto.MessageDto;
import ro.utcn.ps.mi.assignment1.dto.UserDto;
import ro.utcn.ps.mi.assignment1.dto.VotesDto;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.service.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value="/stackoverflow")
@RestController
public class AnswareControler {
    private final UserService userService;
    private final AnswareService answareService;
    private final QuestionService questionService;
    private  final VotesService votesService;
    private final UDetailsService uDetailsService;

    @GetMapping(value = "/allAnswares")
    public ArrayList<AnswareDto> findAllAnswares(){
        return  (ArrayList<AnswareDto>)answareService.findAll();
    }

    @PostMapping(value = "/createAnsware")
    public ArrayList<AnswareDto> createAnsware(@RequestBody AnswareDto answare){
        answare.setQuestion_id(answare.getQuestion_id()+1);
        answareService.save(answare,null);
        return questionService.findAnswaresForQuestion(answare.getQuestion_id());
    }

    @GetMapping(value = "/questionAnswares{id}")
    public ArrayList<AnswareDto> getAnswaresForQuestions(@PathVariable("id") Integer id){
        return questionService.findAnswaresForQuestion(id+1);
    }

    @PutMapping(value = "/updateAnsware{id}")
    public MessageDto changeText(@RequestParam("text") String text, @PathVariable("id") Integer id){
        AnswareDto answare= answareService.findById(id+1);
        answare.setText(text);
        answareService.save(answare,id+1);
        return new MessageDto("Updated");
    }

    @PutMapping(value = "/like{id}")
    public ArrayList<AnswareDto>  addVote(@PathVariable("id") Integer id,@RequestParam Integer questionId){
        User user = uDetailsService.loadCurrentUser();
        AnswareDto answare = answareService.findById(id+1);
        System.out.println(answare.getText());
        boolean ver = false;
        for(VotesDto v : votesService.findAllAnswares()) {
                if(v.getUser().equals(user.getUsername())){
                    if(v.getId()==id+1){
                        ver=true;
                    }
                }

        }
        if(!ver){
            answare.setUpvotes(answare.getUpvotes()+1);
            VotesDto v = new VotesDto();
            v.setId(id+1);
            v.setUser(user.getUsername());
            v.setType("answare");
            votesService.saveAnswareVote(v);
            answareService.save(answare,id+1);
        }
        for(AnswareDto a :  questionService.findAnswaresForQuestion(questionId+1)){
            System.out.println(a.getText());
        }
        return questionService.findAnswaresForQuestion(questionId+1);
    }

    @PutMapping(value = "/dislike{id}")
    public ArrayList<AnswareDto> subVote(@PathVariable("id") Integer id,@RequestParam Integer questionId){
        User  user = uDetailsService.loadCurrentUser();
        AnswareDto answare = answareService.findById(id+1);
        boolean ver = false;
        for(VotesDto v : votesService.findAllAnswares()) {
                if(v.getUser().equals(user.getUsername())){
                    if(v.getId()==id+1){
                        ver=true;
                    }
                }
        }
        if(!ver){
            answare.setDownvotes(answare.getDownvotes()+1);
            VotesDto v = new VotesDto();
            v.setId(id+1);
            v.setUser(user.getUsername());
            v.setType("answare");
            votesService.saveAnswareVote(v);
            answareService.save(answare,id+1);
        }
        return questionService.findAnswaresForQuestion(questionId+1);
    }

}
