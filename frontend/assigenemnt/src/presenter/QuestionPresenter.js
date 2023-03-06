import QuestionInstance from '../model/Question';
import RestClient from '../rest/RestClient';

//const client = new RestClient("username1", "password");
class QuestionPresenter{


    addTagToQuestion(index, tag) {
        return QuestionInstance.addTagToQuestion(index, tag);
    }
    async addQuestion(newQuestion,client) {
        var message = await client.createQuestion(newQuestion);
    }
    async getQuestion(client){

        var questions = await client.loadAllStudents();
        QuestionInstance.addQuestion(questions);
        return QuestionInstance.getQuestion();
    }

    async getQuestionByText(text,client) {
        var questions = await client.loadSerachedQuestions(text);
        QuestionInstance.removeQuetions();
        QuestionInstance.addQuestion(questions);
        return QuestionInstance.getQuestion();
    }

    async getQuestionById(id,client) {
        var questions = await client.loadAllStudents();
        return questions[id];

    }

    async upvote(question,client) {
        
        var message = await client.loadUpdatedQuestion(question.title, question.body, question.createTime, question.author, question.upvotes, question.downvotes, question.tags);
        console.log(message.message);
        if (message.message === "Voted") {
            return QuestionInstance.upvote(question);
        } else {
            return QuestionInstance.getQuestion();
        }
    }

    async downvote(question,client) {
        var message = await client.loadUpdatedQuestion(question.title, question.body, question.createTime, question.author, question.upvotes, question.downvotes, question.tags);
        console.log(message.message);
        if (message.message === "Voted") {
            return QuestionInstance.downvote(question);
        } else {
            return QuestionInstance.getQuestion();
        }
    }

    async uVote(question, client) {
        var questions = await client.UpVotedQuestion(question.title, question.body, question.createTime, question.author, question.upvotes, question.downvotes, question.tags);
        return questions;
    }
    async dVote(question, client) {
        var questions = await client.DownVotedQuestion(question.title, question.body, question.createTime, question.author, question.upvotes, question.downvotes, question.tags);
        return questions;
    }
}
const QuestionPresenterInstance = new QuestionPresenter();
export default QuestionPresenterInstance;