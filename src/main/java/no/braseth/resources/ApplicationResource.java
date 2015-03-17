package no.braseth.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import no.braseth.core.ApplicationInfo;
import no.braseth.core.ServiceInfo;
import no.braseth.dto.ApplicationRegistration;
import no.braseth.infrastructure.ApplicationInfoRepo;
import no.braseth.infrastructure.ServiceInfoRepo;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
@Api("/applications")
public class ApplicationResource {

    private ApplicationInfoRepo repo;

    public ApplicationResource(ApplicationInfoRepo repo) {
        this.repo = repo;
    }

    @GET
    @ApiOperation(value = "Retrieves all applications", response = ApplicationInfo.class, responseContainer = "List")
    public List<ApplicationInfo> listAll() {
        return repo.findAll();
    }

    @POST
    @ApiOperation(value = "Accepts one or more applications for registration")
    public void registerApplication(@ApiParam ApplicationRegistration... registrations) {
        repo.registerNewApplication(registrations);
    }



}
