package domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CandidateRepository{
    void save(List<Candidate> candidates);
    default void save(Candidate candidate){
        save(Collections.singletonList(candidate));
    }

    List<Candidate> find(CandidateQuery query);
    default List<Candidate> findAll(){
        return find(new CandidateQuery.Builder().build());
    }

    default Optional<Candidate> findById(String id){
        CandidateQuery query = new CandidateQuery.Builder().ids(Set.of(id)).build();
        return find(query).stream().findFirst();
    }
}
