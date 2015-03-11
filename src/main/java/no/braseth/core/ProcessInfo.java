package no.braseth.core;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.OUTGOING;

@NodeEntity
public class ProcessInfo {
    @GraphId
    Long id;

    String description;

    @RelatedTo(type="RUNS_ON_SERVER", direction = OUTGOING)
    ServerInfo server;

    @RelatedTo(type="RUNS_IN_ENVIRONMENT", direction = OUTGOING)
    EnvironmentInfo environment;

    @RelatedTo(type="IS_INSTANCE_OF_APPLICATION", direction = OUTGOING)
    ApplicationInfo application;

    @RelatedToVia(type="PROVIDES_SERVICE", direction = OUTGOING)
    Set<ProvidesServiceInfo> providedServices = new HashSet<>();

    @RelatedTo(type="CONSUMES_SERVICE", direction = OUTGOING)
    Set<ServiceInfo> consumedServices = new HashSet<>();

    public ProcessInfo id(Long id) {
        this.id = id;
        return this;
    }

    public ProcessInfo description(String description) {
        this.description = description;
        return this;
    }

    public ProcessInfo server(ServerInfo server) {
        this.server = server;
        return this;
    }

    public ProcessInfo environment(EnvironmentInfo environment) {
        this.environment = environment;
        return this;
    }

    public ProcessInfo application(ApplicationInfo application) {
        this.application = application;
        return this;
    }

    public Long id() {
        return id;
    }

    public String description() {
        return description;
    }

    public ServerInfo server() {
        return server;
    }

    public EnvironmentInfo environment() {
        return environment;
    }

    public ApplicationInfo application() {
        return application;
    }

    public Set<ServiceInfo> consumedServices() {
        return consumedServices;
    }

    public void addProvidedService(ServiceInfo service, String url){
        ProvidesServiceInfo psi = new ProvidesServiceInfo(service, this, url);
//        providedServices.add(psi);
    }
}
