package api.dto.out;

import domain.Candidate;
import domain.Election;

import java.util.List;

public record ElectionResponseDTO(String id, List<String> candidates){
    public static ElectionResponseDTO fromDomain(Election election){
        return new ElectionResponseDTO(election.id(), election.candidates().stream().map(Candidate::id).toList());
    }
}
