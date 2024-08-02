package domain;

import domain.annotations.Principal;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import java.util.List;

@ApplicationScoped
public class ElectionService {

    private final CandidateService service;

    private final Instance<ElectionRepository> electionRepositories;
    private final ElectionRepository repository;
    public ElectionService(CandidateService service, @Any Instance<ElectionRepository> electionRepositories, @Principal ElectionRepository repository) {
        this.service = service;
        this.electionRepositories = electionRepositories;
        this.repository = repository;
    }

    public void submit(){
        Election election = Election.create(service.findAll());
        electionRepositories.forEach(electionRepository -> electionRepository.submit(election));
    }

    public List<Election> findAll(){
        return repository.findAll();
    }
}
