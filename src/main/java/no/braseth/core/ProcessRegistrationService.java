package no.braseth.core;

import no.braseth.dto.ProcessRegistration;
import no.braseth.infrastructure.ServiceCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessRegistrationService {

    @Autowired ServiceCatalogRepository repo;

    public void registerNewProcesses(ProcessRegistration... processes) {
        repo.mergeProcess(processes);
        repo.addCalculatedValuesForApplications();
    }


}
