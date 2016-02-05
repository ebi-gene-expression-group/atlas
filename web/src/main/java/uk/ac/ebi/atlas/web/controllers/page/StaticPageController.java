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

package uk.ac.ebi.atlas.web.controllers.page;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.ServletContextResourceLoader;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@Scope("singleton")
public class    StaticPageController {

    private final ServletContextResourceLoader servletContextResourceLoader;

    @Inject
    public StaticPageController(ServletContext servletContext) {
        servletContextResourceLoader = new ServletContextResourceLoader(servletContext);
    }

    @RequestMapping("/{pageName}.html")
    public String getStaticPage(HttpServletRequest request, @PathVariable String pageName) throws IOException {
        String path = String.format("/resources/html/%s.html", pageName);

        request.setAttribute("contentResource", fetchResource(path));
        request.setAttribute("nav", pageName);
        return "static-template";
    }

    @RequestMapping("/help/{pageName}.html")
    public String getHelpPage(HttpServletRequest request, @PathVariable String pageName) throws IOException {
        String path = String.format("/resources/html/help/%s.html", pageName);

        request.setAttribute("contentResource", fetchResource(path));
        request.setAttribute("nav", "help");
        return "static-template";
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
