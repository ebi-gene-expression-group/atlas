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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

@Named("experimentInterceptor")
public class ExperimentInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(ExperimentInterceptor.class);

    private ExperimentsCache experimentsCache;
    private ApplicationProperties applicationProperties;

    public ExperimentInterceptor(){
    }


    @Inject
    public void setApplicationProperties(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    @Inject
    public void setExperimentsCache(ExperimentsCache experimentsCache){
        this.experimentsCache = experimentsCache;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{

        String requestURI = request.getRequestURI();

        String contextPath = request.getContextPath();

        String experimentPath = StringUtils.difference(contextPath+"/experiments/",requestURI);

        String experimentAccession = StringUtils.substringBefore(experimentPath, "/");

        experimentAccession = StringUtils.substringBeforeLast(experimentAccession, ".");

        if (applicationProperties.getExperimentIdentifiers().contains(experimentAccession)) {
            request.setAttribute("experimentAccession", experimentAccession);
            return true;
        }

        response.sendError(404);
        return false;
    }



    @Override
    public void postHandle(HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler,
                              ModelAndView modelAndView) throws IOException{

        String experimentAccession = (String)request.getAttribute("experimentAccession");

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        String specie = experiment.getSpecie();

        modelAndView.getModel().put("specie", specie);

        modelAndView.getModel().put("experimentDescription", experiment.getDescription());

    }
}
