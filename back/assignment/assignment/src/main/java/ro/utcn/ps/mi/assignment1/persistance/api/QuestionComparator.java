package ro.utcn.ps.mi.assignment1.persistance.api;

import ro.utcn.ps.mi.assignment1.dto.QuestionDto;
import ro.utcn.ps.mi.assignment1.entity.Question;

import java.util.Comparator;

public class QuestionComparator implements Comparator<QuestionDto> {
    @Override
    public int compare(QuestionDto o1, QuestionDto o2) {
        return o1.getCreateTime().compareTo(o1.getCreateTime());
    }
}
