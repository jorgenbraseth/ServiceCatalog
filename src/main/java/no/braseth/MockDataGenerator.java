package no.braseth;

import no.braseth.core.ProcessInfo;
import no.braseth.core.ServiceInfo;
import org.jfairy.Fairy;

public class MockDataGenerator {

    public static ServiceInfo service() {
        ServiceInfo s = new ServiceInfo();
        s.name = Fairy.create().textProducer().sentence(1);
        return s;
    }

    public static ProcessInfo process() {
        return new ProcessInfo()
                .description(Fairy.create().textProducer().sentence(2));

    }
}
