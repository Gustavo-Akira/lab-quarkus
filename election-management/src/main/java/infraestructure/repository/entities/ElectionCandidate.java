package infraestructure.repository.entities;

import domain.Candidate;
import domain.Election;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "election_candidate")
public class ElectionCandidate {
    @EmbeddedId
    private ElectionCandidateId electionCandidateId;

    private Integer votes;

    public ElectionCandidateId getElectionCandidateId() {
        return electionCandidateId;
    }

    public void setElectionCandidateId(ElectionCandidateId electionCandidateId) {
        this.electionCandidateId = electionCandidateId;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public static ElectionCandidate fromDomain(Election election, Candidate candidate, Integer votes){
        ElectionCandidate electionCandidate = new ElectionCandidate();
        ElectionCandidateId id = new ElectionCandidateId();
        id.setCandidateId(candidate.id());
        id.setElectionId(election.id());
        electionCandidate.setElectionCandidateId(id);
        electionCandidate.setVotes(votes);
        return electionCandidate;
    }
}

@Embeddable
class ElectionCandidateId implements Serializable {
    @Column(name = "election_id")
    private String electionId;

    @Column(name = "candidate_id")
    private String candidateId;

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }
}
