package domain;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;

@ApplicationScoped
public class ElectionService {

    private final CandidateService service;

    private final Instance<ElectionRepository> electionRepositories;

    public ElectionService(CandidateService service, Instance<ElectionRepository> electionRepositories) {
        this.service = service;
        this.electionRepositories = electionRepositories;
    }

    public void submit(){
        Election election = Election.create(service.findAll());
        electionRepositories.forEach(electionRepository -> electionRepository.submit(election));
    }
}
