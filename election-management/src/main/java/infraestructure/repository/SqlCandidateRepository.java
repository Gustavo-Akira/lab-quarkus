package infraestructure.repository;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SqlCandidateRepository implements CandidateRepository {

    @Override
    public void save(List<Candidate> candidates) {

    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        return List.of();
    }
}
