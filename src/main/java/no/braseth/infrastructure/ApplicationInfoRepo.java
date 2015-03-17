package no.braseth.infrastructure;

import no.braseth.core.ApplicationInfo;
import no.braseth.core.ProcessInfo;
import no.braseth.core.ProvidesServiceInfo;
import no.braseth.core.ServiceInfo;
import no.braseth.dto.ApplicationRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationInfoRepo {

    @Autowired
    private ApplicationInfoNeo4JRepo applicationRepo;

    @Autowired
    ServiceCatalogRepository repo;

    @Transactional
    public List<ApplicationInfo> findAll() {
        return StreamSupport.stream(applicationRepo.findAll().spliterator(), true)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void registerNewApplication(ApplicationRegistration... registrations) {
        for (ApplicationRegistration registration : registrations) {


            ApplicationInfo applicationInfo = new ApplicationInfo(registration.getName(), registration.getDescription());
            repo.save(applicationInfo);

            applicationInfo.applicationGroup = repo.getOrCreateApplicationGroup(registration.getApplicationGroup());

            applicationInfo.addEnvironment(
                    repo.getOrCreateEnvironment(registration.getEnvironment())
            );

            applicationInfo.addServer(
                    repo.getOrCreateServer(registration.getServer())
            );


            for (String serviceName : registration.getConsumedServices()) {
                applicationInfo.addConsumedService(
                        repo.getOrCreateService(serviceName)
                );
            }

            for (String serviceName : registration.getProvidedServices()) {
                applicationInfo.addProvidedService(
                        repo.getOrCreateService(serviceName)
                );
            }

            repo.save(applicationInfo);
        }
    }
}
