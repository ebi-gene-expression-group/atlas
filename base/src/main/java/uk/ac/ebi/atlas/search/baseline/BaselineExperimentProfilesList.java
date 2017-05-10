
package uk.ac.ebi.atlas.search.baseline;


import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.GeneProfilesList;

import java.util.List;
import java.util.TreeSet;

public class BaselineExperimentProfilesList extends GeneProfilesList<BaselineExperimentProfile> {

    public BaselineExperimentProfilesList() {
    }

    public List<FactorAcrossExperiments> getFactorsAcrossExperiments(){
        TreeSet<FactorAcrossExperiments> result = new TreeSet<>();
        for(BaselineExperimentProfile profile: this){

            result.addAll(profile.getConditions());
        }

        return ImmutableList.copyOf(result);
    }




}
