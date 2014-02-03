package uk.ac.ebi.atlas.commands;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.streams.baseline.RankBaselineProfilesPipeline;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;

@Named
@Scope("prototype")
public class HeatMapBaselineProfiles {

    private static final Logger LOGGER = Logger.getLogger(HeatMapBaselineProfiles.class);

    private SolrQueryService solrQueryService;
    private BaselineProfileInputStreamFactory baselineProfileInputStreamFactory;
    private RankBaselineProfilesPipeline rankBaselineProfilesPipeline;

    @Inject
    public HeatMapBaselineProfiles(SolrQueryService solrQueryService,
                                   BaselineProfileInputStreamFactory baselineProfileInputStreamFactory,
                                   RankBaselineProfilesPipeline rankBaselineProfilesPipeline) {
        this.solrQueryService = solrQueryService;
        this.baselineProfileInputStreamFactory = baselineProfileInputStreamFactory;
        this.rankBaselineProfilesPipeline = rankBaselineProfilesPipeline;
    }

    public BaselineProfilesList fetch(BaselineRequestContext requestContext) throws GenesNotFoundException {
        BaselineExperiment experiment = requestContext.getExperiment();
        String experimentAccession = experiment.getAccession();

        String queryFactorType = requestContext.getQueryFactorType();
        Set<Factor> filterFactors = requestContext.getSelectedFilterFactors();
        double cutOff = requestContext.getCutoff();
        Set<Factor> allQueryFactors = requestContext.getAllQueryFactors();
        boolean isSpecific = requestContext.isSpecific();
        Set<Factor> queryFactors = requestContext.getSelectedQueryFactors();
        String geneQuery = requestContext.getGeneQuery();
        int heatmapMatrixSize = requestContext.getHeatmapMatrixSize();

        if (!StringUtils.isBlank(geneQuery)) {
            String filteredBySpecies = requestContext.getFilteredBySpecies();

            GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(geneQuery,
                    requestContext.isExactMatch(),
                    filteredBySpecies,
                    requestContext.isGeneSetMatch());

            if (geneQueryResponse.isEmpty()) {
                throw new GenesNotFoundException("No genes found for searchText = " + geneQuery + ", species = " + filteredBySpecies);
            }

            rankBaselineProfilesPipeline.selectGeneIDs(geneQueryResponse.getAllGeneIds());

            if (requestContext.isGeneSetMatch()) {
                rankBaselineProfilesPipeline.averageIntoGeneSets(geneQueryResponse.getQueryTermsToIds());
            }

        }

        try (ObjectInputStream<BaselineProfile> baselineProfileInputStream =
                     baselineProfileInputStreamFactory.createBaselineProfileInputStream(experimentAccession, queryFactorType, cutOff, filterFactors)) {

            return rankBaselineProfilesPipeline.inputStream(baselineProfileInputStream)
                    .cutOff(cutOff)
                    .isSpecific(isSpecific)
                    .queryFactors(queryFactors)
                    .allQueryFactors(allQueryFactors)
                    .top(heatmapMatrixSize)
                    .fetch();

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }


    }
}
