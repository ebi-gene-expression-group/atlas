package uk.ac.ebi.atlas.search.geneids;

import com.google.auto.value.AutoValue;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@AutoValue
public abstract class GeneQuery {
    // Replace queryTerm and category with a SemanticQuery when/if we allow multiple search terms, or maybe delete this
    // class altogether and have the service accept a SemanticQuery and a Species as two arguments. If we do the second
    // option, we should review SemanticQuery not to allow empty values and see how that impacts Expression Atlas.
    public abstract String queryTerm();
    public abstract Optional<BioentityPropertyName> category();
    public abstract Optional<Species> species();

    public static GeneQuery create(String queryTerm) {
        return create(queryTerm, Optional.empty(), Optional.empty());
    }

    public static GeneQuery create(String queryTerm, BioentityPropertyName category) {
        return create(queryTerm, Optional.of(category), Optional.empty());
    }

    public static GeneQuery create(String queryTerm, Species species) {
        return create(queryTerm, Optional.empty(), Optional.of(species));
    }

    public static GeneQuery create(String queryTerm, BioentityPropertyName category, Species species) {
        return create(queryTerm, Optional.of(category), Optional.of(species));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static GeneQuery create(String queryTerm,
                                    Optional<BioentityPropertyName> category,
                                    Optional<Species> species) {
        if (isBlank(queryTerm)) {
            throw new IllegalArgumentException("Query term cannot be blank");
        }
        return new AutoValue_GeneQuery(queryTerm, category, species);
    }
}
