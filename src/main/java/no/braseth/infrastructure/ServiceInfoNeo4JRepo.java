package no.braseth.infrastructure;

import no.braseth.core.ServiceInfo;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface ServiceInfoNeo4JRepo extends GraphRepository<ServiceInfo> {
}
