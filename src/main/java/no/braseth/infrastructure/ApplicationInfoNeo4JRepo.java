package no.braseth.infrastructure;

import javafx.application.Application;
import no.braseth.core.ApplicationInfo;
import no.braseth.core.ServiceInfo;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

@Service
public interface ApplicationInfoNeo4JRepo extends GraphRepository<ApplicationInfo> {
}
