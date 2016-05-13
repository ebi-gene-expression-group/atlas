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
        int totalCoexpressionsRequested = 0;
        for(Map.Entry<String, Integer> e: coexpressionsRequested.entrySet()){
            totalCoexpressionsRequested+=e.getValue();
        }
        if(totalCoexpressionsRequested==0){
            return baselineProfilesWriter.write(writer, BaselineRequestContext.createFor(experiment,preferences));
        } else {
            GeneQuery originalGeneQuery = preferences.getGeneQuery();
            GeneQuery extendedGeneQuery = coexpressedGenesService.extendGeneQueryWithCoexpressions(experiment,
                    originalGeneQuery,coexpressionsRequested);

            preferences.setGeneQuery(extendedGeneQuery);
            long count = baselineProfilesWriter.write(writer, BaselineRequestContext
                    .createWithCustomGeneQueryDescription(experiment,preferences, originalGeneQuery.description()
                            +" with "+(extendedGeneQuery.size()-originalGeneQuery.size())+" similarly expressed " +
                            "genes"));
            preferences.setGeneQuery(originalGeneQuery);

            return count;
        }
    }
}
