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

package uk.ac.ebi.atlas.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import uk.ac.ebi.atlas.web.interceptors.AdminInterceptor;
import uk.ac.ebi.atlas.web.interceptors.CrossExperimentSearchInterceptor;
import uk.ac.ebi.atlas.web.interceptors.ExperimentInterceptor;

import javax.inject.Inject;


@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Inject
    private ExperimentInterceptor experimentInterceptor;
    @Inject
    private AdminInterceptor adminInterceptor;
    @Inject
    private CrossExperimentSearchInterceptor crossExperimentSearchInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/json/gene-by-cutoff/**").addResourceLocations("classpath:/cutoff-histograms/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.addViewController("/help.html").setViewName("help");
        viewControllerRegistry.addViewController("/baseline-atlas-help.html").setViewName("baseline-atlas-help");
        viewControllerRegistry.addViewController("/differential-atlas-help.html").setViewName("differential-atlas-help");
        viewControllerRegistry.addViewController("/experiments").setViewName("experiments");
        viewControllerRegistry.addViewController("/experiments/").setViewName("experiments");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(experimentInterceptor).addPathPatterns("/experiments/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
    }

}