package ro.utcn.ps.mi.assignment1.persistance.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.persistance.api.TagRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HibernateTagRepository implements TagRepository {
    private final EntityManager entityManager;
    @Override
    public Tag save(Tag tag) {
        if(tag.getId() != null) {
            // update
            entityManager.merge(tag);
        } else {
            // insert
            entityManager.persist(tag);
        }

        return tag;
    }

    @Override
    public void remove(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public List<Tag> findByText(String text) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        query.select(query.from(Tag.class));
        List<Tag> tags =entityManager.createQuery(query).getResultList();
        List<Tag> result = new ArrayList<Tag>();
        for(Tag t : tags){
            if(t.getText().contains(text)){
                result.add(t);
            }
        }
        return result;
    }

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        query.select(query.from(Tag.class));

        return entityManager.createQuery(query).getResultList();
    }

}
