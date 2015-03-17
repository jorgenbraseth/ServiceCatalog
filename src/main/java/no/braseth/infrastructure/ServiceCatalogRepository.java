package no.braseth.infrastructure;

import com.codahale.metrics.Timer;
import no.braseth.ServiceCatalog;
import no.braseth.core.*;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class ServiceCatalogRepository {

    @Autowired
    Neo4jTemplate neo4j;

    public ServiceCatalogRepository() {
    }

    public void addCalculatedValuesForApplications(){
        Timer.Context timer = timer("addCalculatedValuesForApplications");
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-->(e:EnvironmentInfo) MERGE (a)-[:IS_DEPLOYED_IN_ENVIRONMENT]->(e)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-->(s:ServerInfo) MERGE (a)-[:IS_DEPLOYED_ON_SERVER]->(s)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-[:CONSUMES_SERVICE]->(s:ServiceInfo) MERGE (a)-[:IS_CONSUMED_BY_APPLICATION]->(s)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-[:PROVIDES_SERVICE]->(s:ServiceInfo) MERGE (a)-[:IS_PROVIDED_BY_APPLICATION]->(s)",null);
        timer.stop();
    }

    public ServiceInfo getOrCreateService(String serviceName) {
        Timer.Context timer = timer("getOrCreateService");
        Collection<String> labels = new ArrayList<>();
        labels.add("ServiceInfo");
        Node node = neo4j.merge("_ServiceInfo", "name", serviceName, new HashMap<>(), labels);
        ServiceInfo service = neo4j.projectTo(node, ServiceInfo.class);
        timer.stop();

        return service;
    }

    private Timer.Context timer(String name) {
        return ServiceCatalog.METRICS.timer(name).time();
    }

    public EnvironmentInfo getOrCreateEnvironment(String environmentName) {
        if(environmentName == null){
            return null;
        }

        Timer.Context timer = timer("getOrCreateEnvironment");
        Collection<String> labels = new ArrayList<>();
        labels.add("EnvironmentInfo");
        Node node = neo4j.merge("_EnvironmentInfo", "name", environmentName, new HashMap<>(), labels);
        EnvironmentInfo environment = neo4j.projectTo(node, EnvironmentInfo.class);
        timer.stop();

        return environment;
    }

    public ApplicationInfo getOrCreateApplication(String applicationName) {
        if(applicationName == null){
            return null;
        }

        Timer.Context timer = timer("getOrCreateApplication");
        Collection<String> labels = new ArrayList<>();
        labels.add("ApplicationInfo");
        Node node = neo4j.merge("_ApplicationInfo", "name", applicationName, new HashMap<>(), labels);
        ApplicationInfo application = neo4j.projectTo(node, ApplicationInfo.class);
        timer.stop();

        return application;
    }

    public ServerInfo getOrCreateServer(String serverName) {
        if(serverName == null){
            return null;
        }
        Timer.Context timer = timer("getOrCreateServer");
        Collection<String> labels = new ArrayList<>();
        labels.add("ServerInfo");
        Node node = neo4j.merge("_ServerInfo", "name", serverName, new HashMap<>(), labels);
        ServerInfo server = neo4j.projectTo(node, ServerInfo.class);
        timer.stop();

        return server;
    }

    public <T> T save(T entity) {
        Timer.Context timer = timer("ServiceCatalogRepository - All saves");
        Timer.Context timer2 = timer("ServiceCatalogRepository - Save "+entity.getClass().getName());
        T saved = neo4j.save(entity);
        timer.stop();
        timer2.stop();
        return saved;
    }

    public ApplicationGroupInfo getOrCreateApplicationGroup(String groupName) {
        if(groupName == null){
            return null;
        }
        Timer.Context timer = timer("getOrCreateApplicationGroup");
        Collection<String> labels = new ArrayList<>();
        labels.add("ApplicationGroup");
        Node node = neo4j.merge("_ApplicationGroup", "name", groupName, new HashMap<>(), labels);
        ApplicationGroupInfo group = neo4j.projectTo(node, ApplicationGroupInfo.class);
        timer.stop();

        return group;
    }
}
