package no.braseth.core;

import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

@NodeEntity
public class ApplicationInfo {

    @GraphId
    public  Long id;

    @Indexed(unique = true)
    public  String name;
    public  String description;

    @RelatedTo(type = "IS_PART_OF_APPLICATION_GROUP", direction = OUTGOING)
    public  ApplicationGroupInfo applicationGroup;

    @RelatedTo(type = "IS_INSTANCE_OF_APPLICATION", direction = INCOMING)
    private  Set<ProcessInfo> processes = new HashSet<>();

    @RelatedTo(type = "IS_DEPLOYED_IN_ENVIRONMENT", direction = OUTGOING)
    private  Set<EnvironmentInfo> environments = new HashSet<>();

    @RelatedTo(type = "IS_DEPLOYED_ON_SERVER", direction = OUTGOING)
    private  Set<ServerInfo> servers = new HashSet<>();

    @RelatedTo(type = "IS_CONSUMED_BY_APPLICATION", direction = OUTGOING)
    private  Set<ServiceInfo> consumes = new HashSet<>();

    @RelatedTo(type = "IS_PROVIDED_BY_APPLICATION", direction = OUTGOING)
    private  Set<ServiceInfo> provides = new HashSet<>();


    public ApplicationInfo() { /* For Jackson */ }

    public ApplicationInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ApplicationInfo applicationGroup(ApplicationGroupInfo group) {
        this.applicationGroup = group;
        return this;
    }

    public Set<ProcessInfo> getProcesses() {
        return processes;
    }

    public ApplicationInfo addEnvironment(EnvironmentInfo environment) {
        if(environment!=null) {
            this.environments.add(environment);
        }
        return this;
    }

    public ApplicationInfo addServer(ServerInfo server) {
        if(server!=null) {
            this.servers.add(server);
        }
        return this;
    }

    public ApplicationInfo addConsumedService(ServiceInfo service) {
        if(service!=null) {
            this.consumes.add(service);
        }
        return this;
    }

    public ApplicationInfo addProvidedService(ServiceInfo service) {
        if(service!=null) {
            this.provides.add(service);
        }
        return this;
    }
}
