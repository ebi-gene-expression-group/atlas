package uk.ac.ebi.atlas.commons.configuration;

import com.google.common.collect.Sets;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import uk.ac.ebi.atlas.model.differential.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;

import javax.inject.Named;
import java.util.Set;
import java.util.SortedSet;

public class ContrastsConfiguration {

    private XMLConfiguration xmlConfiguration;

    public ContrastsConfiguration(XMLConfiguration xmlConfiguration) {
        this.xmlConfiguration = xmlConfiguration;
    }

    public Set<Contrast> getContrasts() {

        Set<Contrast> contrasts = Sets.newHashSet();
        String[] ids = xmlConfiguration.getStringArray("contrasts/contrast/@id");
        for (String id : ids) {
            Contrast contrast = getContrast(id);
            contrasts.add(contrast);
        }
        return contrasts;
    }

    Contrast getContrast(String id) {
        Configuration contrastConfiguration = xmlConfiguration.configurationAt("contrasts/contrast[@id=\'" + id + "\']");
        String name = contrastConfiguration.getString("name");
        String reference = contrastConfiguration.getString("reference_assay_group");
        String test = contrastConfiguration.getString("test_assay_group");
        return new Contrast(id, getAssayGroup(reference), getAssayGroup(test), name);
    }

    AssayGroup getAssayGroup(String id) {
        String[] assayAccessions = xmlConfiguration.getStringArray("assay_groups/assay_group[@id=\'" + id + "\']/assay");
        return new AssayGroup(assayAccessions);
    }

}
