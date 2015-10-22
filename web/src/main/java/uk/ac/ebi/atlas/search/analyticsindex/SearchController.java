package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.*;
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
import uk.ac.ebi.atlas.bioentity.go.GoTermTrader;
import uk.ac.ebi.atlas.bioentity.go.PoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTermTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.thirdpartyintegration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.utils.ReactomeClient;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import java.io.IOException;
import java.util.SortedSet;

import static org.apache.commons.lang3.text.WordUtils.capitalize;

public abstract class SearchController {

    private final EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder;

    private final AnalyticsSearchDAO analyticsSearchDAO;

    protected static final String BIOENTITY_PROPERTY_NAME = "symbol";

    protected SpeciesLookupService speciesLookupService;
    private SpeciesLookupService.Result speciesResult;

    protected BioEntityPropertyDao bioEntityPropertyDao;

    private BioEntityPropertyService bioEntityPropertyService;

    private  ReactomeClient reactomeClient;

    private  GoTermTrader goTermTrader;

    private  PoTermTrader poTermTrader;

    private  InterProTermTrader interProTermTrader;

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
    public void setReactomeClient(ReactomeClient reactomeClient) {
        this.reactomeClient = reactomeClient;
    }

    @Inject
    public void setGoTermTrader(GoTermTrader goTermTrader) {
        this.goTermTrader = goTermTrader;
    }

    @Inject
    public void setPoTermTrader(PoTermTrader poTermTrader) {
        this.poTermTrader = poTermTrader;
    }

    @Inject
    public void setInterProTermTrader(InterProTermTrader interProTermTrader) {
        this.interProTermTrader = interProTermTrader;
    }

    private String[] bioentityPropertyNames;

    @Value("#{configuration['index.property_names.genepage']}")
    void setBioentityPropertyNames(String[] bioentityPropertyNames) {
        this.bioentityPropertyNames = bioentityPropertyNames;
    }

    public SearchController(EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder, AnalyticsSearchDAO analyticsSearchDAO) {
        this.ebiGlobalSearchQueryBuilder = ebiGlobalSearchQueryBuilder;
        this.analyticsSearchDAO = analyticsSearchDAO;
    }

    public void addSearchHeader(GeneQuerySearchRequestParameters requestParameters, Model model) throws IOException, SolrServerException {
        GeneQuery geneQuery = requestParameters.getGeneQuery();
        ImmutableSet<String> experimentTypes = analyticsSearchDAO.fetchExperimentTypes(geneQuery);

        boolean hasBioEntities = bioEntityPropertyDao.hasBioentityProperties(geneQuery.description(), getPagePropertyTypes());

        model.addAttribute("hasBaselineResults", ExperimentType.containsBaseline(experimentTypes));
        model.addAttribute("hasDifferentialResults", ExperimentType.containsDifferential(experimentTypes));

        model.addAttribute("searchDescription", requestParameters.getDescription());
        model.addAttribute("geneQuery", geneQuery);

        String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQuery.asString(), requestParameters.getConditionQuery());
        model.addAttribute("globalSearchTerm", globalSearchTerm);

        initBioentityIdentifierHeader(geneQuery.description(), model, hasBioEntities);
    }

    public void initBioentityIdentifierHeader(String identifier, Model model, boolean hasBioEntities) throws IOException, SolrServerException {
        String species = null, entityName = null;
        SortedSet<String> entityNames = Sets.newTreeSet();
        SortedSetMultimap<String, String> propertyValuesByType = TreeMultimap.create();

        boolean hasGeneInformationTab = true;

        if(!GeneSetUtil.isGeneSet(identifier) && hasBioEntities) {

            species = speciesLookupService.fetchSpeciesForBioentityId(identifier);
            propertyValuesByType = bioEntityPropertyDao.fetchGenePageProperties(identifier, getPagePropertyTypes());
            entityNames = propertyValuesByType.get(getBioentityPropertyName());

            if (entityNames.isEmpty()) {
                entityNames.add(identifier);
                entityName = identifier;
            } else {
                entityName = entityNames.first();
            }

        } else if (GeneSetUtil.isGeneSet(identifier)) {

            speciesResult = speciesLookupService.fetchSpeciesForGeneSet(identifier);
            species = speciesResult.firstSpecies();

            identifier = identifier.toUpperCase();
            if (GeneSetUtil.isReactome(identifier)) {
                propertyValuesByType.put("reactome", identifier);
                propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, reactomeClient.fetchPathwayNameFailSafe(identifier));
            } else if (GeneSetUtil.isGeneOntology(identifier)) {
                String termName = goTermTrader.getTermName(identifier);
                propertyValuesByType.put("go", identifier);
                propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
            } else if (GeneSetUtil.isPlantOntology(identifier)) {
                String termName = poTermTrader.getTermName(identifier);
                propertyValuesByType.put("po", identifier);
                propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
            } else if (GeneSetUtil.isInterPro(identifier)) {
                String term = interProTermTrader.getTerm(identifier);
                propertyValuesByType.put("interpro", identifier);
                propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, term);
            } else if (GeneSetUtil.isPlantReactome(identifier)) {
                propertyValuesByType.put("plant_reactome", identifier);
            }
            entityName = identifier;
            entityNames.add(identifier);

        } else {
            model.addAttribute("identifierDescription", identifier.toUpperCase());
            hasGeneInformationTab = false;
        }

        model.addAttribute("hasGeneInformation", hasGeneInformationTab);
        model.addAttribute("noTabLine", hasGeneInformationTab);
        model.addAttribute("mainTitle", "Expression summary for " + (hasGeneInformationTab ? entityName : identifier) + " - " + capitalize(species));
        if(hasGeneInformationTab) {
            bioEntityPropertyService.init(species, propertyValuesByType, entityName, identifier);
        }
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
