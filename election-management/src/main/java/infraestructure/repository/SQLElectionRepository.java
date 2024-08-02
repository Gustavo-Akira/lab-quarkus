package infraestructure.repository;

import domain.Candidate;
import domain.ElectionRepository;
import infraestructure.repository.entities.Election;
import infraestructure.repository.entities.ElectionCandidate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class SQLElectionRepository implements ElectionRepository {

    private final EntityManager entityManager;

    public SQLElectionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void submit(domain.Election election) {
        Election entity =  Election.fromDomain(election);
        this.entityManager.merge(entity);
        election.votes().entrySet().stream().map(entry-> ElectionCandidate.fromDomain(election, entry.getKey(),entry.getValue())).forEach(entityManager::merge);
    }

    public List<domain.Election> findAll(){
        Stream<Object[]> stream = entityManager.createNativeQuery("SELECT e.id AS election_id, c.id AS candidate_id, c.photo, c.given_name, c.family_name, c.email, c.phone, c.job_title, ec.votes FROM elections AS e INNER JOIN election_candidate AS ec ON ec.election_id = e.id INNER JOIN candidates AS c ON ec.candidate_id = c.id")
                .getResultStream();
        Map<String, List<Object[]>> map = stream.collect(groupingBy(o -> (String) o[0]));
        return map.entrySet().stream().map(entry->{
            Map.Entry<Candidate, Integer>[] candidates = entry.getValue()
                    .stream()
                    .map(row -> Map.entry(new Candidate((String) row[1],
                                    Optional.ofNullable((String) row[2]),
                                    (String) row[3],
                                    (String) row[4],
                                    (String) row[5],
                                    Optional.ofNullable((String) row[6]),
                                    Optional.ofNullable((String) row[7])),
                            (Integer) row[8]))
                    .toArray(Map.Entry[]::new);

            return new domain.Election(entry.getKey(), Map.ofEntries(candidates));
        }).toList();
    }

    @Transactional
    public void sync(domain.Election election) {
        election.votes()
                .entrySet()
                .stream()
                .map(entry -> ElectionCandidate.fromDomain(election, entry.getKey(), entry.getValue()))
                .forEach(entityManager::merge);
    }
}
