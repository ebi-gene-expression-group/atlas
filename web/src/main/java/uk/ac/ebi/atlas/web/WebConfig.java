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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import uk.ac.ebi.atlas.web.interceptors.AdminInterceptor;
import uk.ac.ebi.atlas.web.interceptors.ExperimentTimingInterceptor;
import uk.ac.ebi.atlas.web.interceptors.SearchTimingInterceptor;

import javax.inject.Inject;


@EnableWebMvc //equivalent to mvc:annotation-driven
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Inject
    private ExperimentTimingInterceptor experimentTimingInterceptor;
    @Inject
    private SearchTimingInterceptor searchTimingInterceptor;

    @Inject
    private AdminInterceptor adminInterceptor;

    @Value("#{configuration['experiment.data.location']}")
    private String experimentDataLocation;

    // equivalent to mvc:resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/json/gene-by-cutoff/**").addResourceLocations("classpath:/cutoff-histograms/");
        registry.addResourceHandler("/expdata/**").addResourceLocations("file:" + experimentDataLocation);
    }

    // equivalent to mvc:view-controller
    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.addViewController("/experiments").setViewName("experiments");
        viewControllerRegistry.addViewController("/experiments/").setViewName("experiments");
    }

    // equivalent to mvc:interceptors
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(experimentTimingInterceptor).addPathPatterns("/experiments/**");
        registry.addInterceptor(searchTimingInterceptor).addPathPatterns("/query");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
    }

}