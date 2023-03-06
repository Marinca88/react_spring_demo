package ro.utcn.ps.mi.assignment1.persistance.api;

public interface RepositoryFactory {
    QuestionRepository createQuestionRepository();
    AnswareRepository createAnswareRepository();
    TagRepository createTagReposytory();
    UserRepository createUserReposytory();
    QuestionVotesRepository createQuestionVotesRepository();
    AnswareVotesRepository createAnswareVotesRepository();
}
