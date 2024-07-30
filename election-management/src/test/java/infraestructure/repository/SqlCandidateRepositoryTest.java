package infraestructure.repository;

import domain.CandidateRepository;
import domain.CandidateRepositoryTest;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
class SqlCandidateRepositoryTest extends CandidateRepositoryTest {

    @Inject
    SqlCandidateRepository repository;

    @Inject
    EntityManager entityManager;

    @Override
    public CandidateRepository repository() {
        return repository;
    }

    @AfterEach
    @TestTransaction
    void tearDown(){
        entityManager.createNativeQuery("TRUNCATE TABLE candidate").executeUpdate();
    }
}