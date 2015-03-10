package no.braseth.resources;

import no.braseth.core.ServiceInfo;
import no.braseth.infrastructure.ServiceInfoRepo;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/services")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceResource {

    private ServiceInfoRepo repo;

    public ServiceResource(ServiceInfoRepo repo) {
        this.repo = repo;
    }

    @GET
    @Transactional
    public List<ServiceInfo> listAll() {
        return repo.findAll();
    }

}
