package no.braseth.core;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.OUTGOING;

@NodeEntity
public class ServiceInfo {

    @GraphId
    private Long id;

    @Fetch
    private String name;

    @RelatedTo(type="PROVIDED_BY", direction = OUTGOING)
    private Set<ApplicationInfo> applications;


    public ServiceInfo(){
        // Jackson deserialization
    }

    public ServiceInfo(Long id, String name) {
        this.id = id;
        this.name = name;
        applications = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<ApplicationInfo> getApplications() {
        return applications;
    }
}
