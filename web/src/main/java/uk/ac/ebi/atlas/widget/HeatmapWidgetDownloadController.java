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

package uk.ac.ebi.atlas.widget;

import com.google.common.base.Joiner;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineTissueExperimentSearchResult;
import uk.ac.ebi.atlas.search.baseline.BaselineTissueExperimentSearchResultFormatter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Scope("request")
public final class HeatmapWidgetDownloadController {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";

    private final BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;
    private final String tsvFileMastheadTemplate;

    @Inject
    private HeatmapWidgetDownloadController(BaselineExperimentProfileSearchService baselineExperimentProfileSearchService,
                                            @Value("classpath:/file-templates/download-headers-baseline-widget.txt") Resource tsvFileMastheadResource) throws IOException {
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
        this.tsvFileMastheadTemplate = IOUtils.toString(tsvFileMastheadResource.getInputStream());
    }

    @RequestMapping(value = "/widgets/heatmap/bioentity.tsv", method = RequestMethod.GET)
    public void heatmapWidgetData (@RequestParam(value = "geneQuery", required = true) String bioEntityAccession,
                                     @RequestParam(value = "species", required = true) String species,
                                     HttpServletResponse response) throws IOException {


        BaselineTissueExperimentSearchResult searchResult = baselineExperimentProfileSearchService.query(bioEntityAccession, species, true);

        if (!searchResult.isEmpty()) {
            setHttpHeaders(response, bioEntityAccession + "_baseline.tsv");
            PrintWriter writer = response.getWriter();
            writer.write(formatFileHeader(bioEntityAccession));
            writeTsv(searchResult, writer);
        }
    }

    private void setHttpHeaders(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("text/tab-separated-values");
    }

    private String formatFileHeader(String bioEntityAccession) {
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, bioEntityAccession, timeStamp) + "\n";
    }

    private void writeTsv(BaselineTissueExperimentSearchResult searchResult, PrintWriter writer)  {
        BaselineTissueExperimentSearchResultFormatter formatter = new BaselineTissueExperimentSearchResultFormatter(searchResult);

        String headers = Joiner.on("\t").join(formatter.getHeaders()) + "\n";
        writer.write(headers);

        for (String[] row : formatter) {
            String rowTab = Joiner.on("\t").join(row);
            writer.write(rowTab + "\n");
        }

    }

}
