package uk.ac.ebi.atlas.commons.configuration;

import com.google.common.collect.Sets;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import uk.ac.ebi.atlas.model.differential.Contrast;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ContrastsConfiguration {


    private XMLConfiguration xmlConfiguration;
    private String experimentAccession;

    public ContrastsConfiguration(XMLConfiguration xmlConfiguration, String experimentAccession) {


        this.xmlConfiguration = xmlConfiguration;
        this.experimentAccession = experimentAccession;
    }

    Set<String> getAssayAccessions(String id){
        Set<String> assays = Sets.newHashSet();
        List<Object> list = xmlConfiguration.getList("contrast_info.assasy_groups.assay_group[@id=" + id + "]/assay");
        for (Object assayObject : list) {
            assays.add((String)assayObject);
        }
        return assays;
    }

    public Set<Contrast> getContrasts() {

        List contrastElements = xmlConfiguration.getList("contrast_info.contrasts");

        Set<Contrast> contrasts = Sets.newHashSet();
        for(Iterator it = contrastElements.iterator(); it.hasNext();) {

            HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();

        }
        return contrasts;
    }
}
