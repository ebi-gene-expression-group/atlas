package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Ordering;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneExpression;
import uk.ac.ebi.atlas.model.GeneExpressionsList;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.GeneSpecificityComparator;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamFilter;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;
import uk.ac.ebi.atlas.web.controllers.RequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

@Named("rankBySpecificityAndExpressionLevel")
@Scope("prototype")
public class RankBySpecificityAndExpressionLevelCommand implements Function<String, GeneExpressionsList> {

    private static final Logger logger = Logger.getLogger(RankBySpecificityAndExpressionLevelCommand.class);

    private String dataFileURL;

    private RequestPreferences requestPreferences;

    GeneProfilesInputStream.Builder geneProfileInputStreamBuilder;

    @Inject
    public RankBySpecificityAndExpressionLevelCommand(GeneProfilesInputStream.Builder geneProfileInputStreamBuilder, @Value("#{configuration['magetab.test.datafile.url']}") String dataFileURL) {
        this.geneProfileInputStreamBuilder = geneProfileInputStreamBuilder;
        this.dataFileURL = dataFileURL;
    }

    @Override
    public GeneExpressionsList apply(String experimentAccession) {

        Comparator<GeneExpression> reverseSpecificityComparator = buildReverseSpecificityComparator();

        Queue<GeneExpression> rankingQueue = buildRankingQueue(reverseSpecificityComparator);


        try (ObjectInputStream<GeneProfile> inputStream = buildGeneProfilesInputStream(experimentAccession)) {

            GeneProfile geneProfile;

            int geneCount = 0;

            while ((geneProfile = inputStream.readNext()) != null) {
                boolean expressionAdded = false;
                for (GeneExpression geneExpression : geneProfile.filterByOrganismParts(requestPreferences.getOrganismParts())) {
                    rankingQueue.add(geneExpression);
                    expressionAdded = true;
                }
                if (expressionAdded) {
                    geneCount++;
                }
            }

            GeneExpressionsList list = new GeneExpressionsList(rankingQueue);

            Collections.sort(list, reverseSpecificityComparator);

            list.setTotalResultCount(geneCount);

            return list;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }

    }

    protected ObjectInputStream<GeneProfile> buildGeneProfilesInputStream(String experimentAccession) {

        ObjectInputStream<GeneProfile> geneProfileInputStream = geneProfileInputStreamBuilder.forDataFileURL(dataFileURL)
            .withExperimentAccession(experimentAccession)
            .withCutoff(requestPreferences.getCutoff()).create();

        if (CollectionUtils.isEmpty(requestPreferences.getGeneIDs())) {

            return geneProfileInputStream;

        }

        return new GeneProfileInputStreamFilter(geneProfileInputStream, requestPreferences.getGeneIDs());

    }

    protected Ordering<GeneExpression> buildReverseSpecificityComparator() {
        return Ordering.from(new GeneSpecificityComparator()).reverse();
    }

    protected Queue<GeneExpression> buildRankingQueue(Comparator<GeneExpression> reverseSpecificityComparator) {
        return MinMaxPriorityQueue.orderedBy(reverseSpecificityComparator).maximumSize(requestPreferences.getHeatmapMatrixSize()).create();
    }

    public void setRequestPreferences(RequestPreferences requestPreferences) {
        this.requestPreferences = requestPreferences;
    }


}