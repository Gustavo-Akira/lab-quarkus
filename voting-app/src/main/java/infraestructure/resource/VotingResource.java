package infraestructure.resource;

import api.ElectionApi;
import api.dto.out.ElectionResponseDTO;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/api/voting")
public class VotingResource {
    private final ElectionApi api;

    public VotingResource(ElectionApi api) {
        this.api = api;
    }
    @GET
    public List<ElectionResponseDTO> candidates(){
        return api.findAll();
    }

    @POST
    @Path("/elections/{electionId}/candidate/{candidateId}")
    @Transactional
    @ResponseStatus(RestResponse.StatusCode.CREATED)
    public void vote(@PathParam("electionId") String electionId, @PathParam("candidateId") String candidateId){
        api.vote(electionId,candidateId);
    }
}
