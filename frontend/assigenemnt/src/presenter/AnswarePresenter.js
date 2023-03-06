import AnswareInstance from '../model/Answare'
import RestClient from '../rest/RestClient';

//const client = new RestClient("username1", "password");
class AnswarePresenter {
    async addAnsware(newAnsware,client) {
        var answares = await client.loadRefreshedAnswares(newAnsware.upvotes, newAnsware.downvotes, newAnsware.text, newAnsware.referenceQuestion)
        AnswareInstance.addAnswares(answares);
        return AnswareInstance.getAnswares();
    }

    removeAnsware(index) {
        return AnswareInstance.removeAnsware(index);
    }

    async editAnsware(answare,text,client) {
        console.log(answare)
        console.log(text)
        var answares = await client.loadAllAnswares()
        var ind = answares.indexOf(answares.find(element => element.text === text && answare.authorUsername == element.author && element.question_id == (answare.referenceQuestion+1)));
        var el = await client.editAnsware(ind, answare.text);
        if (el.message == "Updated") {
            var returnedAnswares = await client.loadAnswaresForQuestion(answare.referenceQuestion);
            console.log(returnedAnswares)
            AnswareInstance.addAnswares(returnedAnswares)
            return returnedAnswares;
        } else {
            return AnswareInstance.getAnswares()
        }
    }
    async getAnswaresForQuestion(index,client) {
        var answares = await client.loadAnswaresForQuestion(index);
        AnswareInstance.addAnswares(answares);
        return AnswareInstance.getAnswares();
    }
    getAnswares() {
        return AnswareInstance.getAnswares();
    }

    async upvote(index, answare,client) {
        console.log(answare)
        var answares = await client.loadAllAnswares()
        console.log(answares)
        var id = answares.indexOf(answares.find(element => element.text === answare.text && answare.author === element.author && element.question_id === answare.question_id));
        console.log(id)
        var a = await client.upvoteAnsware(id, index);
        console.log(a);
        return a;
    }

    async downvote(index, answare,client) {
        console.log(answare)
        var answares = await client.loadAllAnswares()
        var id = answares.indexOf(answares.find(element => element.text === answare.text && answare.author === element.author && element.question_id === answare.question_id));
        var answares = await client.downvoteAnsware(id, index);
        return answares;
    }

    getVotes(index) {
        return AnswareInstance.getVotes(index);
    }
}
const AnswarePresenterInstance = new AnswarePresenter();
export default AnswarePresenterInstance;