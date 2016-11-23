package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class CoexpressedGenesService {

    final CoexpressedGenesDao coexpressedGenesDao;

    public CoexpressedGenesService(CoexpressedGenesDao coexpressedGenesDao) {
        this.coexpressedGenesDao =coexpressedGenesDao;
    }

    Pair<GeneQueryResponse, List<String>> extendGeneQueryResponseWithCoexpressions(GeneQueryResponse originalResponse,
                                                                                   BaselineExperiment experiment,
                                                                                   Map<String,Integer> coexpressionsRequested,
                                                                                   boolean alsoIncludeOriginalGeneQueryResponse) {
        GeneQueryResponse newGeneQuery = new GeneQueryResponse();
        List<String> prescribedOrder = new ArrayList<>();
        for (String queryTerm: originalResponse.getQueryTerms()) {
            Collection<String> idsForThatTerm = originalResponse.getIds(queryTerm);
            Set<String> idsToAdd = new HashSet<>();
            for (String id : idsForThatTerm) {
                if( alsoIncludeOriginalGeneQueryResponse) {
                    prescribedOrder.add(id);
                }
                if (coexpressionsRequested.containsKey(id.toUpperCase())) {
                    List<String> coexpressionsForThisId = fetchCoexpressions(experiment.getAccession(), id, coexpressionsRequested.get(id.toUpperCase()));
                    idsToAdd.addAll(coexpressionsForThisId);
                    prescribedOrder.addAll(coexpressionsForThisId);
                }
            }
            if (alsoIncludeOriginalGeneQueryResponse) {
                newGeneQuery.addGeneIds(queryTerm, new HashSet<>(idsForThatTerm));
            }
            if (idsToAdd.size() > 0) {
                newGeneQuery.addGeneIds(queryTerm + ":coexpressions", idsToAdd);
            }
        }

        return Pair.of(newGeneQuery,prescribedOrder);
    }

    List<String> fetchCoexpressions(String experimentAccession, String identifier, Integer requestedAmount) {
        List<String> coexpressedGenes = coexpressedGenesDao.coexpressedGenesFor(experimentAccession, identifier);
        return coexpressedGenes.subList(0, Math.min(Math.max(0, requestedAmount), coexpressedGenes.size()));
    }

    public GeneQueryResponse extendGeneQueryResponseWithCoexpressions(BaselineExperiment experiment,
                                                                      GeneQueryResponse geneQueryResponse,
                                                                      Map<String, Integer> coexpressionsRequested) {
        return extendGeneQueryResponseWithCoexpressions(geneQueryResponse,
                experiment,coexpressionsRequested,true).getLeft();
    }


    public Optional<Pair<GeneQueryResponse, List<String>>> tryGetRelatedCoexpressions(BaselineExperiment experiment,
                                                                                      GeneQueryResponse geneQueryResponse,
                                                                                      Map<String, Integer> coexpressionsRequested) {
        Pair<GeneQueryResponse, List<String>> result =
                extendGeneQueryResponseWithCoexpressions(geneQueryResponse, experiment, coexpressionsRequested, false);
        return result.getLeft().getAllGeneIds().isEmpty()
                ? Optional.<Pair<GeneQueryResponse, List<String>>>absent()
                : Optional.of(result);
    }
}
