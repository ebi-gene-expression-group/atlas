package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public abstract class LinkToExperimentPage<Input> implements Function<Input, URI> {

    private final URI experimentsLocation;
    private final Map<String, String> params;

    public LinkToExperimentPage(SemanticQuery geneQuery) {
        try {
            experimentsLocation = new URI("experiments/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        params = geneQuery.size()>0 ? ImmutableMap.of("geneQuery", geneQuery.toUrlEncodedJson()): ImmutableMap.<String, String>of();

    }

    public abstract Map<String, String> perInputQueryParameters(Input input);

    public abstract String accession(Input input);

    @Nullable
    @Override
    public URI apply(@Nullable Input input) {

        Map<String, String> allQueryParams = ImmutableMap.<String, String>builder().putAll(params).putAll(perInputQueryParameters(input)).build();

        return experimentsLocation.resolve(accession(input) +
                (allQueryParams.isEmpty() ? "" : "?" + Joiner.on("&").withKeyValueSeparator("=").join(allQueryParams))
        );
    }
}
