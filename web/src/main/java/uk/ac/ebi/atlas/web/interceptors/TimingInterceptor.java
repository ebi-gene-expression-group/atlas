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

package uk.ac.ebi.atlas.web.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
public class TimingInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LogManager.getLogger(TimingInterceptor.class);

    protected static final String STOP_WATCH = "requestURLStopWatch";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestURL = request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        LOGGER.info(requestURL + " - start");

        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        request.setAttribute(STOP_WATCH, stopWatch);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        StopWatch stopWatch = (StopWatch) request.getAttribute(STOP_WATCH);
        stopWatch.stop();

        String requestURL = request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        LOGGER.info(requestURL + " - time taken " + stopWatch.getTotalTimeSeconds());
    }

}
