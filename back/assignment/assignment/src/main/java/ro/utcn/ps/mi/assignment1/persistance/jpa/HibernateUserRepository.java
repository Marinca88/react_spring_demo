package ro.utcn.ps.mi.assignment1.persistance.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.ps.mi.assignment1.entity.Tag;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateUserRepository implements UserRepository{
    private final EntityManager entityManager;
    @Override
    public User save(User user) {
        if(user.getId()!= null) {
            // update
            entityManager.merge(user);
        } else {
            // insert
            entityManager.persist(user);
        }

        return user;
    }

    @Override
    public void remove(User user) {
        entityManager.remove(user);
    }

    @Override
    public Optional<User> findByUsername(String string) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.select(query.from(User.class));
        List<User> users = entityManager.createQuery(query).getResultList();
        Optional<User> user = Optional.ofNullable(null);
        for(User u: users){
            if(u.getUsername().equals(string)){
                user=Optional.ofNullable(u);
            }
        }
        return user;
    }

    @Override
    public Optional<User> findById(Integer id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.select(query.from(User.class));
        List<User> users = entityManager.createQuery(query).getResultList();
        Optional<User> user = Optional.ofNullable(null);
        for(User u: users){
            if(u.getId()==id){
                user=Optional.ofNullable(u);
            }
        }
        return user;
    }
}
