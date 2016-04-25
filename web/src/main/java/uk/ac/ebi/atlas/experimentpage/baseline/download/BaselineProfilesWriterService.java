package uk.ac.ebi.atlas.experimentpage.baseline.download;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.io.Writer;
import java.util.Map;

public class BaselineProfilesWriterService {

    private BaselineProfilesWriter baselineProfilesWriter;

    private CoexpressedGenesService coexpressedGenesService;

    public BaselineProfilesWriterService(BaselineProfilesWriter baselineProfilesWriter, CoexpressedGenesService coexpressedGenesService){
        this.baselineProfilesWriter = baselineProfilesWriter;
        this.coexpressedGenesService = coexpressedGenesService;
    }

    public long write(Writer writer, BaselineRequestPreferences preferences, BaselineExperiment experiment,
                     Map<String, Integer> coexpressionsRequested) throws GenesNotFoundException {
        if(coexpressionsRequested.isEmpty()){
            return baselineProfilesWriter.write(writer, BaselineRequestContext.createFor(experiment,preferences));
        } else {
            GeneQuery originalGeneQuery = preferences.getGeneQuery();

            preferences.setGeneQuery(coexpressedGenesService.extendGeneQueryWithCoexpressions(experiment,
                    originalGeneQuery,coexpressionsRequested));
            long count = baselineProfilesWriter.write(writer, BaselineRequestContext.createFor(experiment,preferences));
            preferences.setGeneQuery(originalGeneQuery);

            return count;
        }
    }
}
