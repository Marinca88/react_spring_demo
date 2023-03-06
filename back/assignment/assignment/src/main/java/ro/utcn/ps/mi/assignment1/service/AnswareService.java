package ro.utcn.ps.mi.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.ps.mi.assignment1.dto.AnswareDto;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.RepositoryFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswareService {
    private final RepositoryFactory repositoryFactory;
    private final UDetailsService uDetailsService;

    @Transactional
    public  void save(AnswareDto answare, Integer id){
        if(id==null) {
            Answare answare1 = new Answare(answare.getUpvotes(), answare.getDownvotes(), answare.getText(),LocalDateTime.now() );
            User user  = uDetailsService.loadCurrentUser();
            answare1.setAuthor(user);
            answare1.setQuestion_id(answare.getQuestion_id());
            repositoryFactory.createAnswareRepository().save(answare1);
        }else{
            Answare answare1 = repositoryFactory.createAnswareRepository().findById(id).orElseThrow(AnswareNotFoundException::new);
            answare1.setUpvotes(answare.getUpvotes());
            answare1.setDownvotes(answare.getDownvotes());
            answare1.setText(answare.getText());
            repositoryFactory.createAnswareRepository().save(answare1);
        }
    }

    @Transactional
    public void remove(Integer id){
        Answare answare = repositoryFactory.createAnswareRepository().findById(id).orElseThrow(AnswareNotFoundException::new);
        repositoryFactory.createAnswareRepository().remove(answare);
    }

    @Transactional
    public AnswareDto findById(Integer id){
       Answare answare= repositoryFactory.createAnswareRepository().findById(id).orElseThrow(AnswareNotFoundException::new);
       return AnswareDto.answareDtoFromAnsware(answare);
    }

    @Transactional
    public List<AnswareDto> findAll(){
        return repositoryFactory.createAnswareRepository().findAll().stream().map(AnswareDto::answareDtoFromAnsware).collect(Collectors.toList());
    }

}
