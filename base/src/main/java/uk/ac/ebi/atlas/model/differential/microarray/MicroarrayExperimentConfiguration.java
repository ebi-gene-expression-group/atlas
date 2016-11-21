package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.commons.readers.XmlReader;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;

import java.util.SortedSet;

public class MicroarrayExperimentConfiguration extends ExperimentConfiguration {

    private SortedSet<String> arrayDesignAccessions;

    public MicroarrayExperimentConfiguration(XmlReader xmlReader) {
        super(xmlReader);
        this.arrayDesignAccessions = Sets.newTreeSet(xmlReader.getList("analytics/array_design"));
    }

    public SortedSet<String> getArrayDesignAccessions() {
        return arrayDesignAccessions;
    }
}
