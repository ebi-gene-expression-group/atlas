package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.ServletContextResourceLoader;
import uk.ac.ebi.atlas.utils.QCReportUtil;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.MessageFormat;

@Controller
@Scope("singleton")
public class QCReportController {

    private final ServletContextResourceLoader servletContextResourceLoader;
    private final QCReportUtil qcReportUtil;

    @Inject
    public QCReportController(ServletContext servletContext, QCReportUtil qcReportUtil) {
        this.servletContextResourceLoader = new ServletContextResourceLoader(servletContext);
        this.qcReportUtil = qcReportUtil;
    }

    @RequestMapping("/experiments/{experimentAccession}/qc/{arrayDesign}/{resource:.*}")
    public String getQCPage(HttpServletRequest request, @PathVariable String experimentAccession,
                            @PathVariable String arrayDesign,
                            @PathVariable String resource) throws IOException {

        if(!resource.equals("index.html")) {
            return forwardToQcResource(experimentAccession, arrayDesign, resource);
        }

        if(!qcReportUtil.hasQCReport(experimentAccession, arrayDesign)) {
            throw new ResourceNotFoundException("No qc report for " + experimentAccession + " array design " + arrayDesign);
        }

        String path = qcReportUtil.buildQCReportIndexHtmlPath(experimentAccession, arrayDesign);

        request.setAttribute("contentPath", FileSystems.getDefault().getPath(path));
        request.setAttribute("experimentAccession", experimentAccession);

        return "qc-template";
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToQcResource(String experimentAccession, String arrayDesign, String resource) throws IOException {
        String path = MessageFormat.format("/qc/{0}/qc/{0}_{1}_QM/{2}", experimentAccession, arrayDesign, resource);

        return "forward:" + path;
    }

    private Resource fetchResource(String path) {
        Resource resource = servletContextResourceLoader.getResource(path);
        if (!resource.exists() || !resource.isReadable()) {
            throw new ResourceNotFoundException("Resource " + path + " does not exist");
        }
        return resource;
    }
}
