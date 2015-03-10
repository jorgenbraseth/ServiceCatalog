package no.braseth;

import no.braseth.core.ServiceInfo;
import no.braseth.infrastructure.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/services")
@Produces(MediaType.APPLICATION_JSON)
@Service
public class ServiceResource {

    @Autowired private ServiceRepo repo;

    @GET
    public Result<ServiceInfo> listAll() {
        return repo.findAll();
    }
}
