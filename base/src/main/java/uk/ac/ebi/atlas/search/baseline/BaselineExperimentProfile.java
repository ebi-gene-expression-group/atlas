package uk.ac.ebi.atlas.search.baseline;

import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.Map;

public class BaselineExperimentProfile extends BaselineProfile implements Comparable<BaselineExperimentProfile> {

    private final FactorGroup filterFactors;

    private final Integer nonFilterFactorsSize;

    private ExperimentType experimentType;
    
    public BaselineExperimentProfile(BaselineExperimentSlice experimentSlice) {
        super(experimentSlice.experimentAccession(), experimentSlice.experimentDisplayName());
        filterFactors = experimentSlice.filterFactors();
        nonFilterFactorsSize = experimentSlice.nonFilterFactors().size();
        experimentType = experimentSlice.getExperimentType();
    }

    public FactorGroup getFilterFactors() {
        return filterFactors;
    }

    @Override
    /**
     * RNA-seq experiments should come before other types of baseline experiments;
     * Within the same experiment type, the experiments with more conditions with expressions in them should come to the top.
     * If the number of conditions with expression is the same, the sorting should be alphabetic - by the experiment display name.
     */
    public int compareTo(BaselineExperimentProfile other) {
        int experimentTypeComparison =
                this.experimentType == other.experimentType ?
                        0 : (this.experimentType == ExperimentType.RNASEQ_MRNA_BASELINE ? -1 : 1);
        int comparison = (other.nonFilterFactorsSize).compareTo(this.nonFilterFactorsSize);
        if (experimentTypeComparison != 0) {
            return experimentTypeComparison;
        } else {
            return (comparison != 0) ? comparison : (this.getName().compareTo(other.getName()));
        }
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getName());
        if (!filterFactors.isEmpty() && !filterFactors.containsOnlyOrganism()) {
            sb.append(" - ").append(FilterFactorsConverter.prettyPrint(filterFactors));
        }
        return sb.toString();
    }

    String getShortName() {
        StringBuilder sb = new StringBuilder();
        String name = super.getName();
        if(StringUtils.startsWithAny(name, new String[]{"Tissues - ", "Cell Type - ", "Cell Lines - ", "Individual - ",
                                                        "Developmental Stages - ", "Strains - ", "Cultivars - ", "Ecotypes - "})) {
            String shortenName = name.substring(0, name.indexOf("-") + 2);
            name = name.replace(shortenName, "");
            sb.append(name);
        } else if (StringUtils.startsWith(name, "Proteomics - Tissues - ")) {
            name = name.replace("Proteomics - Tissues - ", "");
            sb.append(name);
        } else {
            sb.append(name);
        }

        if (!filterFactors.isEmpty() && !filterFactors.containsOnlyOrganism()) {
            sb.append(" - ").append(FilterFactorsConverter.prettyPrint(filterFactors));
        }
        return sb.toString();
    }

    public String getExperimentType() {
        return experimentType.toString();
    }

    @Override
    public Map<String, String> properties() {
        Map<String, String> result = super.properties();
        result.put("name", getShortName());
        result.put("experimentType", experimentType.toString());
        result.put("serializedFilterFactors",FilterFactorsConverter.serialize(getFilterFactors()));
        return result;
    }
}
