package no.braseth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import no.braseth.core.ProcessInfo;
import no.braseth.core.ProvidesServiceInfo;
import no.braseth.core.ServiceInfo;
import org.springframework.data.neo4j.annotation.NodeEntity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@NodeEntity
public class BasicServiceInfo {

    private final String name;

    private List<String> consumingProcesses = new ArrayList<>();
    private List<String> providingProcesses = new ArrayList<>();

    public BasicServiceInfo(ServiceInfo serviceInfo) {
        name = serviceInfo.name;
        for (ProcessInfo consumer : serviceInfo.consumingProcesses) {
            consumingProcesses.add(consumer.getDescription());
        }
        for (ProvidesServiceInfo provider : serviceInfo.providingProcesses) {
            providingProcesses.add(provider.getProcess().getDescription());
        }
    }

    public BasicServiceInfo(String name, List<String> providers, List<String> consumers) {
        this.name = name;
        this.providingProcesses = providers;
        this.consumingProcesses = consumers;
    }

    public String getName() {
        return name;
    }

    public List<String> getConsumingProcesses() {
        return consumingProcesses;
    }

    public List<String> getProvidingProcesses() {
        return providingProcesses;
    }
}
