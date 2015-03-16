package no.braseth.core;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="PROVIDES_SERVICE")
public class ProvidesServiceInfo {

    @GraphId
    public Long id;

    @StartNode private ProcessInfo process;

    @EndNode
    private ServiceInfo service;

    public ProvidesServiceInfo(ServiceInfo service, ProcessInfo process, String url) {
        this.service = service;
        this.process = process;
        this.url = url;
    }

    public ProvidesServiceInfo() { /* For Jackson */ }

    private String url;

    public String getUrl() {
        return url;
    }

    public ProcessInfo getProcess() {
        return process;
    }

    public ServiceInfo getService() {
        return service;
    }
}
