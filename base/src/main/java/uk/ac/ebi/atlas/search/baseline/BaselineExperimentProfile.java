package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.Map;

public class BaselineExperimentProfile extends Profile<FactorAcrossExperiments, BaselineExpression> implements Comparable<BaselineExperimentProfile> {

    private final FactorGroup filterFactors;
    private final Integer nonFilterFactorsSize;

    private String experimentAccession;
    private ExperimentType experimentType;

    public BaselineExperimentProfile(BaselineExperimentSlice experimentSlice) {
        super(joinIntoText(
                experimentSlice.experimentAccession(),
                experimentSlice.filterFactors()),
                experimentSlice.experimentDisplayName());
        filterFactors = experimentSlice.filterFactors();
        nonFilterFactorsSize = experimentSlice.nonFilterFactors().size();
        experimentAccession = experimentSlice.experimentAccession();
        experimentType = experimentSlice.getExperimentType();
    }

    @Override
    public String getId() {
        return experimentAccession;
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
    protected void updateStateAfterAddingExpression(BaselineExpression expression) {
        // used to maintain maxes and mins but it really isn't necessary
    }

    @Override
    public String getName() {
        return joinIntoText(super.getName(), filterFactors);
    }

    private static String joinIntoText(String core, FactorGroup filterFactors) {
        StringBuilder sb = new StringBuilder();
        sb.append(core);
        if (!filterFactors.isEmpty() && !filterFactors.containsOnlyOrganism()) {
            sb.append(" - ").append(factorsAsText(filterFactors));
        }
        return sb.toString();
    }

    static String factorsAsText(Iterable<Factor> factors) {
        if (!factors.iterator().hasNext()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Factor factor : factors) {
            sb.append(factor.getValue()).append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    static String shorten(String name) {
        if (StringUtils.startsWithAny(name,
                new String[]{
                        "Tissues - ",
                        "Cell Type - ",
                        "Cell Lines - ",
                        "Individual - ",
                        "Developmental Stages - ",
                        "Strains - ",
                        "Cultivars - ",
                        "Ecotypes - "})) {
            String shortenName = name.substring(0, name.indexOf("-") + 2);
            return name.replace(shortenName, "");
        } else if (StringUtils.startsWith(name, "Proteomics - Tissues - ")) {
            return name.replace("Proteomics - Tissues - ", "");
        } else {
            return name;
        }
    }

    String getShortName() {
        return joinIntoText(shorten(super.getName()),
                filterFactors.containsOnlyOrganism() ? new FactorSet() : filterFactors
                );
    }

    public String getExperimentType() {
        return experimentType.toString();
    }

    @Override
    public Map<String, String> properties() {
        return ImmutableMap.<String, String>builder()
                .put("id", super.properties().get("id"))
                .put("name", getShortName())
                .put("experimentType", experimentType.toString())
                .build();
    }
}
