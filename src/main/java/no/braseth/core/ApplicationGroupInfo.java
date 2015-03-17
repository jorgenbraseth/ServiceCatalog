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
    public Long id;

    public String name;

    @RelatedTo(type="IS_PART_OF_APPLICATION_GROUP", direction = INCOMING)
    private Set<ApplicationInfo> applications = new HashSet<>();

    public Set<ApplicationInfo> getApplications() {
        return applications;
    }
}
