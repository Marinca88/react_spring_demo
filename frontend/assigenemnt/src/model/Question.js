import { EventEmitter } from "events"
import WebSocketListenerInstance from "../ws/Websocket";

class Question extends EventEmitter{
    constructor() {
        super();
        this.state = {
            questions: []
        };
        WebSocketListenerInstance.on("QUESTION_UPDATED", event => {
            //StudentModelInstance.addStudent(event.student);
            //this.emit("event", this.state.students);
            console.log("am prins mesajul");
        })
    }

    addTagToQuestion(index,tag) {
        this.state.questions[index].tags = [
            ...this.state.questions[index].tags,
            tag
        ];
    }

    addQuestion(questions) {
        this.state.questions = [
            ...questions
        ];
    }

    getQuestion() {
        return this.state.questions;
    }

    removeQuetions() {
        this.state.questions = [];
    }


    getQuestionbyId(id) {
        return this.state.questions[id];
    }

    upvote(question) {
        var index = this.state.questions.indexOf(question);
        this.state.questions[index].upvotes = this.state.questions[index].upvotes + 1;
        return this.state.questions;

    }

    downvote(question) {
        var index = this.state.questions.indexOf(question);
        this.state.questions[index].downvotes = this.state.questions[index].downvotes + 1;
        return this.state.questions;
    }

    getVotes(index) {
        var votes = this.state.questions[index].upvotes - this.state.questions[index].downvotes
        return votes
    }
}
const QuestionInstance = new Question();
export default QuestionInstance;