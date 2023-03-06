package ro.utcn.ps.mi.assignment1.persistance.api;

import ro.utcn.ps.mi.assignment1.entity.Answare;

import java.util.List;
import java.util.Optional;

public interface AnswareRepository {
    public Answare save(Answare answare);
    public void remove(Answare answare);
    public Optional<Answare> findById(Integer integer);
    public List<Answare> findAll();
}
