package no.braseth;

import no.braseth.infrastructure.ServiceRepo;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        // nothing to do yet
    }

    @Override
    public void run(ServiceCatalogConfiguration configuration,
                    Environment environment) {

        final ServiceResource resource = context.getBean(ServiceResource.class);
        environment.jersey().register(resource);
    }
}
