import VotesInstance from '../model/Votes';

class VotesPresenter {
    addVote(newVote) {
        return VotesInstance.addVote(newVote);
    }

    getVotesBy(username) {
        return VotesInstance.getVotesBy(username)
    }

}
const VotesPresenterInstance = new VotesPresenter();
export default VotesPresenterInstance;