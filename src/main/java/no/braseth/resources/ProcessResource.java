package no.braseth.resources;

import no.braseth.core.ApplicationInfo;
import no.braseth.core.ProcessInfo;
import no.braseth.infrastructure.ApplicationInfoRepo;
import no.braseth.infrastructure.ProcessInfoRepo;
import no.braseth.infrastructure.ServiceInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/processes")
@Produces(MediaType.APPLICATION_JSON)
public class ProcessResource {

    private ProcessInfoRepo repo;

    public ProcessResource(ProcessInfoRepo repo) {
        this.repo = repo;
    }

    @GET
    @Transactional
    public List<ProcessInfo> listAll() {
        return repo.findAll();
    }

}
