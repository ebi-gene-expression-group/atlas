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
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.SortedSetMultimap;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.commands.BaselineProfilesHeatMap;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptionsWidgetQuery;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public abstract class BioEntityPageController {

    protected static final String PROPERTY_TYPE_DESCRIPTION = "description";

    protected SolrQueryService solrQueryService;

    private BioEntityCardProperties bioEntityCardProperties;

    private BioEntityPropertyService bioEntityPropertyService;

    private ApplicationProperties applicationProperties;

    private BaselineProfilesHeatMap baselineProfilesHeatMap;

    private ExperimentTrader experimentTrader;

    @Inject
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
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
    public void setSolrQueryService(SolrQueryService solrQueryService) {
        this.solrQueryService = solrQueryService;
    }

    @Inject
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String showBioentityPage(String identifier, Model model) {

        initBioentityPropertyService(identifier);

        model.addAttribute("entityIdentifier", identifier);

        //if it is false the bioentity property panel will not be shown and the
        //differential heatmap will visualize an extra column for design element (not required when the heatmap displays a single bioentity)
        model.addAttribute("singleBioentityPage", true);

        //if all geneIds and geneNames in the BioentityPage are the same we don't want to display in the heatmap the columns Genes and Organism
        model.addAttribute("bioentitySameIdentifier", true);

        String species = fetchSpecies(identifier);
        String referenceExperimentAccession = applicationProperties.getBaselineWidgetExperimentAccessionBySpecies(species);

        //to check if the widget contains the identifier or not and inform properly in the results gene pages
        //TODO: check if this is needed, may just need hasGeneProfiles
        model.addAttribute("hasReferenceBaselineExperimentForSpecies", StringUtils.isNotEmpty(referenceExperimentAccession));

        try {
            if (referenceExperimentAccession != null) {

                String ensemblIdentifiersForMiRNA = (String) model.asMap().get("ensemblIdentifiersForMiRNA");
                ImmutableSet<String> identifiers = (ensemblIdentifiersForMiRNA == null) ? ImmutableSet.of(identifier) : ImmutableSet.<String>builder().add(identifier).addAll(Splitter.on("+").split(ensemblIdentifiersForMiRNA)).build();

                model.addAttribute("hasGeneProfiles", hasGeneProfiles(referenceExperimentAccession, species, identifiers));
            } else {
                model.addAttribute("hasGeneProfiles", false);
            }

        } catch (GenesNotFoundException e) {
            throw new ResourceNotFoundException("No gene profiles with identifier " + identifier);
        }

        //bioentity properties panel data
        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioEntities";
    }

    String fetchSpecies(String identifier) {
        return solrQueryService.findSpeciesForBioentityId(identifier);
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

    protected void initBioentityPropertyService(String identifier) {
        String species = solrQueryService.findSpeciesForBioentityId(identifier);

        SortedSetMultimap<String, String> propertyValuesByType = solrQueryService.fetchGenePageProperties(identifier, getPagePropertyTypes());
        SortedSet<String> entityNames = propertyValuesByType.get(getBioentityPropertyName());
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }
        bioEntityPropertyService.init(species, propertyValuesByType, entityNames, identifier);
    }

    protected boolean hasGeneProfiles(String experimentAccession, String species, Set<String> identifiers) throws GenesNotFoundException {
        BaselineExperiment experiment;
        try {
            experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, null);
        } catch (ResourceNotFoundException e) {
            // if we don't have the baseline reference experiment, then no gene profiles
            return false;
        }

        // convert enstranscript, ensgene, and geneset ID to individual gene ids
        Set<String> geneIds = solrQueryService.fetchGeneIds(Joiner.on(" ").join(identifiers), true, species);

        BaselineProfileStreamOptionsWidgetQuery options = new BaselineProfileStreamOptionsWidgetQuery(experiment, species, geneIds);

        BaselineProfilesList baselineProfiles = baselineProfilesHeatMap.fetch(options);

        return (baselineProfiles.size() > 0);
    }

}