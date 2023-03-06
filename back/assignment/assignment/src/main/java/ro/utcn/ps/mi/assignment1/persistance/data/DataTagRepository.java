package ro.utcn.ps.mi.assignment1.persistance.data;

import org.springframework.data.repository.Repository;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.persistance.api.TagRepository;

import java.util.ArrayList;
import java.util.List;

public interface DataTagRepository extends TagRepository, Repository<Tag,Integer> {
    void delete(Tag tag);

    @Override
    public default void remove(Tag tag) {
        delete(tag);
    }
    @Override
    public default List<Tag> findByText(String text){
        List<Tag> tags = findAll();
        List<Tag> result = new ArrayList<Tag>();
        for(Tag t : tags){
            if(t.getText().contains(text)){
                result.add(t);
            }
        }
        return result;
    }

    List<Tag> findAll();
}
