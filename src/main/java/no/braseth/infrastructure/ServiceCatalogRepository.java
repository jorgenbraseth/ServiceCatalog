package no.braseth.infrastructure;

import no.braseth.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceCatalogRepository {

    @Autowired
    Neo4jTemplate neo4j;

    public ServiceInfo getOrCreateService(String serviceName) {
        ServiceInfo service = neo4j.repositoryFor(ServiceInfo.class).findBySchemaPropertyValue("name", serviceName);
        if(service == null){
            service = new ServiceInfo();
            service.name = serviceName;
            neo4j.save(service);
        }
        return service;
    }

    public void addCalculatedValuesForApplications(){
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-->(e:EnvironmentInfo) MERGE (a)-[:IS_DEPLOYED_IN_ENVIRONMENT]->(e)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-->(s:ServerInfo) MERGE (a)-[:IS_DEPLOYED_ON_SERVER]->(s)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-[:CONSUMES_SERVICE]->(s:ServiceInfo) MERGE (a)-[:IS_CONSUMED_BY_APPLICATION]->(s)",null);
        neo4j.query("MATCH (a:ApplicationInfo)<--(:ProcessInfo)-[:PROVIDES_SERVICE]->(s:ServiceInfo) MERGE (a)-[:IS_PROVIDED_BY_APPLICATION]->(s)",null);
    }

    public EnvironmentInfo getOrCreateEnvironment(String environmentName) {
        if(environmentName == null){
            return null;
        }
        EnvironmentInfo environment = neo4j.repositoryFor(EnvironmentInfo.class).findBySchemaPropertyValue("name", environmentName);
        if(environment == null){
            environment = new EnvironmentInfo(environmentName);
            neo4j.save(environment);
        }
        return environment;
    }

    public ApplicationInfo getOrCreateApplication(String applicationName) {
        if(applicationName == null){
            return null;
        }
        ApplicationInfo application = neo4j.repositoryFor(ApplicationInfo.class).findBySchemaPropertyValue("name", applicationName);
        if(application == null){
            application = new ApplicationInfo();
            application.name = applicationName;
            neo4j.save(application);
        }
        return application;
    }

    public ServerInfo getOrCreateServer(String serverName) {
        if(serverName == null){
            return null;
        }
        ServerInfo server = neo4j.repositoryFor(ServerInfo.class).findBySchemaPropertyValue("name", serverName);
        if(server == null){
            server = new ServerInfo(serverName);
            neo4j.save(server);
        }
        return server;
    }

    public <T> T save(T entity) {
        return neo4j.save(entity);
    }
}
