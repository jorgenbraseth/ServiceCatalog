package no.braseth.core;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class ServerInfo {
    @GraphId
    public Long id;
    public String name;

    @RelatedTo(type="RUNS_ON_SERVER", direction = INCOMING)
    Set<ProcessInfo> runningProcesses = new HashSet<>();

    public ServerInfo() { /* For Jackson */ }

    public ServerInfo(String name) {
        this.name = name;
        this.runningProcesses = runningProcesses;
    }

}
