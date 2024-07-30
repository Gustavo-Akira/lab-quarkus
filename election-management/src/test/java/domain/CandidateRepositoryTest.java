package domain;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

public abstract class CandidateRepositoryTest {
    public abstract CandidateRepository repository();

    @Test
    void save(){
        Candidate candidate = Instancio.create(Candidate.class);
        repository().save(candidate);
        Optional<Candidate> result = repository().findById(candidate.id());
        assertTrue(result.isPresent());
        assertEquals(candidate, result.get());
    }

    @Test
    void findAll(){
        List<Candidate> candidates = Instancio.stream(Candidate.class).limit(1).toList();
        repository().save(candidates);
        List<Candidate> result = repository().findAll();
        assertIterableEquals(candidates,result);
    }


    @Test
    void findByName(){
        Candidate candidate1 = Instancio.create(Candidate.class);
        Candidate candidate = Instancio.of(Candidate.class).set(field("familyName"),"Uekita").create();
        CandidateQuery query = new CandidateQuery.Builder().name("UEK").build();
        repository().save(List.of(candidate1, candidate));
        List<Candidate> result = repository().find(query);

        assertEquals(1,result.size());
        assertEquals(candidate, result.get(0));
    }
}