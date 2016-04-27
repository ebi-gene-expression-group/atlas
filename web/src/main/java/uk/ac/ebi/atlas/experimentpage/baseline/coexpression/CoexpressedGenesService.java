package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.common.base.Optional;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.util.List;
import java.util.Map;

public class CoexpressedGenesService {

    final CoexpressedGenesDao coexpressedGenesDao;

    public CoexpressedGenesService(CoexpressedGenesDao coexpressedGenesDao){
        this.coexpressedGenesDao =coexpressedGenesDao;
    }

    public GeneQuery extendGeneQueryWithCoexpressions(BaselineExperiment experiment, GeneQuery geneQuery,
                                                          Map<String,Integer> coexpressionsRequested){
        GeneQuery result = geneQuery;
        for(String t: geneQuery.terms()){
            if(coexpressionsRequested.containsKey(t.toUpperCase())) {
                List<String> l = coexpressedGenesDao.coexpressedGenesFor(experiment.getAccession(), t);
                l = l.subList(0, Math.min(Math.max(0,coexpressionsRequested.get(t)), l.size()));
                result = result.extend(t,l.toArray(new String[l.size()]));
            }
        }
        return result;
    }

    public Optional<GeneQuery> tryGetRelatedCoexpressions(BaselineExperiment experiment, GeneQuery geneQuery,
                                                          Map<String,Integer> coexpressionsRequested){
        GeneQuery result = extendGeneQueryWithCoexpressions(experiment, geneQuery, coexpressionsRequested)
                            .subtract(geneQuery.terms());
        return result.isEmpty()
                ? Optional.<GeneQuery>absent()
                : Optional.of(result);
    }
}
