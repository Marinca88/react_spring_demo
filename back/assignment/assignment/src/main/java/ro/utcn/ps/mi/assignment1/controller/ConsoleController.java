package ro.utcn.ps.mi.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.utcn.ps.mi.assignment1.entity.*;
import ro.utcn.ps.mi.assignment1.service.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleController {/*implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final QuestionService questionService;
    private  final AnswareService answareService;
    private  final UserService userService;
    private  final TagService tagService;

    @Override
    public void run(String... args) {
        print("Welcome to my awesome reinterpretation of StackOverflow.");
        boolean done = false;
        while (!done) {
            print("Enter a command: ");
            String command = scanner.next().trim();
            try {
                done = handleCommand(command);
            } catch (QuestionNotFoundException questionNotFoundException) {
                print("The question with the given ID was not found!");
            }
        }
    }

    private boolean handleCommand(String command) {
        switch (command) {
            case "findQuestionFromText":
                serchList();
                return false;
            case "insertQuestion":
                handleInsert();
                return false;
            case "removeQuestion":
                handleRemove();
                return false;
            case "findQuestionFromAuthor":
                serchListOnAuthor();
                return false;
            case "seeAllQuestions":
                handleAll();
                return false;
            case "saveAnsware":
                saveAnsware();
                return false;
            case "updateAnsware":
                updateAnsware();
                return false;
            case "removeAnsware":
                removeAmsware();
                return false;
            case "saveUser":
                saveUser();
                return false;
            case "updateUser":
                updateUser();
                return false;
            case "removeUser":
                removeUser();
                return false;
            case "saveTag":
                saveTag();
                return false;
            case "removeTag":
                removeTag();
                return false;
            case "findTagFromText":
                findTag();
                return false;
            case "exit":
                return true;
            default:
                print("Unknown command. Try again.");
                return false;
        }
    }
    private  void saveTag(){
        print("text:");
        String text = scanner.next().trim();
        Tag tag = new Tag(text);
        List<Question> questions =questionService.findFromText("Body");
        tag.setQuestions(questions);
        print("Insert true/Update false");
        Boolean ver= scanner.nextBoolean();
        if(!ver){
            print("text:");
            String s = scanner.next().trim();
            Tag t = tagService.findFromText(s).get(0);
            t.setText(text);
            tagService.save(t);
        }else{
            tagService.save(tag);
        }
    }

    private void removeTag(){
        print("text:");
        String s = scanner.next().trim();
        Tag t = tagService.findFromText(s).get(0);
        tagService.remove(t);
    }
    private void findTag(){
        print("text:");
        String s = scanner.next().trim();
        System.out.println( tagService.findFromText(s).get(0).getText());
    }

    private void saveUser(){
        print("name:");
        String text = scanner.next().trim();
        print("email:");
        String email = scanner.next().trim();
        print("password:");
        String password = scanner.next().trim();
        print("username:");
        String username= scanner.next().trim();
        User user = new User(text,username,email,password,AppUserRole.USER,true,true,0);
        userService.save(user);

    }

    private  void  updateUser(){
        print("Username:");
        String  username = scanner.next().trim();
        User user = userService.findByUsername(username);
        print("email:");
        String email= scanner.next().trim();
        print("password");
        String password = scanner.next().trim();
        print("new_name");
        String  name = scanner.next().trim();
        print("score");
        Integer score = scanner.nextInt();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setScore(score);
        userService.save(user);

    }

    private void removeUser(){
        print("Username:");
        String  username = scanner.next().trim();
        User user = userService.findByUsername(username);
        userService.removeUser(user);
    }

    private void  saveAnsware(){
        print("text:");
        String  text = scanner.next().trim();
        User user = new User( 1,"user1","username1","email1@yahoo.com","password", AppUserRole.USER,true,true,0);
        Answare answare = new Answare(0,0,text,LocalDateTime.now());
        answare.setAuthor(user);
        answare.setQuestion_id(1);
        answareService.save(answare);

    }

    private void updateAnsware(){
        print("id");
        Integer id = scanner.nextInt();
        Answare answare=answareService.findById(id);
        print("edit|+ for upvotes|- downvotes");
        String op= scanner.next().trim();
        if(op.equals("edit")) {
            print("Text");
            String text = scanner.next().trim();
            answare.setText(text);
            answareService.save(answare);
        }
        if(op.equals("+")){
            answare.setUpvotes(answare.getUpvotes()+1);
            answareService.save(answare);
        }
        if(op.equals("-")){
            answare.setDownvotes(answare.getDownvotes()-1);
            answareService.save(answare);
        }
    }

    private void removeAmsware(){
        print("id");
        Integer id = scanner.nextInt();
        Answare answare=answareService.findById(id);
        answareService.remove(answare);
    }

    private void handleAll() {
        questionService.findAll().forEach(question -> print(question.toString()));
    }

    private void serchListOnAuthor() {
        print("Author:");
        String author = scanner.next().trim();
        print(author);
        questionService.findFromAuthor(null,author).forEach(question -> print(question.toString()));
    }

    private void serchList(){
        print("search text:");
        String text = scanner.next().trim();
        questionService.findFromText(text).forEach(question -> print(question.toString()+ " "+ question.getAnswares() + question.getAuthor().toString()));

    }


    private void handleInsert() {
        print("Title:");
        String title = scanner.next().trim();
        print("Body:");
        String body = scanner.next().trim();
        print("Insert true/Update false");
        Boolean ver= scanner.nextBoolean();
        User user = new User( 1,"User1","username1","email1@yahoo.com","password", AppUserRole.USER,true,true,0);
        Tag tag  = new Tag(1,"Text1");
        if(!ver){
            print("Id");
            int var = scanner.nextInt();
            Question question = questionService.findById(var);
            question.setBody(body);
            question.setTitle(title);
            question.setAuthor(user);
            question.setTags(Arrays.asList(tag));
            questionService.save(question);
        }else {
            Question question =new Question(title, body, LocalDateTime.now());
            question.setAuthor(user);
            question.setTags(Arrays.asList(tag));
            questionService.save(question);
        }
    }

    private void handleRemove() {
        print("Question ID:");
        int id = scanner.nextInt();
        questionService.remove(null,id);
    }

    private void print(String value) {
        System.out.println(value);
    }*/
}
