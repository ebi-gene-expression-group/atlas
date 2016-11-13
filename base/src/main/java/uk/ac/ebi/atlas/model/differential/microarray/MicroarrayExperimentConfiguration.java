package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Sets;
import org.apache.commons.configuration2.XMLConfiguration;
import org.w3c.dom.Document;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;

import java.nio.file.Path;
import java.util.SortedSet;

public class MicroarrayExperimentConfiguration extends ExperimentConfiguration {

    private SortedSet<String> arrayDesignAccessions;

    public MicroarrayExperimentConfiguration(XMLConfiguration xmlConfiguration, Document document, Path pathToFile) {
        super(xmlConfiguration, document, pathToFile);
        this.arrayDesignAccessions = Sets.newTreeSet(xmlConfiguration.getList(String.class, "analytics/array_design"));
    }

    public SortedSet<String> getArrayDesignAccessions() {
        return arrayDesignAccessions;
    }
}
