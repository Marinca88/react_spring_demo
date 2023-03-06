package ro.utcn.ps.mi.assignment1.persistance.api;

import ro.utcn.ps.mi.assignment1.entity.Tag;

import java.util.List;

public interface TagRepository {
    public Tag save(Tag tag);
    public void remove(Tag tag);
    public List<Tag> findByText(String text);
    public List<Tag> findAll();
}
