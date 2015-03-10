package no.braseth.core;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class ApplicationInfo {

    @GraphId
    private Long id;

    private String name;
    private String description;

    @RelatedTo(type="PROVIDED_BY", direction = INCOMING)
    private Set<ServiceInfo> servicesProvided;

    public ApplicationInfo() {
    }

    public ApplicationInfo(Long id, String name, String description, Set<ServiceInfo> servicesProvided) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.servicesProvided = servicesProvided;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ServiceInfo> getServicesProvided() {
        return servicesProvided;
    }

    public void setServicesProvided(Set<ServiceInfo> servicesProvided) {
        this.servicesProvided = servicesProvided;
    }
}
