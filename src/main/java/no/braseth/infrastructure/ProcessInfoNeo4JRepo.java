package no.braseth.infrastructure;

import no.braseth.core.ProcessInfo;
import no.braseth.core.ServiceInfo;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProcessInfoNeo4JRepo extends GraphRepository<ProcessInfo> {
}
