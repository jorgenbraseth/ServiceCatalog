package no.braseth.core;

import no.braseth.dto.ProcessRegistration;
import no.braseth.infrastructure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessRegistrationService {

    @Autowired ServiceCatalogRepository repo;
    @Transactional
    public void registerNewProcesses(ProcessRegistration... processes) {
        for (ProcessRegistration process : processes) {

            ProcessInfo processInfo = new ProcessInfo();
            repo.save(processInfo);

            processInfo.description(
                    process.getDescription()
            );

            processInfo.application(
                    repo.getOrCreateApplication(process.getApplication())
            );

            processInfo.environment(
                    repo.getOrCreateEnvironment(process.getEnvironment())
            );

            processInfo.server(
                    repo.getOrCreateServer(process.getServer())
            );

            for (String serviceName : process.getConsumedServices()) {
                processInfo.getConsumedServices().add(
                        repo.getOrCreateService(serviceName)
                );
            }

            for (String serviceName : process.getProvidedServices().keySet()) {
                ServiceInfo service = repo.getOrCreateService(serviceName);
                ProvidesServiceInfo providesServiceInfo = new ProvidesServiceInfo(service, processInfo, process.getProvidedServices().get(serviceName));
                repo.save(providesServiceInfo);
                processInfo.getProvidedServices().add(providesServiceInfo);
            }

            repo.save(processInfo);

        }
        repo.addCalculatedValuesForApplications();
    }


}
