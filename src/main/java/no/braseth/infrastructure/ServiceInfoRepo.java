package no.braseth.infrastructure;

import com.sun.jersey.server.impl.uri.UriHelper;
import no.braseth.core.ServiceInfo;
import no.braseth.dto.BasicServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServiceInfoRepo {

    private static String FIND_ALL_SERVICES =
            "MATCH (s:ServiceInfo)<-[:PROVIDES_SERVICE]-(p:ProcessInfo)," +
            "(s)<-[:CONSUMES_SERVICE]-(c:ProcessInfo)" +
            "return " +
                    "s.name as name, " +
                    "collect(distinct p.description) as providingProcesses, " +
                    "collect(distinct c.description) as consumingProcesses";

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    Neo4jTemplate neo4j;

    @Transactional
    @SuppressWarnings("unchecked")
    public List<BasicServiceInfo> findAll() {
        Result<Map<String, Object>> results = neo4j.query(FIND_ALL_SERVICES, new HashMap<>());
        List<BasicServiceInfo> infos = new ArrayList<>();

        for (Map<String, Object> result : results) {
            String nameString = (String) result.get("name");
            List<String> providers = (List<String>) result.get("providingProcesses");
            List<String> consumers = (List<String>) result.get("consumingProcesses");
            infos.add(new BasicServiceInfo(nameString, providers, consumers));
        }

        return infos;

    }

    @Transactional
    @SuppressWarnings("unchecked")
    public BasicServiceInfo find(String name) {
        Map<String, Object> vals = new HashMap<>();
        vals.put("name", name);

        Result<Map<String, Object>> result = neo4j.query("MATCH (s:ServiceInfo{name:{name}})<-[:PROVIDES_SERVICE]-(p:ProcessInfo), \n" +
                        "(s)<-[:CONSUMES_SERVICE]-(c:ProcessInfo)\n" +
                        "return s.name as name, collect(distinct p.description) as providingProcesses, collect(distinct c.description) as consumingProcesses",
                vals
        );
        Map<String, Object> single = result.single();
        String nameString = (String) single.get("name");
        List<String> providers = (List<String>) single.get("providingProcesses");
        List<String> consumers = (List<String>) single.get("consumingProcesses");


        return new BasicServiceInfo(nameString, providers, consumers);
    }


}
