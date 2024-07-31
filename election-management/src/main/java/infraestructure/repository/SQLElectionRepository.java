package infraestructure.repository;

import domain.ElectionRepository;
import infraestructure.repository.entities.Election;
import infraestructure.repository.entities.ElectionCandidate;

import javax.persistence.EntityManager;
import java.util.stream.Collectors;

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
}
