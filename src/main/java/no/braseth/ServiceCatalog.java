package no.braseth;

import no.braseth.core.ApplicationInfo;
import no.braseth.core.ServiceInfo;
import no.braseth.infrastructure.ApplicationInfoNeo4JRepo;
import no.braseth.infrastructure.ApplicationInfoRepo;
import no.braseth.infrastructure.ServiceInfoNeo4JRepo;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.braseth.infrastructure.ServiceInfoRepo;
import no.braseth.resources.ApplicationResource;
import no.braseth.resources.ServiceResource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;

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
        ServiceInfoNeo4JRepo repo = context.getBean(ServiceInfoNeo4JRepo.class);
        ApplicationInfoNeo4JRepo appRepo = context.getBean(ApplicationInfoNeo4JRepo.class);

        repo.deleteAll();
        appRepo.deleteAll();

        ServiceInfo s1 = new ServiceInfo(null, "FooService");
        ServiceInfo s2 = new ServiceInfo(null, "BarService");

        ApplicationInfo app1 = new ApplicationInfo(
                null,
                "FooBar Application","Application that supplies both voo AND bar!",
                new HashSet<>());

        s1.getApplications().add(app1);
        s2.getApplications().add(app1);

        repo.save(s1);
        repo.save(s2);

    }

    @Override
    public void run(ServiceCatalogConfiguration configuration,
                    Environment environment) {

        final ServiceInfoRepo serviceInfoRepo = context.getBean(ServiceInfoRepo.class);
        final ApplicationInfoRepo applicationInfoRepo = context.getBean(ApplicationInfoRepo.class);
        environment.jersey().register(new ServiceResource(serviceInfoRepo));
        environment.jersey().register(new ApplicationResource(applicationInfoRepo));
    }
}
