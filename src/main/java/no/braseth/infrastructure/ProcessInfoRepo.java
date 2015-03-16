package no.braseth.infrastructure;

import no.braseth.core.ApplicationInfo;
import no.braseth.core.ProcessInfo;
import no.braseth.dto.ProcessRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProcessInfoRepo {

    @Autowired
    private ProcessInfoNeo4JRepo repo;

    @Transactional
    public List<ProcessInfo> findAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), true)
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
