package uk.ac.ebi.atlas.utils;

import com.google.common.base.Preconditions;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.StringReader;

@Named
@Scope("prototype")
public class ArrayExpressClient {
    private static final Logger logger = Logger.getLogger(ArrayExpressClient.class);
    private static final String EXPERIMENT_NAME_XPATH = "//experiment/name/text()";
    private static final String EXPERIMENT_ASSAYS_XPATH = "//experiment/assays/text()";

    private RestTemplate restTemplate;

    private ApplicationProperties applicationProperties;

    @Inject
    public ArrayExpressClient(RestTemplate restTemplate, ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    public String fetchExperimentName(String experimentAccession) {

        String experimentXML = restTemplate.getForObject(applicationProperties.getArrayExpressRestURL(experimentAccession), String.class);

        return parseOutExperimentName(experimentXML);

    }

    private String parseOutExperimentName(String experimentXML) {
        try {
            Builder parser = new Builder();
            Document doc = parser.build(new StringReader(experimentXML));
            return buildFullName(parseValue(doc, EXPERIMENT_NAME_XPATH), parseValue(doc, EXPERIMENT_ASSAYS_XPATH));

        } catch (ParsingException | IOException ex) {
            throw new IllegalStateException("Cannot access ArrayExpress", ex);
        }
    }

    private String parseValue(Document doc, String xpathQuery) {
        Nodes nodes = doc.query(xpathQuery);
        Preconditions.checkState(nodes.size() == 1, "Experiment name cannot be fetched from ArrayExpress");

        return nodes.get(0).getValue();
    }

    private String buildFullName(String name, String assayNumber) {
        StringBuilder sb = new StringBuilder();
        return sb.append(name)
                .append(" (")
                .append(assayNumber)
                .append(" assays)")
                .toString();

    }
}
