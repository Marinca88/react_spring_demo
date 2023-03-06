package ro.utcn.ps.mi.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.ps.mi.assignment1.dto.TagDto;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.persistance.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final RepositoryFactory repositoryFactory;
    @Transactional
    public void save(TagDto tag){
        Tag tag1 = new Tag(tag.getText());
        repositoryFactory.createTagReposytory().save(tag1);
    }
    @Transactional
    public void remove(TagDto tag){
        Tag tag1 = repositoryFactory.createTagReposytory().findByText(tag.getText()).get(0);
        repositoryFactory.createTagReposytory().remove(tag1);
    }
     @Transactional
    public List<TagDto> findFromText(String  string){
        return repositoryFactory.createTagReposytory().findByText(string).stream().map(TagDto::tagDtoFromTag).collect(Collectors.toList());
    }

    @Transactional
    public  List<TagDto> findAll(){
        return  repositoryFactory.createTagReposytory().findAll().stream().map(TagDto::tagDtoFromTag).collect(Collectors.toList());
    }


}
