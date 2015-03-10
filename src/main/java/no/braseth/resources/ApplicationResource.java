package no.braseth.resources;

import no.braseth.core.ApplicationInfo;
import no.braseth.core.ServiceInfo;
import no.braseth.infrastructure.ApplicationInfoRepo;
import no.braseth.infrastructure.ServiceInfoRepo;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {

    private ApplicationInfoRepo repo;

    public ApplicationResource(ApplicationInfoRepo repo) {
        this.repo = repo;
    }

    @GET
    @Transactional
    public List<ApplicationInfo> listAll() {
        return repo.findAll();
    }

}
