package no.braseth.core;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class EnvironmentInfo {
    @GraphId
    public Long id;

    @Indexed(unique = true)
    public String name;

    @RelatedTo(type="RUNS_IN_ENVIRONMENT", direction = INCOMING)
    Set<ProcessInfo> processesRunning = new HashSet<>();


    public EnvironmentInfo() { /* For Jackson */ }

    public EnvironmentInfo(String name) {
        this.name = name;
    }
}
