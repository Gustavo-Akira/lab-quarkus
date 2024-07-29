package infraestructure.repository;

import domain.Candidate;
import domain.CandidateRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class SqlCandidateRepository implements CandidateRepository {

    @Override
    public void save(List<Candidate> candidates) {

    }

    @Override
    public List<Candidate> findAll() {
        return new ArrayList<>();
    }
}
