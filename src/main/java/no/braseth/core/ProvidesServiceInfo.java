package no.braseth.core;

import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity(type="PROVIDES_SERVICE")
public class ProvidesServiceInfo {

    @GraphId
    public Long id;

    @Fetch
    private String url;

    @StartNode private ProcessInfo process;

    @EndNode
    private ServiceInfo service;

    public ProvidesServiceInfo(ServiceInfo service, ProcessInfo process, String url) {
        this.service = service;
        this.process = process;
        this.url = url;
    }

    public ProvidesServiceInfo() { /* For Jackson */ }

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
