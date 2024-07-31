package infraestructure.repository.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "election")
public class Election {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Election fromDomain(domain.Election electionDomain){
        Election election = new Election();
        election.setId(electionDomain.id());
        return election;
    }
}
