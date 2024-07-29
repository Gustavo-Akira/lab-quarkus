package infraestructure.repository;

import domain.CandidateRepository;
import domain.CandidateRepositoryTest;
import io.quarkus.test.junit.QuarkusTest;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
class SqlCandidateRepositoryTest extends CandidateRepositoryTest {

    @Inject
    SqlCandidateRepository repository;

    @Override
    public CandidateRepository repository() {
        return repository;
    }
}