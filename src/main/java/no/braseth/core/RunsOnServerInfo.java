package no.braseth.core;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity
public class RunsOnServerInfo {

    @GraphId
    public Long id;

    @StartNode
    public ProcessInfo process;
    @EndNode
    public ServerInfo server;

    public Integer port;

    public ProcessInfo getProcess() {
        return process;
    }
}
