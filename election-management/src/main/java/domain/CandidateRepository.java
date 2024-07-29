package domain;

import java.util.Collections;
import java.util.List;

public interface CandidateRepository{
    void save(List<Candidate> candidates);
    default void save(Candidate candidate){
        save(Collections.singletonList(candidate));
    }

    List<Candidate> findAll();
}
