package ro.utcn.ps.mi.assignment1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ro.utcn.ps.mi.assignment1.dto.*;
import ro.utcn.ps.mi.assignment1.event.QuestionUpdatedEvent;
import ro.utcn.ps.mi.assignment1.persistance.api.QuestionComparator;
import ro.utcn.ps.mi.assignment1.service.*;

import java.util.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/stackoverflow")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService service;
    private final TagService tagService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UDetailsService uDetailsService;
    private  final VotesService votesService;

    @GetMapping
    public List<QuestionDto> seeAllQuestions() {
        List<QuestionDto> questions = service.findAll();
        //Collections.sort(questions, new QuestionComparator());
        for (QuestionDto q : questions)
            System.out.println(q.getTitle());
        return questions;
    }

    @PostMapping(value = "/createQuestion")
    public MessageDto saveQuestion(@RequestBody QuestionDto question) {
        System.out.println("Let's save  the question"+" "+question.getTitle());
        //User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(){
        service.save(question, null);
        return new MessageDto("Saved");
    }

    @PutMapping(value = "/updateQuestion{id}")
    public String updateQuesion(@RequestBody QuestionDto question, @PathVariable("id") Integer id) {
        QuestionDto q = service.findById(id);
        q.setTitle(question.getTitle());
        q.setBody(question.getBody());
        service.save(q, id);
        return "updated";
    }



    @PutMapping(value = "/upvoteQuestion")
    public MessageDto upvoteQuestion(@RequestBody QuestionDto question){
       String v =service.upVote(question);
       return new MessageDto(v);
    }

    @PutMapping(value = "/uvoteQuestion")
    public List<QuestionDto> uvoteQuestion(@RequestBody QuestionDto question){
        service.upVote(question);
        return service.findAll();
    }

    @PutMapping(value = "/downQuestion")
    public MessageDto downQuestion(@RequestBody QuestionDto question){
        String v =service.downVote(question);
        return new MessageDto(v);
    }

    @PutMapping(value = "/dvoteQuestion")
    public List<QuestionDto> dvoteQuestion(@RequestBody QuestionDto question){
        service.downVote(question);
        return service.findAll();
    }

    @PostMapping(value = "/search")
    public List<QuestionDto> serach(@RequestParam String text) {
        List<QuestionDto> questions = new ArrayList<QuestionDto>();
        List<QuestionDto> fromText = service.findFromText(text);
        if (!fromText.isEmpty()) {
            questions.addAll(fromText);
        }
        List<QuestionDto> allQuesions = service.findAll();
        List<TagDto> findFromTag = tagService.findFromText(text);
        for (TagDto tag : findFromTag)
            for (QuestionDto question : allQuesions)
                if (question.getTags().contains(tag.getText()) && !questions.contains(question)) {
                    questions.add(question);
                }

        //Collections.sort(questions, new QuestionComparator());
        return questions;
    }

    @DeleteMapping(value = "/remove{id}")
    public String remove(@PathVariable("id") Integer id) {
        System.out.println(id);
        service.remove(null, id);
        return "removed";
    }

    @GetMapping(value = "/myQuestions")
    public List<QuestionDto> myQuestions() {
        // User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByUsername("vasile");
        List<QuestionDto> questions = service.findFromAuthor(user, "");
        Collections.sort(questions, new QuestionComparator());
        return questions;
    }

    @GetMapping(value = "/question{id}")
    public QuestionDto viiewQuestion(@PathVariable("id") Integer id) {
        QuestionDto question = service.findById(id);
        String s = question.toString();
        return question;
    }

    @EventListener(QuestionUpdatedEvent.class)
    public void handleQuestionCreated(QuestionUpdatedEvent event) {
        log.info("Got an event:{}", event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
