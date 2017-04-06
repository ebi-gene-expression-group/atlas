package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.commons.readers.XmlReader;

import java.util.*;

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

        for (Map.Entry<String, String> e: xmlReader.getMap("defaultFilterFactors", "type", "value").entrySet()) {
            defaultFilterFactors.add(new Factor(e.getKey(), e.getValue()));
        }

        return defaultFilterFactors;
    }

    public String getDefaultQueryFactorType() {

        String defaultQueryFactorType = xmlReader.getString("defaultQueryFactorType");
        Preconditions.checkState(StringUtils.isNotEmpty(defaultQueryFactorType), "defaultQueryFactorType missing from factors file!");

        return defaultQueryFactorType;
    }

    public boolean orderCurated() {
        return "curated".equals(xmlReader.getString("orderFactor"));
    }

    public List<String> getMenuFilterFactorTypes() {
        String s = xmlReader.getString("menuFilterFactorTypes");
        if(StringUtils.isEmpty(s)){
            return ImmutableList.of();
        } else {
            return ImmutableList.copyOf(Arrays.asList(s.split("\\W*,\\W*")));
        }
    }

    public Map<String, String> getSpeciesMapping() {
        return xmlReader.getMap("speciesMapping", "samples", "genes");
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
