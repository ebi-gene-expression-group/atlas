package uk.ac.ebi.atlas.experimentpage.baseline.download;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.io.Writer;
import java.util.List;
import java.util.Map;

public class BaselineProfilesWriterService {

    private BaselineProfilesWriter baselineProfilesWriter;

    private CoexpressedGenesDao coexpressedGenesDao;

    public BaselineProfilesWriterService(BaselineProfilesWriter baselineProfilesWriter, CoexpressedGenesDao
            coexpressedGenesDao){
        this.baselineProfilesWriter = baselineProfilesWriter;
        this.coexpressedGenesDao = coexpressedGenesDao;
    }

    public long write(Writer writer, BaselineRequestPreferences preferences, BaselineExperiment experiment,
                     Map<String, Integer> coexpressionsRequested) throws GenesNotFoundException {
        if(coexpressionsRequested.isEmpty()){
            return baselineProfilesWriter.write(writer, BaselineRequestContext.createFor(experiment,preferences));
        } else {
            GeneQuery originalGeneQuery = preferences.getGeneQuery();

            preferences.setGeneQuery(extendGeneQueryWithCoexpressions(experiment,originalGeneQuery,coexpressionsRequested));
            long count = baselineProfilesWriter.write(writer, BaselineRequestContext.createFor(experiment,preferences));
            preferences.setGeneQuery(originalGeneQuery);

            return count;
        }
    }

    GeneQuery extendGeneQueryWithCoexpressions(BaselineExperiment experiment, GeneQuery geneQuery, Map<String,
            Integer> coexpressionsRequested){
        GeneQuery result = geneQuery;
        for(String t: geneQuery.terms()){
            if(coexpressionsRequested.containsKey(t)) {
                List<String> l = coexpressedGenesDao.coexpressedGenesFor(experiment.getAccession(), t);
                l = l.subList(0, Math.min(Math.max(0,coexpressionsRequested.get(t)), l.size()));
                result = result.extend(t,l.toArray(new String[l.size()]));
            }
        }
        return result;
    }

}
