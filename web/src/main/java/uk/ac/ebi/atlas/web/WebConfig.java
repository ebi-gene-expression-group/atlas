package uk.ac.ebi.atlas.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
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
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry){
        viewControllerRegistry.addViewController("/help.html").setViewName("help");
        viewControllerRegistry.addViewController("/help-2.html").setViewName("help-2");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(experimentInterceptor).addPathPatterns("/experiments/**");
    }

}