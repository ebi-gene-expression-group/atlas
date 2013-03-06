package uk.ac.ebi.atlas.commons.configuration;

import com.google.common.collect.Sets;
import org.apache.commons.configuration.XMLConfiguration;
import uk.ac.ebi.atlas.model.differential.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;

import java.util.Set;

public class ContrastsConfiguration {


    private XMLConfiguration xmlConfiguration;
    private String experimentAccession;

    public ContrastsConfiguration(XMLConfiguration xmlConfiguration, String experimentAccession) {


        this.xmlConfiguration = xmlConfiguration;
        this.experimentAccession = experimentAccession;
    }

    Set<String> getAssayAccessions(String id) {
        Set<String> assays = Sets.newHashSet();
        String[] list = xmlConfiguration.getStringArray("assay_groups/assay_group[@id=\'" + id + "\']/assay");
        for (Object assayObject : list) {
            assays.add((String) assayObject);
        }
        return assays;
    }

    AssayGroup getAssayGroup(String id) {
        return new AssayGroup(xmlConfiguration.getStringArray("assay_groups/assay_group[@id=\'" + id + "\']/assay"));

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
        String name = xmlConfiguration.getString("contrasts/contrast[@id=\'" + id + "\']/name");
        String reference = xmlConfiguration.getString("contrasts/contrast[@id=\'" + id + "\']/reference_assay_group");
        String test = xmlConfiguration.getString("contrasts/contrast[@id=\'" + id + "\']/test_assay_group");
        return new Contrast(id, getAssayGroup(reference), getAssayGroup(test), name);
    }
}
