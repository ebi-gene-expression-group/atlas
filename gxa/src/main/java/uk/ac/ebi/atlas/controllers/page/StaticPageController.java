package uk.ac.ebi.atlas.controllers.page;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.ServletContextResourceLoader;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class StaticPageController {

    private final ServletContextResourceLoader servletContextResourceLoader;

    @Inject
    public StaticPageController(ServletContext servletContext) {
        servletContextResourceLoader = new ServletContextResourceLoader(servletContext);
    }

    @RequestMapping("/{pageName}.html")
    public String getStaticPage(HttpServletRequest request,
                                @PathVariable String pageName) throws IOException {
        String path = String.format("/resources/html/%s.html", pageName);
        request.setAttribute("contentResource", fetchResource(path));
        return "static";
    }

    @RequestMapping("/help/{pageName}.html")
    public String getHelpPage(HttpServletRequest request,
                              @PathVariable String pageName) throws IOException {
        String path = String.format("/resources/html/help/%s.html", pageName);
        request.setAttribute("contentResource", fetchResource(path));
        return "static";
    }

    @RequestMapping("/{pageName}.hhhh")
    public String getHomePage(@PathVariable String pageName) {
        return "redirect:/home?pageName=" + pageName;
    }

    private Resource fetchResource(String path) {
        Resource resource = servletContextResourceLoader.getResource(path);
        if (!resource.exists() || !resource.isReadable()) {
            throw new ResourceNotFoundException("Resource " + path + " does not exist");
        }
        return resource;
    }

}
