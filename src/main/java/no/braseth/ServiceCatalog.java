package no.braseth;

import io.dropwizard.assets.AssetsBundle;
import no.braseth.core.*;
import no.braseth.infrastructure.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.braseth.resources.ApplicationResource;
import no.braseth.resources.ProcessResource;
import no.braseth.resources.ServiceResource;
import org.jfairy.Fairy;
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

        ProcessInfo p1 = MockDataGenerator.process()
                .server(server)
                .environment(environment)
                .application(application);

        ServiceInfo providedService = MockDataGenerator.service();
        neo4j.save(providedService);

        p1.addProvidedService(providedService, Fairy.create().company().url());
        p1.getConsumedServices().add(MockDataGenerator.service());

        neo4j.save(p1);
    }

    @Override
    public void run(ServiceCatalogConfiguration configuration,
                    Environment environment) {

        environment.jersey().setUrlPattern("/api/*");

        final ServiceInfoRepo serviceInfoRepo = context.getBean(ServiceInfoRepo.class);
        environment.jersey().register(new ServiceResource(serviceInfoRepo));

        final ApplicationInfoRepo applicationInfoRepo = context.getBean(ApplicationInfoRepo.class);
        environment.jersey().register(new ApplicationResource(applicationInfoRepo));

        final ProcessInfoRepo processInfoRepo = context.getBean(ProcessInfoRepo.class);
        ProcessRegistrationService processRegistrationService = context.getBean(ProcessRegistrationService.class);
        environment.jersey().register(new ProcessResource(processInfoRepo,processRegistrationService));
    }
}

