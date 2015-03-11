package no.braseth.core;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity
public class RunsOnServerInfo {

    @GraphId
    Long id;

    @StartNode
    ProcessInfo process;
    @EndNode
    ServerInfo server;

    Integer port;

    public ProcessInfo getProcess() {
        return process;
    }
}
