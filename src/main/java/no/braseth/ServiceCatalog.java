package no.braseth;

import io.dropwizard.assets.AssetsBundle;
import no.braseth.core.*;
import no.braseth.infrastructure.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.braseth.resources.ApplicationResource;
import no.braseth.resources.ServiceResource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.util.HashSet;
import java.util.Map;

public class ServiceCatalog extends Application<ServiceCatalogConfiguration> {
    private static ApplicationContext context;

    public static void main(String[] args) throws Exception {
        context = new ClassPathXmlApplicationContext("META-INF/spring/application-context.xml");
        new ServiceCatalog().run(args);
    }

    @Override
    public String getName() {
        return "ServiceCatalog";
    }

    @Override
    public void initialize(Bootstrap<ServiceCatalogConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/webapp", "/", "index.html"));

        createMockData();
    }

    private void createMockData() {
        Neo4jTemplate neo4j = context.getBean(Neo4jTemplate.class);

        neo4j.query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r", null);

        ServerInfo server = new ServerInfo("Server numero uno");
        EnvironmentInfo environment = new EnvironmentInfo("Production");
        ApplicationInfo application = new ApplicationInfo("Main Application","The most important application of them all");

        ProcessInfo p1 = new ProcessInfo()
                .application(application)
                .description("Kul prosess as")
                .server(server)
                .environment(environment);

        ServiceInfo s1 = new ServiceInfo();
        p1.addProvidedService(s1,"http://and.stuff");
        ServiceInfo s2 = new ServiceInfo();
        p1.consumedServices().add(s2);

        neo4j.save(p1);
    }

    @Override
    public void run(ServiceCatalogConfiguration configuration,
                    Environment environment) {

        final ServiceInfoRepo serviceInfoRepo = context.getBean(ServiceInfoRepo.class);
        final ApplicationInfoRepo applicationInfoRepo = context.getBean(ApplicationInfoRepo.class);
        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new ServiceResource(serviceInfoRepo));
        environment.jersey().register(new ApplicationResource(applicationInfoRepo));
    }
}
