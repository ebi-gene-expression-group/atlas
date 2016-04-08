/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.bioentity;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.bioentity.go.GoPoTerm;
import uk.ac.ebi.atlas.bioentity.go.GoTermTrader;
import uk.ac.ebi.atlas.bioentity.go.PoTermTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfilesHeatMap;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptionsWidgetQuery;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroup;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroupSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsList;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsSearchService;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.*;

import static uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroups.removeFirstAssayGroups;
import static uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroups.selectNonTissueExperiments;

public abstract class BioEntityPageController {

    protected static final String PROPERTY_TYPE_DESCRIPTION = "description";

    protected static final int DEFAULT_BASELINE_SPLIT = 10;

    protected BioEntityPropertyDao bioEntityPropertyDao;

    protected SolrQueryService solrQueryService;

    protected SpeciesLookupService speciesLookupService;

    private BioEntityCardProperties bioEntityCardProperties;

    private BioEntityPropertyService bioEntityPropertyService;

    private ApplicationProperties applicationProperties;

    private BaselineProfilesHeatMap baselineProfilesHeatMap;

    private ExperimentTrader experimentTrader;

    private GoTermTrader goTermTrader;

    private PoTermTrader poTermTrader;

    private DiffAnalyticsSearchService diffAnalyticsSearchService;

    private BaselineExperimentAssayGroupSearchService baselineExperimentAssayGroupSearchService;

    private BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;

    @Inject
    public void setBaselineExperimentProfileSearchService(BaselineExperimentProfileSearchService baselineExperimentProfileSearchService) {
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
    }

    @Inject
    public void setBaselineExperimentAssayGroupSearchService(BaselineExperimentAssayGroupSearchService baselineExperimentAssayGroupSearchService) {
        this.baselineExperimentAssayGroupSearchService = baselineExperimentAssayGroupSearchService;
    }

    @Inject
    public void setSpeciesLookupService(SpeciesLookupService speciesLookupService) {
        this.speciesLookupService = speciesLookupService;
    }

    @Inject
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
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
    public void setBaselineProfilesHeatMap(BaselineProfilesHeatMap baselineProfilesHeatMap) {
        this.baselineProfilesHeatMap = baselineProfilesHeatMap;
    }

    @Inject
    public void setBioEntityCardProperties(BioEntityCardProperties bioEntityCardProperties) {
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    @Inject
    public void setBioEntityPropertyService(BioEntityPropertyService bioEntityPropertyService) {
        this.bioEntityPropertyService = bioEntityPropertyService;
    }

    @Inject
    public void setBioEntityPropertyDao(BioEntityPropertyDao bioEntityPropertyDao) {
        this.bioEntityPropertyDao = bioEntityPropertyDao;
    }

    @Inject
    public void setSolrQueryService(SolrQueryService solrQueryService) {
        this.solrQueryService = solrQueryService;
    }

    @Inject
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Inject
    void setDifferentialBioentityExpressionBuilder(DiffAnalyticsSearchService diffAnalyticsSearchService) {
        this.diffAnalyticsSearchService = diffAnalyticsSearchService;
    }

    void loadDifferentialResults(String identifier, Model model) {
        loadDifferentialResults(Sets.newHashSet(identifier), model);
    }

    void loadDifferentialResults(Collection<String> geneIdentifiers, Model model) {
        DiffAnalyticsList diffAnalyticsList =
                diffAnalyticsSearchService.fetchTopAnySpecies(geneIdentifiers);

        model.addAttribute("bioentities", diffAnalyticsList);

        // setting FDR as cutoff
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

        model.addAttribute("preferences", requestPreferences);
    }


    public String showBioentityPage(String identifier, Model model, boolean singleGeneDiffHeatmap) {

        initBioentityPropertyService(identifier, model);

        // It may have been already added if we got here from BioentitiesSearchController L103
        if (!model.containsAttribute("searchDescription")) {
            model.addAttribute("searchDescription", identifier);
        }

        model.addAttribute("geneQuery", GeneQuery.create(identifier));

        model.addAttribute("exactMatch", true);

        model.addAttribute("showBioentityPropertiesPane", true);

        //toggle display of multi-gene columns (gene name, and organism) in the differential heatmap
        model.addAttribute("singleGeneDiffHeatmap", singleGeneDiffHeatmap);

        //bioentity properties panel data
        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioEntities";
    }

    void addWidgetHasBaselineProfiles(String identifier, Model model) {
        String species = fetchSpecies(identifier);
        String referenceExperimentAccession = applicationProperties.getBaselineReferenceExperimentAccession(species);

        try {
            //to check if the widget contains the identifier or not and inform properly in the results gene pages
            if (referenceExperimentAccession != null) {
                model.addAttribute("widgetHasBaselineProfiles", widgetHasBaselineProfiles(referenceExperimentAccession, species, ImmutableSet.of(identifier)));
            } else {
                model.addAttribute("widgetHasBaselineProfiles", false);
            }

        } catch (GenesNotFoundException e) {
            throw new ResourceNotFoundException("No gene profiles with identifier " + identifier);
        }
    }

    void addBaselineResults(Set<String> geneIds, Model model) {
        BaselineExperimentSearchResult tissueResults = baselineExperimentProfileSearchService.query(geneIds);

        if (tissueResults.isEmpty()) {
            addBaselineCounts(geneIds, model);
        } else {
            addWidget(model);
            addBaselineCountsForNonTissueExperiments(geneIds, model);
        }
    }

    void addWidget(Model model) {
        model.addAttribute("widgetHasBaselineProfiles", true);
    }

    void addBaselineCounts(Set<String> geneIds, Model model) {
        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = baselineExperimentAssayGroupSearchService.queryAnySpecies(geneIds, Optional.<String>absent());

        model.addAttribute("firstBaselineCounts", removeFirstAssayGroups(baselineExperimentAssayGroups, DEFAULT_BASELINE_SPLIT));
        model.addAttribute("remainingBaselineCounts", baselineExperimentAssayGroups);
    }

    void addBaselineCountsForNonTissueExperiments(Set<String> geneIds, Model model) {
        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = baselineExperimentAssayGroupSearchService.queryAnySpecies(geneIds, Optional.<String>absent());
        SortedSet<BaselineExperimentAssayGroup> nonTissueExperiments = selectNonTissueExperiments(baselineExperimentAssayGroups);

        model.addAttribute("firstBaselineCounts", removeFirstAssayGroups(nonTissueExperiments, DEFAULT_BASELINE_SPLIT));
        model.addAttribute("remainingBaselineCounts", nonTissueExperiments);
    }

    String fetchSpecies(String identifier) {
        return speciesLookupService.fetchSpeciesForBioentityId(identifier);
    }

    protected Map<String, String> buildPropertyNamesByTypeMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        for (String propertyType : getPagePropertyTypes()) {
            if (isDisplayedInPropertyList(propertyType)) {
                result.put(propertyType, bioEntityCardProperties.getPropertyName(propertyType));
            }
        }
        return result;
    }

    protected abstract boolean isDisplayedInPropertyList(String propertyType);

    abstract String[] getPagePropertyTypes();

    abstract String getBioentityPropertyName();

    protected void initBioentityPropertyService(String identifier, Model model) {
        String species = speciesLookupService.fetchSpeciesForBioentityId(identifier);

        SortedSetMultimap<String, String> propertyValuesByType = bioEntityPropertyDao.fetchGenePageProperties(identifier, getPagePropertyTypes());
        SortedSet<String> entityNames = propertyValuesByType.get(getBioentityPropertyName());
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }

        model.addAttribute("mainTitle", "Expression summary for " + entityNames.first() + " - " + StringUtils.capitalize(species));

        ImmutableSetMultimap<Integer, GoPoTerm> goTerms = mapGoTermsByDepth(propertyValuesByType.get("go"));
        ImmutableSetMultimap<Integer, GoPoTerm> poTerms = mapPoTermsByDepth(propertyValuesByType.get("po"));

        bioEntityPropertyService.init(species, propertyValuesByType, goTerms, poTerms, entityNames, identifier);
    }

    protected boolean widgetHasBaselineProfiles(String experimentAccession, String species, Set<String> identifiers) throws GenesNotFoundException {
        BaselineExperiment experiment;
        try {
            experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, null);
        } catch (ResourceNotFoundException e) {
            // if we don't have the baseline reference experiment, then no gene profiles
            return false;
        }

        BaselineProfileStreamOptionsWidgetQuery options = new BaselineProfileStreamOptionsWidgetQuery(experiment,
                species);

        BaselineProfilesList baselineProfiles = baselineProfilesHeatMap.fetch(options,solrQueryService.fetchResponseBasedOnRequestContext(Joiner.on(" ").join(identifiers), true, species));

        return (baselineProfiles.size() > 0);
    }


    protected ImmutableSetMultimap<Integer, GoPoTerm> mapGoTermsByDepth(Set<String> accessions) {
        ImmutableSetMultimap.Builder<Integer, GoPoTerm> builder = new ImmutableSetMultimap.Builder<>();

        for (String accession : accessions) {
            try {
                builder.put(goTermTrader.getDepth(accession), goTermTrader.getTerm(accession));
            } catch (NullPointerException e) {
                // Ignore terms which aren’t found in goIDToTerm.tsv
            }
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