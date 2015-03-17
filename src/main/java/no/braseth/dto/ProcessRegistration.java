package no.braseth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProcessRegistration {
    String description;

    String server;

    String environment;

    String application;

    Map<String,String> providedServices = new HashMap<>();

    Set<String> consumedServices = new HashSet<>();

    public ProcessRegistration() { /* For Jackson */ }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Set<String> getConsumedServices() {
        return consumedServices;
    }

    public void setConsumedServices(Set<String> consumedServices) {
        this.consumedServices = consumedServices;
    }

    public Map<String, String> getProvidedServices() {
        return providedServices;
    }
}
