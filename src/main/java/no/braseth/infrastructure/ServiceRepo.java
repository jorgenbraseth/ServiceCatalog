package no.braseth.infrastructure;

import no.braseth.core.ServiceInfo;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface ServiceRepo extends GraphRepository<ServiceInfo> {
}
