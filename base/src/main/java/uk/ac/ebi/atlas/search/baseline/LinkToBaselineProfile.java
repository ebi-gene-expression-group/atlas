package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class LinkToBaselineProfile implements Function<BaselineExperimentProfile, URI> {

    private final URI experimentsLocation;
    private final Map<String, String> params;

    public LinkToBaselineProfile(SemanticQuery geneQuery){
        try {
            experimentsLocation = new URI("experiments/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        params = ImmutableMap.of("geneQuery", geneQuery.toUrlEncodedJson());

    }

    @Override
    public URI apply(BaselineExperimentProfile baselineExperimentProfile){
        return experimentsLocation.resolve(baselineExperimentProfile.getId()+
                "?"+ Joiner.on("&").withKeyValueSeparator("=").join(params.entrySet())
        );
    }

}
