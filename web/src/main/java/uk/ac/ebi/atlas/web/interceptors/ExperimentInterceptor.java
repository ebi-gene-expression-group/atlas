/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.web.interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.model.cache.differential.DifferentialExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Named("experimentInterceptor")
public class ExperimentInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(ExperimentInterceptor.class);
    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    public static final String STOP_WATCH = "stopWatch";
    public static final String EXPERIMENT_TYPE = "experimentType";

    private BaselineExperimentsCache baselineExperimentsCache;
    private DifferentialExperimentsCache differentialExperimentsCache;

    private ApplicationProperties applicationProperties;

    public ExperimentInterceptor() {
    }

    @Inject
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Inject
    public void setBaselineExperimentsCache(BaselineExperimentsCache baselineExperimentsCache) {
        this.baselineExperimentsCache = baselineExperimentsCache;
    }

    @Inject
    public void setDifferentialExperimentsCache(DifferentialExperimentsCache differentialExperimentsCache) {
        this.differentialExperimentsCache = differentialExperimentsCache;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
/*
        String experimentAccession = extractExperimentAccessionFromURI(request);

        if (applicationProperties.getExperimentIdentifiers().contains(experimentAccession)) {

            request.setAttribute(EXPERIMENT_ACCESSION, experimentAccession);


            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();
            request.setAttribute(STOP_WATCH, stopWatch);

            return true;
        }
*/
//        response.sendError(404);
//        return false;

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();
        request.setAttribute(STOP_WATCH, stopWatch);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws IOException {
/*
        String experimentAccession = (String) request.getAttribute(EXPERIMENT_ACCESSION);

        if (modelAndView != null) { //it is null for REST services

            Map modelMap = modelAndView.getModel();

            Experiment experiment = getExperiment(experimentAccession);

            Set<String> allSpecies = experiment.getSpecies();

            modelMap.put("allSpecies", StringUtils.join(allSpecies, ", "));

            modelMap.put("experimentDescription", experiment.getDescription());

            modelMap.put("hasExtraInfo", experiment.hasExtraInfoFile());


        }
*/
        StopWatch stopWatch = (StopWatch) request.getAttribute(STOP_WATCH);

        stopWatch.stop();

        logger.info("<postHandle> time taken " + stopWatch.getTotalTimeSeconds()
                + " s - geneQuery = " + request.getParameter("geneQuery")
                + ", query factor values = " + request.getParameter("queryFactorValues")
                + ", cutoff = " + request.getParameter("cutoff"));

    }
/*
    private String getExperimentType(String experimentAccession) {
        return getExperiment(experimentAccession) instanceof BaselineExperiment ?
                                                    "baseline" : "differential";
    }

    Experiment getExperiment(String experimentAccession) {
        Experiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);
        if (experiment == null) {
            experiment = differentialExperimentsCache.getExperiment(experimentAccession);
        }
        return experiment;
    }
*/
    String extractExperimentAccessionFromURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        String contextPath = request.getContextPath();

        String experimentPath = StringUtils.difference(contextPath + "/experiments/", requestURI);

        String experimentAccession = StringUtils.substringBefore(experimentPath, "/");

        //we want to remove jsessionid stuff eventually added by the server
        experimentAccession = StringUtils.substringBefore(experimentAccession, ";");

        //we want to remove file type extensions
        return StringUtils.substringBeforeLast(experimentAccession, ".");
    }

}
