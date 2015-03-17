package no.braseth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@ApiModel
public class ApplicationRegistration {
    String description;

    String server;

    String environment;

    String name;

    String applicationGroup;

    Set<String> providedServices = new HashSet<>();

    Set<String> consumedServices = new HashSet<>();

    public ApplicationRegistration() { /* For Jackson */ }

    public String getDescription() {
        return description;
    }

    public String getServer() {
        return server;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getName() {
        return name;
    }

    public Set<String> getProvidedServices() {
        return providedServices;
    }

    public Set<String> getConsumedServices() {
        return consumedServices;
    }

    public String getApplicationGroup() {
        return applicationGroup;
    }
}
