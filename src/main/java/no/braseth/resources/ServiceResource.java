package no.braseth.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import no.braseth.core.ServiceInfo;
import no.braseth.dto.BasicServiceInfo;
import no.braseth.infrastructure.ServiceInfoRepo;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/services")
@Produces(MediaType.APPLICATION_JSON)
@Api("/services")
public class ServiceResource {

    private ServiceInfoRepo repo;

    public ServiceResource(ServiceInfoRepo repo) {
        this.repo = repo;
    }

    @GET
    @ApiOperation("Lists all registered services")
    @Timed
    public List<BasicServiceInfo> listAll() {
        return repo.findAll();
    }


    @GET
    @Path("/{name}")
    @ApiOperation("Retrieves a single service")
    @Timed
    public BasicServiceInfo findService(@ApiParam @PathParam("name") String name) {
        return repo.find(name);
    }


}
