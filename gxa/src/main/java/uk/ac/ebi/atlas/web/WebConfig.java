package uk.ac.ebi.atlas.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import uk.ac.ebi.atlas.interceptors.AdminInterceptor;
import uk.ac.ebi.atlas.interceptors.TimingInterceptor;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@EnableWebMvc //equivalent to mvc:annotation-driven
@Configuration
@PropertySource("classpath:configuration.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Inject
    private Environment props;

    @Inject
    private AdminInterceptor adminInterceptor;

    @Inject
    private TimingInterceptor timingInterceptor;

    @Inject
    private DataFileHub dataFileHub;

    // equivalent to mvc:resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());

        registry.addResourceHandler("/expdata/**")
                .addResourceLocations("file:" + dataFileHub.getExperimentDataLocation());
    }

    // equivalent to mvc:interceptors
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
        registry.addInterceptor(timingInterceptor).addPathPatterns("/**");
    }

}