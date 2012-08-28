package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.services.ExpressionLevelsInputStream;
import uk.ac.ebi.atlas.services.MageTabInvestigation;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

@Named("rankExpressionLevels")
public class RankExpressionLevels implements Function<String,  List<ExpressionLevel>> {

    private static final Logger logger = Logger.getLogger(RankExpressionLevels.class);

    private int rankingSize = 10;

    @Value("#{webappProperties['magetab.idf.url.template']}")
    private String idfFileUrlTemplate;

    @Value("#{webappProperties['magetab.test.datafile.url']}")
    private String dataFileURL;

    @Override
    public List<ExpressionLevel> apply(String experimentAccession) throws IllegalStateException{
        List<ExpressionLevel> topTenExpressionLevels = loadTopTenExpressionLevels(experimentAccession);
        if (topTenExpressionLevels == null){
            throw new IllegalStateException("Data not found for experiment: " + experimentAccession);
        }
        return topTenExpressionLevels;
    }


    private List<ExpressionLevel> loadTopTenExpressionLevels(String experimentAccession) {


        String idfFileLocation = String.format(idfFileUrlTemplate,experimentAccession, experimentAccession);

        URL mageTabURL = buildURL(idfFileLocation);

        List<ExperimentRun> experimentRuns =  Lists.newArrayList(MageTabInvestigation.parse(mageTabURL).extractExperimentRuns());


        URL dataFileURL = buildURL(this.dataFileURL);

        try{

            Reader dataFileReader = new InputStreamReader(dataFileURL.openStream());

            ExpressionLevelsInputStream expressionLevelStream = new ExpressionLevelsInputStream(dataFileReader, experimentRuns);

            List<ExpressionLevel> expressionLevelsRanking = rankExpressionLevels(expressionLevelStream);

            return expressionLevelsRanking;

        }catch(IOException e){
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while parsing dataFileURL stream: " + e.getMessage());
        }
    }


    protected List<ExpressionLevel> rankExpressionLevels(ExpressionLevelsInputStream expressionLevelReader) {
        RankStreamingObjects<ExpressionLevel> rankStreamingObjectsCommand = new RankStreamingObjects<ExpressionLevel>(rankingSize);
        return rankStreamingObjectsCommand.apply(expressionLevelReader);
    }

    public RankExpressionLevels setRankingSize(int rankingSize){
        this.rankingSize = rankingSize;
        return this;
    }

    private URL buildURL(String location){
        try{
            return new URL(location);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error while building URL for location " + location + ". Error details: " + e.getMessage());
        }

    }

    public RankExpressionLevels setDataFileURL(String dataFileURL) {
        this.dataFileURL = dataFileURL;
        return this;
    }
}
