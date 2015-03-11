package no.braseth.core;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class ApplicationInfo {

    @GraphId
    private Long id;
    private String name;
    private String description;

    @RelatedTo(type = "IS_PART_OF_APPLICATION_GROUP", direction = INCOMING)
    private ApplicationGroupInfo applicationGroup;

    @RelatedTo(type = "IS_INSTANCE_OF_APPLICATION", direction = INCOMING)
    private Set<ProcessInfo> processes = new HashSet<>();

    public ApplicationInfo() { /* For Jackson */ }

    public ApplicationInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ApplicationInfo applicationGroup(ApplicationGroupInfo group) {
        this.applicationGroup = group;
        return this;
    }
}
