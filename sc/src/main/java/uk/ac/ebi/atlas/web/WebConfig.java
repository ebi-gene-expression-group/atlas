package uk.ac.ebi.atlas.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;

@EnableWebMvc //equivalent to mvc:annotation-driven
@Configuration
@PropertySource("classpath:configuration.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Inject
    Environment props;

//    @Inject
//    private AdminInterceptor adminInterceptor;

//    @Inject
//    private TimingInterceptor timingInterceptor;

    @Inject
    private DataFileHub dataFileHub;

    // equivalent to mvc:resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/versioned-resources-" + props.getProperty("resources.version") + "/**")
                .addResourceLocations("/versioned-resources/");
        registry.addResourceHandler("/expdata/**").addResourceLocations("file:" + dataFileHub.getExperimentDataLocation());
    }

    // equivalent to mvc:interceptors
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
//        registry.addInterceptor(timingInterceptor).addPathPatterns("/**");
//    }

}