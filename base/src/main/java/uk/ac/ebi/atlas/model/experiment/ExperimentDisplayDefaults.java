package uk.ac.ebi.atlas.model.experiment;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AutoValue
public abstract class ExperimentDisplayDefaults {


    abstract Map<String, String> defaultFilterValues();
    public abstract List<String> prescribedOrderOfFilters();


    public String defaultFilterValueForFactor(String factorHeader){
        return defaultFilterValues().containsKey(Factor.normalize(factorHeader))
                ? defaultFilterValues().get(Factor.normalize(factorHeader))
                : "";
    }
    /*
    The curators used to have to specify a slice of the experiment they want displayed, say which dimension to splay
    (defaultQueryFactorType) and values for other filters. How cumbersome!
     */
    String defaultQueryFactorType() {
        for(String filter: prescribedOrderOfFilters()){
            if(! defaultFilterValues().keySet().contains(filter)){
                return filter;
            }
        }
        return "";
    }

    /*
    See factors.xml e.g.
        <defaultFilterFactors>
        <filterFactor>
            <type>TIME</type>
            <value>4 day</value>
        </filterFactor>
        <filterFactor>
            <type>CELL_TYPE</type>
            <value>CD4+ T cell</value>
        </filterFactor>
        <filterFactor>
            <type>INFECT</type>
            <value>Plasmodium chabaudi chabaudi</value>
        </filterFactor>
    </defaultFilterFactors>
    <defaultQueryFactorType>INDIVIDUAL</defaultQueryFactorType>
    <menuFilterFactorTypes>CELL_TYPE, INDIVIDUAL, INFECT, TIME</menuFilterFactorTypes>

     */
    public static ExperimentDisplayDefaults create(){
        return create(ImmutableMap.<String, String>of(), ImmutableList.<String>of());
    }

    public static ExperimentDisplayDefaults create(Collection<Factor> defaultFilterFactors,
                                                   List<String> preferredOrderOfFilters){
        ImmutableMap.Builder<String, String> b = ImmutableMap.builder();
        for(Factor factor : defaultFilterFactors){
            b.put(factor.getType(), factor.getValue());
        }
        return create(b.build(),preferredOrderOfFilters);
    }


    public static ExperimentDisplayDefaults create(Map<String, String> defaultFilterValues,
                                                   List<String> preferredOrderOfFilters){
        return new AutoValue_ExperimentDisplayDefaults(defaultFilterValues,preferredOrderOfFilters);
    }


}
