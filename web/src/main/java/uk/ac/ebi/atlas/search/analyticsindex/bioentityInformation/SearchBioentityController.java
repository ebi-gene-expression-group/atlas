package uk.ac.ebi.atlas.search.analyticsindex.bioentityInformation;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SortedSetMultimap;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.bioentity.go.GoPoTerm;
import uk.ac.ebi.atlas.bioentity.go.GoTermTrader;
import uk.ac.ebi.atlas.bioentity.go.PoTermTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchDAO;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public abstract class SearchBioentityController {

    protected static final String BIOENTITY_PROPERTY_NAME = "symbol";

    protected SpeciesLookupService speciesLookupService;

    protected BioEntityPropertyDao bioEntityPropertyDao;

    protected BioEntityPropertyService bioEntityPropertyService;

    protected GoTermTrader goTermTrader;

    protected PoTermTrader poTermTrader;

    private BioEntityCardProperties bioEntityCardProperties;

    @Inject
    public void setGoTermTrader(GoTermTrader goTermTrader) {
        this.goTermTrader = goTermTrader;
    }

    @Inject
    public void setPoTermTrader(PoTermTrader poTermTrader) {
        this.poTermTrader = poTermTrader;
    }

    @Inject
    public void setSpeciesLookupService(SpeciesLookupService speciesLookupService) {
        this.speciesLookupService = speciesLookupService;
    }

    @Inject
    public void setBioEntityPropertyDao(BioEntityPropertyDao bioEntityPropertyDao) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
    }

    @Inject
    public void setBioEntityPropertyService(BioEntityPropertyService bioEntityPropertyService) {
        this.bioEntityPropertyService = bioEntityPropertyService;
    }

    @Inject
    public void setBioEntityCardProperties(BioEntityCardProperties bioEntityCardProperties) {
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    private final AnalyticsSearchDAO analyticsSearchDAO;

    @Inject
    public SearchBioentityController(AnalyticsSearchDAO analyticsSearchDAO) {
        this.analyticsSearchDAO = analyticsSearchDAO;
    }

    public String showBioentityPage(GeneQuery geneQuery, Model model) throws IOException, SolrServerException {
        model.addAttribute("bioentity", true);

        initBioentityPropertyService(geneQuery.description(), model);

        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        addSearchHeader(geneQuery, model);

        return "search-gene-information";

    }

    protected void addSearchHeader(GeneQuery geneQuery, Model model) {

        ImmutableSet<String> experimentTypes = analyticsSearchDAO.fetchExperimentTypes(geneQuery);

        model.addAttribute("hasGeneInformation", true);
        model.addAttribute("hasBaselineResults", ExperimentType.containsBaseline(experimentTypes));
        model.addAttribute("hasDifferentialResults", ExperimentType.containsDifferential(experimentTypes));
    }

    private Map<String, String> buildPropertyNamesByTypeMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        for (String propertyType : getPagePropertyTypes()) {
            if (isDisplayedInPropertyList(propertyType)) {
                result.put(propertyType, bioEntityCardProperties.getPropertyName(propertyType));
            }
        }
        return result;
    }

    private boolean isDisplayedInPropertyList(String propertyType) {
        return !propertyType.equals(SearchGeneController.PROPERTY_TYPE_DESCRIPTION) && !propertyType.equals(BIOENTITY_PROPERTY_NAME);
    }

    public abstract String[] getPagePropertyTypes();

    public abstract String getBioentityPropertyName();

    protected void initBioentityPropertyService(String identifier, Model model) throws IOException, SolrServerException {
        String species = speciesLookupService.fetchSpeciesForBioentityId(identifier);

        SortedSetMultimap<String, String> propertyValuesByType = bioEntityPropertyDao.fetchGenePageProperties(identifier, getPagePropertyTypes());
        SortedSet<String> entityNames = propertyValuesByType.get(getBioentityPropertyName());
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }

        model.addAttribute("mainTitle", "Expression summary for " + entityNames.first() + " - " + org.apache.commons.lang.StringUtils.capitalize(species));

        ImmutableSetMultimap<Integer, GoPoTerm> goTerms = mapGoTermsByDepth(propertyValuesByType.get("go"));
        ImmutableSetMultimap<Integer, GoPoTerm> poTerms = mapPoTermsByDepth(propertyValuesByType.get("po"));

        bioEntityPropertyService.init(species, propertyValuesByType, goTerms, poTerms, entityNames, identifier);
    }


    protected ImmutableSetMultimap<Integer, GoPoTerm> mapGoTermsByDepth(Set<String> accessions) {
        ImmutableSetMultimap.Builder<Integer, GoPoTerm> builder = new ImmutableSetMultimap.Builder<>();

        for (String accession : accessions) {
            builder.put(goTermTrader.getDepth(accession), goTermTrader.getTerm(accession));
        }

        return builder.build();
    }


    protected ImmutableSetMultimap<Integer, GoPoTerm> mapPoTermsByDepth(Set<String> accessions) {
        ImmutableSetMultimap.Builder<Integer, GoPoTerm> builder = new ImmutableSetMultimap.Builder<>();

        for (String accession : accessions) {
            builder.put(poTermTrader.getDepth(accession), poTermTrader.getTerm(accession));
        }

        return builder.build();
    }
}
