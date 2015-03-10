package no.braseth.infrastructure;

import no.braseth.core.ApplicationInfo;
import no.braseth.core.ServiceInfo;
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
    Neo4jOperations template;

    @Autowired
    private ApplicationInfoNeo4JRepo repo;

    @Transactional
    public List<ApplicationInfo> findAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), true)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public void deleteAll(){
        repo.deleteAll();
    }

}
