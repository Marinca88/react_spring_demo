package ro.utcn.ps.mi.assignment1.persistance.memory;

import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.persistance.api.TagRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTagReposytory implements TagRepository {

    private volatile int currentId = 1;
    private final Map<Integer, Tag> data = new HashMap<Integer, Tag>();

    @Override
    public Tag save(Tag tag) {
        if (tag.getId() != null) {
            data.put(tag.getId(), tag);
        } else {
            tag.setId(currentId++);
            data.put(tag.getId(), tag);
        }

        return tag;
    }

    @Override
    public void remove(Tag tag) {
        data.remove(tag.getId());
    }

    @Override
    public List<Tag> findByText(String text) {
        List<Tag> tags = new ArrayList<Tag>();
        for(Tag t : data.values()){
            if(t.getText().contains(text)){
                tags.add(t);
            }
        }
        return tags;
    }

    @Override
    public List<Tag> findAll() {
        return null;
    }
}
