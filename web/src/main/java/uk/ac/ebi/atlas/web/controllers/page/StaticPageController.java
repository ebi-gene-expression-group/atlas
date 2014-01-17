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

import com.google.common.io.Files;
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
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Controller
@Scope("singleton")
public class StaticPageController {

    private final ServletContextResourceLoader servletContextResourceLoader;

    @Inject
    public StaticPageController(ServletContext servletContext) {
        servletContextResourceLoader = new ServletContextResourceLoader(servletContext);
    }

    @RequestMapping("/{pageName}.html")
    public String getStaticPage(HttpServletRequest request, @PathVariable String pageName) throws IOException {
        String filePath = String.format("/resources/html/%s.html", pageName);

        request.setAttribute("content", fetchFileContents(filePath));
        request.setAttribute("nav", pageName);
        return "static-template";
    }

    @RequestMapping("/help/{pageName}.html")
    public String getHelpPage(HttpServletRequest request, @PathVariable String pageName) throws IOException {
        String filePath = String.format("/resources/html/help/%s.html", pageName);

        request.setAttribute("content", fetchFileContents(filePath));
        request.setAttribute("nav", "help");
        return "static-template";
    }

    @RequestMapping("/help.html")
    public String getHelpPage(HttpServletRequest request) {
        request.setAttribute("pageName", "");
        return "help";
    }

    @RequestMapping("/{pageName}.hhhh")
    public String getHomePage(@PathVariable String pageName) {
        return "redirect:/home?pageName=" + pageName;
    }

    // manually load file contents instead of using c:import to avoid javax.servlet.jsp.JspTagException: 304 errors
    // see http://stackoverflow.com/questions/17218609/jsp-exception-when-i-try-to-import-static-file
    private String fetchFileContents(String filePath) throws IOException {
        return Files.toString(fetchFile(filePath), Charset.forName("UTF-8"));
    }

    private File fetchFile(String filePath) {
        Resource resource = servletContextResourceLoader.getResource(filePath);

        try {
            File file = resource.getFile();

            if (!file.exists()) {
                throw new ResourceNotFoundException("File " + filePath + " does not exist");
            }

            return file;
        } catch (IOException e) {
            throw new ResourceNotFoundException(e);
        }

    }

}
