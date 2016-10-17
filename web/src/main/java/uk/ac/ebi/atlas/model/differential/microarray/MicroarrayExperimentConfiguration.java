package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Sets;
import org.apache.commons.configuration.XMLConfiguration;
import org.w3c.dom.Document;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;

import java.util.List;
import java.util.SortedSet;

public class MicroarrayExperimentConfiguration extends ExperimentConfiguration {

    private SortedSet<String> arrayDesignAccessions;

    public MicroarrayExperimentConfiguration(XMLConfiguration xmlConfiguration, Document document) {
        super(xmlConfiguration, document);
        this.arrayDesignAccessions = Sets.newTreeSet((List) xmlConfiguration.getList("analytics/array_design"));
    }

    public SortedSet<String> getArrayDesignAccessions() {
        return arrayDesignAccessions;
    }
}
