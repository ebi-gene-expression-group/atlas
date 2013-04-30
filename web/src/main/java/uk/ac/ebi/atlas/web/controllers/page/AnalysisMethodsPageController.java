/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@Scope("request")
public class AnalysisMethodsPageController {

    private TsvReader tsvReader;

    private DownloadURLBuilder downloadURLBuilder;

    @Inject
    public AnalysisMethodsPageController(TsvReaderBuilder tsvReaderBuilder, DownloadURLBuilder downloadURLBuilder,
                                         @Value("#{configuration['experiment.analysis-method.path.template']}")
                                         String pathTemplate) {

        this.tsvReader = tsvReaderBuilder.forTsvFilePathTemplate(pathTemplate).build();
        this.downloadURLBuilder = downloadURLBuilder;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = "type")
    public String showGeneProfiles(@PathVariable String experimentAccession, Model model, HttpServletRequest request) throws IOException {

        model.addAttribute("csvLines", tsvReader.readAll(experimentAccession));

        model.addAttribute("experimentAccession", experimentAccession);

        model.addAttribute("rawDownloadUrl", downloadURLBuilder.buildDownloadRawUrl(request));

        model.addAttribute("normalizedUrl", downloadURLBuilder.buildDownloadNormalizedDataUrl(request));

        model.addAttribute("logFoldUrl", downloadURLBuilder.buildDownloadLogFoldDataUrl(request));

        model.addAttribute("analyticsDownloadUrl", downloadURLBuilder.buildDownloadAllAnalyticsUrl(request));

        return "experiment-analysis-methods";
    }


}
















