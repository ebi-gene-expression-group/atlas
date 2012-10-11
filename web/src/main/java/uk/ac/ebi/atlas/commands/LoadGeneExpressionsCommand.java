package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.GeneExpression;
import uk.ac.ebi.atlas.model.GeneExpressionsList;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;
import uk.ac.ebi.atlas.web.controllers.RequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Named("loadGeneExpressions")
@Scope("prototype")
public class LoadGeneExpressionsCommand implements Function<String, List<GeneExpression>> {

    private static final Logger logger = Logger.getLogger(LoadGeneExpressionsCommand.class);

    @Value("#{configuration['magetab.test.datafile.url']}")
    private String dataFileURL;

    private LoadingCache<String, List<ExperimentRun>> experiments;
    private RankBySpecificityAndExpressionLevelCommand rankBySpecificityObjectsCommand;
    private Double cutoff;

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

    //This is a bit smelly, this cache could be directly used by GeneProfilesInputStrea.Builder
    //rather then being used here just to bounce the cached data back to GeneProfilesInputStrea.Builder
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

        try (GeneProfilesInputStream objectInputStream = GeneProfilesInputStream.forFile(dataFileURL)
                .withExperimentRuns(experimentRuns)
                .withCutoff(cutoff).create()) {

            return rankBySpecificityObjectsCommand.apply(objectInputStream);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }
    }

    public LoadGeneExpressionsCommand setPreferences(RequestPreferences preferences) {
        setRankingSize(preferences.getRankingSize());
        setCutoff(preferences.getCutoff());
        setOrganismParts(preferences.getOrganismParts());
        setGeneIds(preferences.getGeneIDs());

        return this;
    }

    public LoadGeneExpressionsCommand setRankingSize(int rankingSize) {
        rankBySpecificityObjectsCommand.setRankingSize(rankingSize);
        return this;
    }

    public LoadGeneExpressionsCommand setCutoff(double cutoff) {
        this.cutoff = cutoff;
        return this;
    }

    //ToDo: refactor to set these parameters directly to command
    public LoadGeneExpressionsCommand setOrganismParts(Set<String> organismParts) {
        rankBySpecificityObjectsCommand.setOrganismParts(organismParts);
        return this;
    }

    public LoadGeneExpressionsCommand setGeneIds(Set<String> geneIds) {
        rankBySpecificityObjectsCommand.setGeneIDs(geneIds);
        return this;
    }
}
