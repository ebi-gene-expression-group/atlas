package uk.ac.ebi.atlas.utils;


import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResourceLoader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import java.io.IOException;

@Named("arrayDesignQCReport")
@Scope("singleton")
public class ArrayDesignQCReport {

    private final ServletContextResourceLoader servletContextResourceLoader;

    @Inject
    public ArrayDesignQCReport(ServletContext servletContext) {
        servletContextResourceLoader = new ServletContextResourceLoader(servletContext);
    }

    public boolean hasQCReport(String experimentAccession, String arrayDesign) throws IOException {

        String path = String.format("/resources/html/qc/%s_%s_QM/index.html", experimentAccession, arrayDesign);

        Resource resource = servletContextResourceLoader.getResource(path);

        return resource.exists();
    }


}
