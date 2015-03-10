package no.braseth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ServiceCatalogConfiguration extends Configuration {
    @NotEmpty
    private String exampleProperty = "DefaultFoo";

    @JsonProperty
    public String getExampleProperty() {
        return exampleProperty;
    }

    @JsonProperty
    public void setExampleProperty(String name) {
        this.exampleProperty = name;
    }
}
