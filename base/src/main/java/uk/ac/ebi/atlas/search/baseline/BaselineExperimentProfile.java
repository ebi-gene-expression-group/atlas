package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.Map;

public class BaselineExperimentProfile extends Profile<FactorAcrossExperiments, BaselineExpression, BaselineExperimentProfile> implements Comparable<BaselineExperimentProfile> {

    private final FactorGroup filterFactors;

    private BaselineExperiment experiment;

    public BaselineExperimentProfile(BaselineExperiment experiment, FactorGroup filterFactors) {
        super(joinIntoText(
                experiment.getAccession(),
                filterFactors),
                experiment.getDisplayName());
        this.experiment = experiment;
        this.filterFactors = filterFactors;
    }

    @Override
    protected BaselineExperimentProfile createEmptyCopy() {
        return new BaselineExperimentProfile(experiment, filterFactors);
    }

    @Override
    public String getId() {
        return experiment.getAccession();
    }

    @Override
    /**
     * RNA-seq experiments should come before other types of baseline experiments;
     * Within the same experiment type, the experiments with more conditions with expressions in them should come to the top.
     * If the number of conditions with expression is the same, the sorting should be alphabetic - by the experiment display name.
     */
    public int compareTo(BaselineExperimentProfile other) {
        return ComparisonChain.start()
                .compareFalseFirst(other.experiment.getType().isRnaSeqBaseline(),this.experiment.getType().isRnaSeqBaseline())
                .compare(other.getSpecificity(), this.getSpecificity())
                .compare(other.getName(), this.getName())
                .result();
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

    public Pair<String, FactorGroup> getExperimentSlice(){
        return Pair.of(experiment.getAccession(), filterFactors);
    }

    public String getExperimentType() {
        return experiment.getType().toString();
    }

    @Override
    public Map<String, String> properties() {
        return ImmutableMap.<String, String>builder()
                .put("id", super.properties().get("id"))
                .put("name", getShortName())
                .put("experimentType", experiment.getType().toString())
                .build();
    }
}
