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
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Named("experimentInterceptor")
public class ExperimentInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(ExperimentInterceptor.class);

    private ExperimentsCache experimentsCache;
    private ApplicationProperties applicationProperties;

    public ExperimentInterceptor() {
    }


    @Inject
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Inject
    public void setExperimentsCache(ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String requestURI = request.getRequestURI();

        String contextPath = request.getContextPath();

        String experimentPath = StringUtils.difference(contextPath + "/experiments/", requestURI);

        String experimentAccession = StringUtils.substringBefore(experimentPath, "/");

        //we want to remove jsessionid stuff eventually added by the server
        experimentAccession = StringUtils.substringBefore(experimentAccession, ";");

        //we want to remove file type extensions
        experimentAccession = StringUtils.substringBeforeLast(experimentAccession, ".");

        if (applicationProperties.getExperimentIdentifiers().contains(experimentAccession)) {

            if (request.getParameterValues("filterFactorValues") != null) {
                Set<FactorValue> offendingFilterFactorValues = validateFilterFactorValues(experimentAccession,
                        request.getParameterValues("filterFactorValues"));
                if (offendingFilterFactorValues.size() > 0) {
                    logger.warn("Offending filterFactorValues found: " + offendingFilterFactorValues);
                    response.sendError(404);
                    return false;
                }
            }

            request.setAttribute("experimentAccession", experimentAccession);

            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();
            request.setAttribute("stopWatch", stopWatch);

            return true;
        }


        response.sendError(404);
        return false;
    }

    /**
     * Checks FactorValue objects against all FactorValue object of an experiment.
     * If an illegal FactorValue has been found, it will be collected in the result set.
     */
    private Set<FactorValue> validateFilterFactorValues(String experimentAccession, String[] requestFilterFactorValues) {

        // collect all factorValues from experiment
        Set<FactorValue> allFactorValues = new HashSet<>();

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);
        for (String run : experiment.getExperimentRunAccessions()) {
            ExperimentRun experimentRun = experiment.getExperimentRun(run);
            // ToDo: this is necessary because of an error in supplied analysis-methods file
            if (experimentRun != null)
                allFactorValues.addAll(experiment.getAllFactorValues(run));
        }

        Set<FactorValue> result = new HashSet<>();
        for (String requestFilterFactorValue : requestFilterFactorValues) {
            FactorValue factorValue = FactorValue.createFactorValue(requestFilterFactorValue);
            if (!allFactorValues.contains(factorValue)) {
                if (factorValue != null) {
                    result.add(factorValue);
                }
            }
        }
        return result;
    }


    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws IOException {

        String experimentAccession = (String) request.getAttribute("experimentAccession");

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        String specie = experiment.getSpecie();

        modelAndView.getModel().put("specie", specie);

        modelAndView.getModel().put("experimentDescription", experiment.getDescription());

        StopWatch stopWatch = (StopWatch) request.getAttribute("stopWatch");

        stopWatch.stop();

        logger.info("<postHandle> time taken " + stopWatch.getTotalTimeSeconds()
                + " s - geneQuery = " + request.getParameter("geneQuery")
                + ", query factor values = " + request.getParameter("queryFactorValues")
                + ", cutoff = " + request.getParameter("cutoff"));

    }
}
