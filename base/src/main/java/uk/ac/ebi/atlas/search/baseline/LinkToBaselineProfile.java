package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentpage.baseline.LinkToExperimentPage;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.util.Map;

public class LinkToBaselineProfile extends LinkToExperimentPage<BaselineExperimentProfile> {

    public LinkToBaselineProfile(SemanticQuery geneQuery) {
        super(geneQuery);

    }

    @Override
    public Map<String, String> perInputQueryParameters(BaselineExperimentProfile baselineExperimentProfile) {
        Pair<String, FactorGroup> accessionAndFilterFactors = baselineExperimentProfile.getExperimentSlice();

        return accessionAndFilterFactors.getRight().isEmpty()
                ? ImmutableMap.<String, String>of()
                : ImmutableMap.of("filterFactors", new RichFactorGroup(accessionAndFilterFactors.getRight()).asUrlEncodedJson());
    }

    @Override
    public String accession(BaselineExperimentProfile baselineExperimentProfile) {
        return baselineExperimentProfile.getExperimentSlice().getLeft();
    }
}
