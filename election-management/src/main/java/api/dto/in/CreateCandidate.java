package api.dto.in;

import domain.Candidate;

import java.util.Optional;

public record CreateCandidate(String givenName, String familyName, String email, Optional<String> photo, Optional<String> phone, Optional<String> jobTitle) {
    public Candidate toDomain(){
        return Candidate.create(photo(),givenName(),familyName(),email(),phone(),jobTitle());
    }
}
