package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class LinkToBaselineProfile implements Function<BaselineExperimentProfile, URI> {

    private final URI experimentsLocation;
    private final Map<String, String> params;

    public LinkToBaselineProfile(SemanticQuery geneQuery) {
        try {
            experimentsLocation = new URI("experiments/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        params = geneQuery.size()>0 ? ImmutableMap.of("geneQuery", geneQuery.toUrlEncodedJson()): ImmutableMap.<String, String>of();

    }

    @Override
    public URI apply(BaselineExperimentProfile baselineExperimentProfile) {
        Pair<String, FactorGroup> accessionAndFilterFactors = baselineExperimentProfile.getExperimentSlice();

        Map<String, String> perProfileParams = accessionAndFilterFactors.getRight().isEmpty()
                ? ImmutableMap.<String, String>of()
                : ImmutableMap.of("filterFactors", new RichFactorGroup(accessionAndFilterFactors.getRight()).asUrlEncodedJson());

        Map<String, String> allQueryParams = ImmutableMap.<String, String>builder().putAll(params).putAll(perProfileParams).build();

        return experimentsLocation.resolve(accessionAndFilterFactors.getLeft() +
                (allQueryParams.isEmpty() ? "" : "?" + Joiner.on("&").withKeyValueSeparator("=").join(allQueryParams))
        );
    }
}
