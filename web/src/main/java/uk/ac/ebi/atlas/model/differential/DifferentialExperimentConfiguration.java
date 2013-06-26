package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Sets;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import java.util.Set;

public class DifferentialExperimentConfiguration {

    private XMLConfiguration xmlConfiguration;

    public DifferentialExperimentConfiguration(XMLConfiguration xmlConfiguration) {
        this.xmlConfiguration = xmlConfiguration;
    }

    public Set<Contrast> getContrasts() {

        Set<Contrast> contrasts = Sets.newLinkedHashSet();
        String[] ids = xmlConfiguration.getStringArray("analytics/contrasts/contrast/@id");
        for (String id : ids) {
            Contrast contrast = getContrast(id);
            contrasts.add(contrast);
        }
        return contrasts;
    }

    Contrast getContrast(String id) {
        Configuration configuration = xmlConfiguration.configurationAt("analytics/contrasts/contrast[@id=\'" + id + "\']");
        String name = configuration.getString("name");
        String reference = configuration.getString("reference_assay_group");
        String test = configuration.getString("test_assay_group");
        return new Contrast(id, getAssayGroup(reference), getAssayGroup(test), name);
    }

    AssayGroup getAssayGroup(String id) {
        String[] assayAccessions = xmlConfiguration.getStringArray("analytics/assay_groups/assay_group[@id=\'" + id + "\']/assay");
        return new AssayGroup(assayAccessions);
    }

}
