package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Sets;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.commons.readers.XmlReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaselineExperimentConfiguration {

    private XmlReader xmlReader;

    public BaselineExperimentConfiguration(XmlReader xmlReader) {
        this.xmlReader = xmlReader;
    }

    public String getExperimentDisplayName() {
        return xmlReader.getString("landingPageDisplayName");
    }

    public List<String> getDataProviderURL() {
        return xmlReader.getList("dataProviderURL");
    }

    public List<String> getDataProviderDescription() {
        return xmlReader.getList("dataProviderDescription");
    }

    public Set<Factor> getDefaultFilterFactors() {

        Set<Factor> defaultFilterFactors = new HashSet<>();
        List<HierarchicalConfiguration<ImmutableNode>> fields = xmlReader.configurationsAt("defaultFilterFactors.filterFactor");
        for (HierarchicalConfiguration sub : fields) {
            String factorType = sub.getString("type");
            String factorValue = sub.getString("value");
            defaultFilterFactors.add(new Factor(factorType, factorValue));
        }

        return defaultFilterFactors;
    }

    public String getDefaultQueryFactorType() {

        String defaultQueryFactorType = xmlReader.getString("defaultQueryFactorType");
        if (defaultQueryFactorType == null || defaultQueryFactorType.trim().length() == 0) {
            throw new IllegalStateException("No defaultQueryFactorType found in factors file.");
        }

        return defaultQueryFactorType;
    }

    public boolean orderCurated() {
        return "curated".equals(xmlReader.getString("orderFactor"));
    }

    public Set<String> getMenuFilterFactorTypes() {
        return Sets.newHashSet(xmlReader.getList("menuFilterFactorTypes"));
    }

    public Map<String, String> getSpeciesMapping() {

        Map<String, String> mapping = new HashMap<>();
        List<HierarchicalConfiguration<ImmutableNode>> fields = xmlReader.configurationsAt("speciesMapping.mapping");
        for (HierarchicalConfiguration sub : fields) {
            String samples = sub.getString("samples").toLowerCase();
            String genes = sub.getString("genes").toLowerCase();
            mapping.put(samples, genes);
        }

        return mapping;
    }

    public List<String> getAlternativeViews() {
        List<String> result = new ArrayList<>();
        for(Object o:  xmlReader.getList("alternativeView")){
            if(o.toString().matches("E-\\w+-\\d+")){
                result.add(o.toString());
            }
        }

        return result;
    }
    public String disclaimer(){
        if("true".equals(xmlReader.getString("fortLauderdale"))){
            return "fortLauderdale";
        } else if (StringUtils.isNotEmpty(xmlReader.getString("disclaimer"))){
            return xmlReader.getString("disclaimer");
        } else {
            return "";
        }
    }

    public boolean isFortLauderdale() {
        return "true".equals(xmlReader.getString("fortLauderdale"));
    }
}
