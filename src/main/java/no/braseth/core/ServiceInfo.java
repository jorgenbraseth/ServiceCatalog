package no.braseth.core;

import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class ServiceInfo {

    @GraphId
    public Long id;

    @Fetch
    @Indexed(unique = true)
    public String name;

    @Fetch
    @RelatedToVia(type="PROVIDES_SERVICE", direction = INCOMING)
    public Set<ProvidesServiceInfo> providingProcesses = new HashSet<>();

    @Fetch
    @RelatedTo(type="CONSUMES_SERVICE", direction = INCOMING)
    public Set<ProcessInfo> consumingProcesses = new HashSet<>();


}
