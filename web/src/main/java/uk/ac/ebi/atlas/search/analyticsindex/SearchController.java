package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SortedSetMultimap;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.bioentity.go.GoPoTerm;
import uk.ac.ebi.atlas.bioentity.go.GoTermTrader;
import uk.ac.ebi.atlas.bioentity.go.PoTermTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.thirdpartyintegration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;

public abstract class SearchController {

    private final EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder;

    private final AnalyticsSearchDao analyticsSearchDao;

    public static final String PROPERTY_TYPE_DESCRIPTION = "description";

    protected static final String BIOENTITY_PROPERTY_NAME = "symbol";

    protected AnalyticsSpeciesLookupService speciesLookupService;

    protected BioEntityPropertyDao bioEntityPropertyDao;
//    protected AnalyticsBioentityPropertyDao bioEntityPropertyDao;

    private BioEntityPropertyService bioEntityPropertyService;

    @Inject
    public void setSpeciesLookupService(AnalyticsSpeciesLookupService speciesLookupService) {
        this.speciesLookupService = speciesLookupService;
    }

//    @Inject
//    public void setBioEntityPropertyDao(AnalyticsBioentityPropertyDao bioEntityPropertyDao) {
//        this.bioEntityPropertyDao = bioEntityPropertyDao;
//    }
    //TODO: After updating solr analytics with the new bioentity types - value and name - replace this for the previous one
    @Inject
    public void setBioEntityPropertyDao(BioEntityPropertyDao bioEntityPropertyDao) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
    }

    @Inject
    public void setBioEntityPropertyService(BioEntityPropertyService bioEntityPropertyService) {
        this.bioEntityPropertyService = bioEntityPropertyService;
    }

    private String[] bioentityPropertyNames;

    @Value("#{configuration['index.property_names.genepage']}")
    void setBioentityPropertyNames(String[] bioentityPropertyNames) {
        this.bioentityPropertyNames = bioentityPropertyNames;
    }

    public SearchController(EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder, AnalyticsSearchDao analyticsSearchDao) {
        this.ebiGlobalSearchQueryBuilder = ebiGlobalSearchQueryBuilder;
        this.analyticsSearchDao = analyticsSearchDao;
    }

    public void addSearchHeader(GeneQuerySearchRequestParameters requestParameters, Model model) throws IOException, SolrServerException {
        GeneQuery geneQuery = requestParameters.getGeneQuery();
        ImmutableSet<String> experimentTypes = analyticsSearchDao.fetchExperimentTypes(geneQuery);

        boolean hasBioEntities = bioEntityPropertyDao.hasBioentityProperties(geneQuery.description(), getPagePropertyTypes());

        model.addAttribute("hasBaselineResults", ExperimentType.containsBaseline(experimentTypes));
        model.addAttribute("hasDifferentialResults", ExperimentType.containsDifferential(experimentTypes));
        model.addAttribute("hasGeneInformation", hasBioEntities);

        model.addAttribute("searchDescription", requestParameters.getDescription());
        model.addAttribute("geneQuery", geneQuery);

        String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQuery.asString(), requestParameters.getConditionQuery());
        model.addAttribute("globalSearchTerm", globalSearchTerm);

        if(hasBioEntities) {
            initBioentityIdentifierHeader(geneQuery.description(), model);
        }
    }

    public void initBioentityIdentifierHeader(String identifier, Model model) throws IOException, SolrServerException {
        String species = speciesLookupService.fetchSpeciesForBioentityId(identifier);

        SortedSetMultimap<String, String> propertyValuesByType = bioEntityPropertyDao.fetchGenePageProperties(identifier, getPagePropertyTypes());
        SortedSet<String> entityNames = propertyValuesByType.get(getBioentityPropertyName());
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }

        model.addAttribute("mainTitle", "Expression summary for " + entityNames.first() + " - " + org.apache.commons.lang.StringUtils.capitalize(species));

        bioEntityPropertyService.init(species, propertyValuesByType, entityNames, identifier);
    }

    public String[] getPagePropertyTypes() {
        return bioentityPropertyNames;
    }

    public String getBioentityPropertyName() {
        return BIOENTITY_PROPERTY_NAME;
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("search-error");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

}
