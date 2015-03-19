package no.braseth.infrastructure;

import com.codahale.metrics.annotation.Timed;
import no.braseth.core.*;
import no.braseth.dto.ProcessRegistration;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.batch.BatchCallback;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.neo4j.helpers.collection.MapUtil.map;

@Service
public class ServiceCatalogRepository {

    private static int idx = 0;
    private final RestGraphDatabase graphDatabase;
    @Autowired
    Neo4jTemplate neo4j;


    public ServiceCatalogRepository() {
        graphDatabase = new RestGraphDatabase("http://localhost:7474/db/data/", "neo4j", "servicecatalog");
    }

    @Timed
    public void addCalculatedValuesForApplications(){
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-->(e:EnvironmentInfo) MERGE (a)-[:IS_DEPLOYED_IN_ENVIRONMENT]->(e)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-->(s:ServerInfo) MERGE (a)-[:IS_DEPLOYED_ON_SERVER]->(s)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-[:CONSUMES_SERVICE]->(s:ServiceInfo) MERGE (a)-[:IS_CONSUMED_BY_APPLICATION]->(s)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-[:PROVIDES_SERVICE]->(s:ServiceInfo) MERGE (a)-[:IS_PROVIDED_BY_APPLICATION]->(s)", null);
    }

    @Timed
    public ServiceInfo getOrCreateService(String serviceName) {
        Collection<String> labels = new ArrayList<>();
        labels.add("ServiceInfo");
        Node node = neo4j.merge("_ServiceInfo", "name", serviceName, new HashMap<>(), labels);
        ServiceInfo service = neo4j.projectTo(node, ServiceInfo.class);

        return service;
    }

    @Timed
    public EnvironmentInfo getOrCreateEnvironment(String environmentName) {
        if(environmentName == null){
            return null;
        }

        Collection<String> labels = new ArrayList<>();
        labels.add("EnvironmentInfo");
        Node node = neo4j.merge("_EnvironmentInfo", "name", environmentName, new HashMap<>(), labels);
        EnvironmentInfo environment = neo4j.projectTo(node, EnvironmentInfo.class);

        return environment;
    }

    @Timed
    public ApplicationInfo getOrCreateApplication(String applicationName) {
        if(applicationName == null){
            return null;
        }

        Collection<String> labels = new ArrayList<>();
        labels.add("ApplicationInfo");
        Node node = neo4j.merge("_ApplicationInfo", "name", applicationName, new HashMap<>(), labels);
        ApplicationInfo application = neo4j.projectTo(node, ApplicationInfo.class);

        return application;
    }

    @Timed
    public ServerInfo getOrCreateServer(String serverName) {
        if(serverName == null){
            return null;
        }
        Collection<String> labels = new ArrayList<>();
        labels.add("ServerInfo");
        Node node = neo4j.merge("_ServerInfo", "name", serverName, new HashMap<>(), labels);
        ServerInfo server = neo4j.projectTo(node, ServerInfo.class);

        return server;
    }

    @Timed
    @Transactional
    public void mergeProcess(ProcessRegistration... registrations){
        RestAPI api = graphDatabase.getRestAPI();
        Transaction tx = api.beginTx();
        api.executeBatch(new BatchCallback<Object>() {
            @Override
            public Object recordBatch(RestAPI api) {
                idx++;
                for (ProcessRegistration registration : registrations) {
                    RestCypherQueryEngine engine = new RestCypherQueryEngine(api);

                    String query =
                            "MERGE (process" + idx + ":ProcessInfo:_ProcessInfo{description:{description}}) ";

                    if(registration.getServer() != null) {
                        query +=
                                "MERGE (s" + idx + ":ServerInfo:_ServerInfo{name:{serverName}})" +
                                "MERGE (s" + idx + ")<-[:RUNS_ON_SERVER]-(process" + idx + ") ";
                    }
                    if(registration.getEnvironment() != null){
                        query += "MERGE (e" + idx + ":EnvironmentInfo:_EnvironmentInfo{name:{environmentName}})" +
                                "MERGE (e" + idx + ")<-[:RUNS_IN_ENVIRONMENT]-(process" + idx + ") ";
                    }
                    if(registration.getApplication() != null){
                        query += "MERGE (a" + idx + ":ApplicationInfo:_ApplicationInfo{name:{applicationName}})" +
                                "MERGE (a" + idx + ")<-[:IS_INSTANCE_OF_APPLICATION]-(process" + idx + ") ";
                    }
                    Map<String, Object> props = map("description", registration.getDescription(),
                            "serverName", registration.getServer(),
                            "environmentName", registration.getEnvironment(),
                            "applicationName", registration.getApplication()
                    );
                    engine.query(query, props);
                }
                return null;
            }
        });
        tx.success();

        tx.close();
    }

    @Timed
    public <T> T save(T entity) {
        T saved = neo4j.save(entity);
        return saved;
    }

    @Timed
    public ApplicationGroupInfo getOrCreateApplicationGroup(String groupName) {
        if(groupName == null){
            return null;
        }
        Collection<String> labels = new ArrayList<>();
        labels.add("ApplicationGroup");
        Node node = neo4j.merge("_ApplicationGroup", "name", groupName, new HashMap<>(), labels);
        ApplicationGroupInfo group = neo4j.projectTo(node, ApplicationGroupInfo.class);

        return group;
    }
}
