class Votes {
    constructor() {
        this.state = {
            votes: [
                {
                    user: "Username2",
                    type: "question",
                    id: 0
                },
                {
                    user: "Username2",
                    type: "question",
                    id: 1
                },
                {
                    user: "Username2",
                    type: "answare",
                    id: 2
                },
                {
                    user: "Username1",
                    type: "answare",
                    id: 2
                }
            ]
        }
    }

    addVote(newVote) {
        console.log("am ajuns aici")
        this.state.votes = [
            ...this.state.votes,
            {
                ...newVote
            }
        ];
    }

    getVotesBy(username) {
        return this.state.votes.filter(item => item.user === username);
    }
}
const VotesInstance = new Votes();
export default VotesInstance;