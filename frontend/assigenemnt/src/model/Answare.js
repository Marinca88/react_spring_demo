class Answare {
    constructor() {
        this.state = {
            answares: []
        };
    }

    addAnswares(answares) {
        this.state.answares = [
            ...answares
        ];
    }

    removeAnsware(index) {
        this.state.answares = this.state.answares.filter(element => this.state.answares.indexOf(element) != index);
    }

    editAnsware(index, text) {
        console.log( "before " + this.state.answares[index].text)
        this.state.answares[index].text = text;
        console.log("after " + this.state.answares[index].text)
    }

    getAnsware(index) {

        return this.state.answares.filter(element => element.referenceQuestion==index)
    }

    getAnswares() {
        console.log(this.state.answares)
        return this.state.answares;
    }

    upvote(index) {
        console.log("before " +index + " " + this.state.answares[index].upvotes)
        this.state.answares[index].upvotes = this.state.answares[index].upvotes + 1;
        console.log("after " +index + " " +this.state.answares[index].upvotes)
    }

    downvote(index) {
        this.state.answares[index].downvotes = this.state.answares[index].downvotes + 1;
    }

    getVotes(index) {
        var votes = this.state.answares[index].upvotes - this.state.answares[index].downvotes
        return votes
    }

}
const AnswareInstance = new Answare();
export default AnswareInstance;