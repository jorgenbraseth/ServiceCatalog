package no.braseth;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
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
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.util.concurrent.TimeUnit;

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
    }

    private void swaggerOn(Environment environment){
        // Swagger Resource
        environment.jersey().register(new ApiListingResourceJSON());

        // Swagger providers
        environment.jersey().register(new ApiDeclarationProvider());
        environment.jersey().register(new ResourceListingProvider());

        // Swagger Scanner, which finds all the resources for @Api Annotations
        ScannerFactory.setScanner(new DefaultJaxrsScanner());

        // Add the reader, which scans the resources and extracts the resource information
        ClassReaders.setReader(new DefaultJaxrsApiReader());

        // Set the swagger config options
        SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion("1.0.1");
        config.setBasePath("http://localhost:8080/api");
    }

    @Override
    public void run(ServiceCatalogConfiguration configuration,
                    Environment environment) {

        swaggerOn(environment);

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

