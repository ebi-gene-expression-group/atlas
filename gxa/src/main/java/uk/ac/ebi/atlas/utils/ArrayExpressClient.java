package uk.ac.ebi.atlas.utils;

import uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf.IdfParser;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.InputSource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class ArrayExpressClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayExpressClient.class);

    private static final String EXPERIMENT_NAME_XPATH = "//experiment/name";

    private String arrayExpressUrlTemplate;
    private RestTemplate restTemplate;
    private IdfParser idfParser;

    @Inject
    public ArrayExpressClient(RestTemplate restTemplate,
                              @Value("#{configuration['experiment.arrayexpress.rest.url.template']}") String arrayExpressUrlTemplate,
                              IdfParser idfParser) {
        this.restTemplate = restTemplate;
        this.arrayExpressUrlTemplate = arrayExpressUrlTemplate;
        this.idfParser = idfParser;
    }

    public String fetchExperimentName(String experimentAccession) {
        try {
            String experimentXML = restTemplate.getForObject(MessageFormat.format(arrayExpressUrlTemplate, experimentAccession), String.class);
            return parseExperimentName(experimentXML);
        } catch (Exception e) {
            LOGGER.error("There was an error parsing the experiment name from ArrayExpress, falling back to IDF file: " + e);
            String experimentName = idfParser.parse(experimentAccession).getLeft();

            if (experimentName.isEmpty()) {
                Throwables.propagate(e);    // Give cache loaders a chance to set the name from the DTO
            }

            return experimentName;
        }
    }

    private String parseExperimentName(String experimentXML) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        InputSource inputSource = new InputSource(new StringReader(experimentXML));
        String experimentName = xpath.evaluate(EXPERIMENT_NAME_XPATH, inputSource);

        if (experimentName.isEmpty()) {
            throw new RuntimeException("Experiment name not present or empty");
        }

        return experimentName;
    }
}
