package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RequestContext;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.ac.ebi.atlas.solr.BioentityType.GENE;

@Named
@Scope("singleton")
//can be singleton because HttpSolrClient is documented to be thread safe, please be careful not to add any other non thread safe state!
public class SolrQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrQueryService.class);

    public static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    public static final String BIOENTITY_TYPE_FIELD = "bioentity_type";
    public static final String PROPERTY_NAME_FIELD = "property_name";

    public static final String PROPERTY_EDGENGRAM_FIELD = "property_value_edgengram";

    private static final String BIOENTITY_TYPE_QUERY =
            "(property_name:\"ensgene\" OR property_name:\"mirna\" OR property_name:\"ensprotein\" OR property_name:\"enstranscript\") " +
                    "AND property_value_lower:\"{0}\"";

    private static final int PROPERTY_VALUES_LIMIT = 1000;

    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    private GxaSolrClient solrServer;

    private SolrQueryBuilderFactory solrQueryBuilderFactory;
    private BioEntityPropertyDao bioEntityPropertyDao;

    @Inject
    public SolrQueryService(BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer,
                            GxaSolrClient solrServer,
                            SolrQueryBuilderFactory solrQueryBuilderFactory, BioEntityPropertyDao bioEntityPropertyDao) {
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
        this.solrServer = solrServer;
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
        this.bioEntityPropertyDao = bioEntityPropertyDao;
    }

    public BioentityProperty findBioentityIdentifierProperty(String bioentityId) {
        String _bioentityId = bioentityId.replace(":", "\\:").replace("[", "\\[").replace("]", "\\]");

        String query = MessageFormat.format(BIOENTITY_TYPE_QUERY, _bioentityId);
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setRows(PROPERTY_VALUES_LIMIT);
        QueryResponse response = solrServer.query(solrQuery);
        List<BioentityProperty> bioentityProperties = response.getBeans(BioentityProperty.class);
        if (!bioentityProperties.isEmpty()) {

            for (BioentityProperty bioentityProperty : bioentityProperties) {
                String bioentityIdentifier = bioentityProperty.getBioentityIdentifier();
                String propertyValue = bioentityProperty.getValue();
                if (bioentityIdentifier.equals(propertyValue)) {
                    return bioentityProperty;
                }
            }
        }
        return null;
    }


    public Set<String> fetchGeneIdentifiersFromSolr(String queryString, String bioentityType, boolean toUppercase, String... propertyNames) {
        //eg: property_value_lower:"hsa-mir-636" AND (bioentity_type:"ensgene") AND (property_name:"mirbase_id")
        SolrQuery solrQuery = solrQueryBuilderFactory.createGeneBioentityIdentifierQueryBuilder()
                .forQueryString(queryString, false)
                .withBioentityTypes(Sets.newHashSet(bioentityType))
                .withPropertyNames(propertyNames).build();

        return solrServer.query(solrQuery, toUppercase, BIOENTITY_IDENTIFIER_FIELD);
    }

    public Set<String> findMatureRNAIds(Set<String> geneIdentifiers) {
        Set<String> expandedIdentifiers = Sets.newHashSet();

        for (String geneIdentifier : geneIdentifiers) {
            Set<String> mirbaseIds = bioEntityPropertyDao.fetchPropertyValuesForGeneId(geneIdentifier, "mirbase_id");
            String mirbaseId = mirbaseIds.size() > 0 ? mirbaseIds.iterator().next() : null;
            Set<String> matureRNAIds = fetchGeneIdentifiersFromSolr((mirbaseId != null) ? mirbaseId : geneIdentifier, "mirna", false, "hairpin_id");
            if (matureRNAIds.size() > 0) {
                expandedIdentifiers.addAll(matureRNAIds);
            } else if (mirbaseId != null) {
                expandedIdentifiers.add(mirbaseId);
            }

        }
        return expandedIdentifiers;
    }

    public GeneQueryResponse fetchGeneIdsOrSetsGroupedByGeneQueryToken(String geneQuery, boolean exactMatch, String species) {

        checkArgument(StringUtils.isNotBlank(geneQuery), "Please specify a gene query");

        species = Species.convertToEnsemblSpecies(species);

        GeneQueryResponse geneQueryResponse = new GeneQueryResponse();

        //associate gene ids with each token in the query string
        for (String queryToken : bioentityPropertyValueTokenizer.split(geneQuery)) {
            Set<String> geneIds = fetchGeneIds(queryToken, exactMatch, species);
            geneQueryResponse.addGeneIds(queryToken, geneIds);
        }
        return geneQueryResponse;

    }

    /**
     *
     * @param species empty string will search across all species, and return orthologs
     * @return Optional.absent if geneQuery is blank, empty Set if no genes found, otherwise Set of geneids found
     */
    public Optional<Set<String>> expandGeneQueryIntoGeneIds(String geneQuery, String species, boolean isExactMatch) {
        if (StringUtils.isBlank(geneQuery)) {
            return Optional.absent();
        }

        LOGGER.info(String.format("<expandGeneQueryIntoGeneIds> geneQuery=%s", geneQuery));

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        //resolve any gene keywords to identifiers
        Set<String> geneIds = findGeneIdsOrSets(geneQuery, isExactMatch, species);

        Set<String> matureRNAIds = findMatureRNAIds(Sets.newHashSet(bioentityPropertyValueTokenizer.split(geneQuery)));
        geneIds.addAll(matureRNAIds);

        stopWatch.stop();
        LOGGER.info(String.format("<expandGeneQueryIntoGeneIds> %s results, took %s seconds", geneIds.size(), stopWatch.getTotalTimeSeconds()));

        return Optional.of(geneIds);
    }


    // NB: if species = "" then will search across all species
    Set<String> findGeneIdsOrSets(String geneQuery, boolean exactMatch, String species) {

        checkArgument(StringUtils.isNotBlank(geneQuery), "Please specify a gene query");

        species = Species.convertToEnsemblSpecies(species);

        return fetchGeneIds(geneQuery, exactMatch, species);
    }

    public Set<String> fetchGeneIds(String geneQuery, boolean exactMatch, String species) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        //eg: {!lucene q.op=OR df=property_value_lower}(property_value_lower:Q9NHV9) AND (bioentity_type:"mirna" OR bioentity_type:"ensgene")
        // fl=bioentity_identifier&group=true&group.field=bioentity_identifier&group.main=true
        SolrQuery solrQuery = solrQueryBuilderFactory.createGeneBioentityIdentifierQueryBuilder()
                .forQueryString(geneQuery, true).withExactMatch(exactMatch)
                .withSpecies(species).withBioentityTypes(GENE.getSolrAliases()).build();

        Set<String> geneIds = solrServer.query(solrQuery, false, BIOENTITY_IDENTIFIER_FIELD);

        stopwatch.stop();
        LOGGER.debug(String.format("Fetched gene ids for %s: returned %s results in %s secs", geneQuery, geneIds.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        return geneIds;
    }

    public GeneQueryResponse fetchResponseBasedOnRequestContext(RequestContext requestContext, String species) throws
            GenesNotFoundException {
        return fetchResponseBasedOnRequestContext(requestContext.getGeneQuery(),requestContext.isExactMatch(), species);
    }
    public GeneQueryResponse fetchResponseBasedOnRequestContext(String geneQuery, boolean isExactMatch, String
            species) throws
            GenesNotFoundException {

        if (StringUtils.isBlank(geneQuery)) {
            return new GeneQueryResponse();
        }

        GeneQueryResponse geneQueryResponse = fetchGeneIdsOrSetsGroupedByGeneQueryToken(geneQuery,
                isExactMatch,
                species);

        if (geneQueryResponse.isEmpty()) {
            throw new GenesNotFoundException("No genes found for searchText = " + geneQuery + ", species = " + species);
        }

        return geneQueryResponse;
    }


}