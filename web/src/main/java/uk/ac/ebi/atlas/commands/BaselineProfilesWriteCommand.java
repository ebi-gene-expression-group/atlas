package uk.ac.ebi.atlas.commands;

import com.google.common.collect.ImmutableSetMultimap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.download.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.streams.baseline.BaselineProfilesPipelineBuilder;
import uk.ac.ebi.atlas.streams.IterableObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;


@Named
@Scope("request")
public class BaselineProfilesWriteCommand {

    private static final Logger LOGGER = Logger.getLogger(BaselineProfilesWriteCommand.class);

    private SolrQueryService solrQueryService;
    private BaselineProfilesTSVWriter baselineProfilesTSVWriter;
    private BaselineProfileInputStreamFactory baselineProfileInputStreamFactory;
    private BaselineProfilesPipelineBuilder baselineProfilesPipelineBuilder;

    @Inject
    public BaselineProfilesWriteCommand(SolrQueryService solrQueryService, BaselineProfilesTSVWriter baselineProfilesTSVWriter,
                                        BaselineProfileInputStreamFactory baselineProfileInputStreamFactory,
                                        BaselineProfilesPipelineBuilder baselineProfilesPipelineBuilder) {
        this.solrQueryService = solrQueryService;
        this.baselineProfilesTSVWriter = baselineProfilesTSVWriter;
        this.baselineProfileInputStreamFactory = baselineProfileInputStreamFactory;
        this.baselineProfilesPipelineBuilder = baselineProfilesPipelineBuilder;

    }

    public Long write(BaselineRequestContext requestContext) throws GenesNotFoundException {

        BaselineExperiment experiment = requestContext.getExperiment();
        String experimentAccession = experiment.getAccession();

        String queryFactorType = requestContext.getQueryFactorType();
        Set<Factor> filterFactors = requestContext.getSelectedFilterFactors();
        double cutOff = requestContext.getCutoff();
        Set<Factor> allQueryFactors = requestContext.getAllQueryFactors();
        boolean isSpecific = requestContext.isSpecific();
        Set<Factor> queryFactors = requestContext.getSelectedQueryFactors();
        String geneQuery = requestContext.getGeneQuery();
        Set<String> uppercaseGeneIDs = Collections.emptySet();
        ImmutableSetMultimap<String, String> geneSetIdsToGeneIds = ImmutableSetMultimap.of();


        if (!StringUtils.isBlank(geneQuery)) {
            String filteredBySpecies = requestContext.getFilteredBySpecies();

            GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(geneQuery,
                    requestContext.isExactMatch(),
                    filteredBySpecies,
                    requestContext.isGeneSetMatch());

            if (geneQueryResponse.isEmpty()) {
                throw new GenesNotFoundException("No genes found for searchText = " + geneQuery + ", species = " + filteredBySpecies);
            }

            uppercaseGeneIDs = geneQueryResponse.getAllGeneIds();

            baselineProfilesPipelineBuilder.selectGeneIDs(uppercaseGeneIDs);


            if (requestContext.isGeneSetMatch()) {
                geneSetIdsToGeneIds = geneQueryResponse.getQueryTermsToIds();
                baselineProfilesPipelineBuilder.averageIntoGeneSets(geneSetIdsToGeneIds);
            }

        }

        try (ObjectInputStream<BaselineProfile> inputStream =
                     baselineProfileInputStreamFactory.createBaselineProfileInputStream(experimentAccession, queryFactorType, cutOff, filterFactors)) {

            Iterable<BaselineProfile> profiles = new IterableObjectInputStream<>(inputStream);

            Iterable<BaselineProfile> mapProfiles = baselineProfilesPipelineBuilder.profiles(profiles).
                    isSpecific(isSpecific).
                    selectGeneIDs(uppercaseGeneIDs).
                    queryFactors(queryFactors).
                    allQueryFactors(allQueryFactors).
                    averageIntoGeneSets(geneSetIdsToGeneIds).
                    build();


            return baselineProfilesTSVWriter.write(mapProfiles, allQueryFactors);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

    public void setResponseWriter(PrintWriter responseWriter) {
        baselineProfilesTSVWriter.setResponseWriter(responseWriter);
    }


}
