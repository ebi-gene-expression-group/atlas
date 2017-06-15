package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.XmlReader;

import java.util.*;
import java.util.stream.Collectors;

public class BaselineExperimentConfiguration {

    private XmlReader xmlReader;

    public BaselineExperimentConfiguration(XmlReader xmlReader) {
        this.xmlReader = xmlReader;
    }

    public String getExperimentDisplayName() {
        return xmlReader.getString("landingPageDisplayName");
    }

    public List<String> getDataProviderURL() {
        return commaSeparatedStringToList(xmlReader.getString("dataProviderURL"));
    }

    public List<String> getDataProviderDescription() {
        return commaSeparatedStringToList(xmlReader.getString("dataProviderDescription"));
    }

    public Set<Factor> getDefaultFilterFactors() {

        Set<Factor> defaultFilterFactors = xmlReader.getMap("defaultFilterFactors", "type", "value").entrySet().stream().map(e -> new Factor(e.getKey(), e.getValue())).collect(Collectors.toSet());

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
        return commaSeparatedStringToList(xmlReader.getString("menuFilterFactorTypes"));
    }

    public Map<String, String> getSpeciesMapping() {
        return xmlReader.getMap("speciesMapping", "samples", "genes");
    }

    public List<String> getAlternativeViews() {
        List<String> result = xmlReader.getList("alternativeView").stream().filter(o -> o.toString().matches("E-\\w+-\\d+")).map(Object::toString).collect(Collectors.toList());

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

    static List<String> commaSeparatedStringToList(String s){
        if(StringUtils.isEmpty(s)){
            return ImmutableList.of();
        } else {
            return ImmutableList.copyOf(Arrays.asList(s.split("\\W*,\\W*")));
        }
    }
}
