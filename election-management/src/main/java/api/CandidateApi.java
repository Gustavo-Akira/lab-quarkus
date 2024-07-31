package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.Candidate;
import domain.CandidateService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CandidateApi {
    private final CandidateService service;


    public CandidateApi(CandidateService service) {
        this.service = service;
    }

    public void create(CreateCandidate createCandidate) {
        service.save(createCandidate.toDomain());
    }

    public Candidate update(String id, UpdateCandidate updateCandidate) {
        service.save(updateCandidate.toDomain(id));
        return Candidate.fromDomain(service.findById(id));
    }

    public List<Candidate> list() {
        return service.findAll().stream().map(Candidate::fromDomain).collect(Collectors.toList());
    }
}
