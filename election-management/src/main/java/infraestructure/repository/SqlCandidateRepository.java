package infraestructure.repository;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class SqlCandidateRepository implements CandidateRepository {
    private final EntityManager entityManager;

    public SqlCandidateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(List<Candidate> candidates) {
        candidates.stream().map(infraestructure.repository.entities.Candidate::fromDomain).forEach(entityManager::merge);
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        var candidateQuery = criteriaBuilder.createQuery(infraestructure.repository.entities.Candidate.class);
        var root = candidateQuery.from(infraestructure.repository.entities.Candidate.class);
        candidateQuery.select(root).where(conditions(query,criteriaBuilder,root));
        return entityManager.createQuery(candidateQuery).getResultStream().map(infraestructure.repository.entities.Candidate::toDomain).toList();
    }

    private Predicate[] conditions(CandidateQuery query, CriteriaBuilder builder, Root<infraestructure.repository.entities.Candidate> root){
        return Stream.of(query.ids().map(id-> builder.in(root.get("id")).value(id)), query.name().map(name-> builder.or(builder.like(builder.lower(root.get("familyName")),name.toLowerCase()+"%"),builder.like(builder.lower(root.get("givenName")),name.toLowerCase()+"%")))).flatMap(Optional::stream).toArray(Predicate[]::new);
    }
}
