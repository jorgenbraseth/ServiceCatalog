package no.braseth.core;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class ApplicationGroupInfo {
    @GraphId
    Long id;

    @RelatedTo(type="IS_PART_OF_APPLICATION_GROUP", direction = INCOMING)
    Set<ApplicationInfo> applications = new HashSet<>();
}
