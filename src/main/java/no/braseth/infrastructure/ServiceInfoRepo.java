package no.braseth.infrastructure;

import no.braseth.core.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServiceInfoRepo {

    @Autowired
    private ServiceInfoNeo4JRepo repo;

    @Transactional
    public List<ServiceInfo> findAll() {
        return StreamSupport.stream(repo.findAll().spliterator(),true)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    @Transactional
    public void deleteAll(){
        repo.deleteAll();
    }

}
