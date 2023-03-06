package ro.utcn.ps.mi.assignment1.event;

import lombok.Data;
import ro.utcn.ps.mi.assignment1.dto.QuestionDto;

@Data
public class QuestionUpdatedEvent extends BaseEvent{

    private final QuestionDto questionDto;
    public QuestionUpdatedEvent(QuestionDto questionDto) {
        super(EventType.QUESTION_UPDATED);
        this.questionDto = questionDto;
    }
}
