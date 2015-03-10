package no.braseth.core;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class ServiceInfo {

    @GraphId
    private Long id;
    private String name;


    public ServiceInfo(){
        // Jackson deserialization
    }

    public ServiceInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
