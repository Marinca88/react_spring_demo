package ro.utcn.ps.mi.assignment1.dto;

import lombok.Data;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.AnswareVotes;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.QuestionVotes;

@Data
public class VotesDto {
    private  String user;
    private String type;
    private  Integer  id;


    public static VotesDto votesDtoFromQuestionVote(QuestionVotes vote, String user){
        VotesDto votesDto = new VotesDto();
        votesDto.setUser(user);
        votesDto.setType("question");
        votesDto.setId(vote.getQuestion().getQuestionId());
        return  votesDto;
    }

    public static VotesDto votesDtoFromAnswareVote(AnswareVotes vote, String user){
        VotesDto votesDto = new VotesDto();
        votesDto.setUser(user);
        votesDto.setType("answare");
        votesDto.setId(vote.getAnsware().getId());
        return  votesDto;
    }

}
