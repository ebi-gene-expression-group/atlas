package uk.ac.ebi.atlas.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import uk.ac.ebi.atlas.web.interceptors.ExperimentInterceptor;

import javax.inject.Inject;


@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Inject
    private ExperimentInterceptor experimentInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/public-resources/");
        registry.addResourceHandler("/json/gene-by-cutoff/**").addResourceLocations("classpath:/cutoff-histograms/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(experimentInterceptor).addPathPatterns("/experiments/**");
    }

}