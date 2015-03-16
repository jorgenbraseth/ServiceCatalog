package no.braseth.resources;

import no.braseth.core.ProcessInfo;
import no.braseth.core.ProcessRegistrationService;
import no.braseth.dto.ProcessRegistration;
import no.braseth.infrastructure.ProcessInfoRepo;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/processes")
@Produces(MediaType.APPLICATION_JSON)
public class ProcessResource {

    private ProcessInfoRepo repo;
    ProcessRegistrationService processRegisterService;

    public ProcessResource(ProcessInfoRepo repo, ProcessRegistrationService processRegistrationService) {
        this.repo = repo;
        this.processRegisterService = processRegistrationService;
    }

    @GET
    @Transactional
    public List<ProcessInfo> listAll() {
        return repo.findAll();
    }

    @POST
    public void registerProcess(ProcessRegistration... registrations) {
        processRegisterService.registerNewProcesses(registrations);

    }

}
