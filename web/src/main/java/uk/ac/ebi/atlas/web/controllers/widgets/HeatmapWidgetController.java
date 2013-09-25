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

package uk.ac.ebi.atlas.web.controllers.widgets;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@Controller
@Scope("request")
public final class HeatmapWidgetController {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";

    private static final String ALL_SPECIES_ATTRIBUTE = "allSpecies";
    private static final String PUBMED_IDS_ATTRIBUTE = "pubMedIds";
    private static final String EXPERIMENT_DESCRIPTION_ATTRIBUTE = "experimentDescription";
    private static final String HAS_EXTRA_INFO_ATTRIBUTE = "hasExtraInfo";
    private static final String EXPERIMENT_TYPE_ATTRIBUTE = "type";

    private ApplicationProperties applicationProperties;

    private SolrQueryService solrQueryService;

    private ExperimentTrader experimentTrader;

    @Inject
    private HeatmapWidgetController(ExperimentTrader experimentTrader,
                                    ApplicationProperties applicationProperties, SolrQueryService solrQueryService) {
        this.experimentTrader = experimentTrader;
        this.applicationProperties = applicationProperties;
        this.solrQueryService = solrQueryService;
    }

    @RequestMapping(value = "/widgets/heatmap/protein")
    public String dispatchWidget(HttpServletRequest request,
                                 @RequestParam(value = "geneQuery", required = true) String bioEntityAccession,
                                 @RequestParam(value = "propertyType", required = false) String propertyType,
                                 @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                 Model model) {

        String species;
        try{
            species = solrQueryService.getSpeciesForPropertyValue(bioEntityAccession, propertyType);
        } catch(Exception e){
            model.addAttribute("errorMessage", "Species could not be determined");
            return "widget-error";
        }
        String experimentAccession = applicationProperties.getExperimentAccessionBySpecies(species);

        if (!StringUtils.isEmpty(experimentAccession)) {
            Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

            request.setAttribute(EXPERIMENT_ATTRIBUTE, experiment);

            Set<String> allSpecies = experiment.getSpecies();

            model.addAttribute(EXPERIMENT_TYPE_ATTRIBUTE, experiment.getType());

            model.addAttribute(ALL_SPECIES_ATTRIBUTE, StringUtils.join(allSpecies, ", "));

            model.addAttribute(EXPERIMENT_DESCRIPTION_ATTRIBUTE, experiment.getDescription());

            model.addAttribute(HAS_EXTRA_INFO_ATTRIBUTE, experiment.hasExtraInfoFile());

            model.addAttribute(PUBMED_IDS_ATTRIBUTE, experiment.getPubMedIds());

            String requestURL = getRequestURL(request);

            String mappedSpecies = experiment.getRequestSpeciesName(species);

            String organismParameters = StringUtils.isEmpty(mappedSpecies) ? "" : "&serializedFilterFactors=ORGANISM:" + mappedSpecies;

            return "forward:" + requestURL + "?type=" + experiment.getType().getParent() + organismParameters;
        } else {
            model.addAttribute("identifier", bioEntityAccession);
            return "widget-error";
        }

    }

    private String getRequestURL(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();

        return StringUtils.substringAfter(requestURI, contextPath);
    }

}
