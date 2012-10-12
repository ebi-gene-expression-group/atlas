package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.cache.LoadingCache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.GeneExpression;
import uk.ac.ebi.atlas.model.GeneExpressionsList;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;
import uk.ac.ebi.atlas.web.controllers.RequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Named("loadGeneExpressions")
@Scope("prototype")
public class LoadGeneExpressionsCommand implements Function<String, List<GeneExpression>> {

    private static final Logger logger = Logger.getLogger(LoadGeneExpressionsCommand.class);

    @Value("#{configuration['magetab.test.datafile.url']}")
    private String dataFileURL;

    private LoadingCache<String, List<ExperimentRun>> experiments;
    private RankBySpecificityAndExpressionLevelCommand rankBySpecificityObjectsCommand;
    private RequestPreferences requestPreferences;


    @Inject
    public LoadGeneExpressionsCommand(LoadingCache<String, List<ExperimentRun>> experiments, RankBySpecificityAndExpressionLevelCommand rankBySpecificityObjectsCommand) {

        this.experiments = experiments;

        this.rankBySpecificityObjectsCommand = rankBySpecificityObjectsCommand;

    }

    @Override
    public GeneExpressionsList apply(String experimentAccession) throws IllegalStateException {

        GeneExpressionsList topTenGeneExpressions = loadTopTenExpressions(experimentAccession);
        return topTenGeneExpressions;

    }

    //ToDo: should be refactored, this cache could be directly used by GeneProfilesInputStream.Builder.
    //The cached data is being extracted here with the only goal of bouncing it to GeneProfilesInputStrea.Builder.
    //Then why not having the Builder take it from the cache itself.
    //So the Builder should be injected via spring and cache should be injected into Builder.
    List<ExperimentRun> getExperimentRuns(String experimentAccession) {

        try {

            return experiments.get(experimentAccession);

        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading MAGE TAB file: " + e.getMessage(), e.getCause());
        }

    }


    GeneExpressionsList loadTopTenExpressions(String experimentAccession) {

        List<ExperimentRun> experimentRuns = getExperimentRuns(experimentAccession);

        ObjectInputStream<GeneProfile> geneProfileInputStream = null;

        try {

            geneProfileInputStream = GeneProfilesInputStream.forFile(dataFileURL)
                                .withExperimentRuns(experimentRuns)
                                .withCutoff(requestPreferences.getCutoff()).create();


            if (!CollectionUtils.isEmpty(requestPreferences.getGeneIDs())){

                geneProfileInputStream = new GeneProfileInputStreamFilter(geneProfileInputStream, requestPreferences.getGeneIDs());

            }

            rankBySpecificityObjectsCommand.setRankingSize(requestPreferences.getRankingSize());
            rankBySpecificityObjectsCommand.setOrganismParts(requestPreferences.getOrganismParts());

            return rankBySpecificityObjectsCommand.apply(geneProfileInputStream);

        } finally {

            try{
                geneProfileInputStream.close();
            }catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
            }
        }

    }

    public LoadGeneExpressionsCommand setRequestPreferences(RequestPreferences requestPreferences){
        this.requestPreferences = requestPreferences;
        return this;
    }


}
