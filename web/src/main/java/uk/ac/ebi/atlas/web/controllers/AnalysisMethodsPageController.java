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

package uk.ac.ebi.atlas.web.controllers;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
public class AnalysisMethodsPageController {

    private static final String ANALYSIS_METHODS_FILE_SUFFIX = "-analysis-methods.tsv";

    @Value("#{configuration['experiment.analysis-method.path.template']}")
    private String pathTemplate;

    @RequestMapping("/experiments/{experimentAccession}-analysis-methods")
    public String showGeneProfiles(@PathVariable String experimentAccession, Model model) throws IOException {

        Path filePath = FileSystems.getDefault().getPath(MessageFormat.format(pathTemplate, experimentAccession));

        Reader dataFileReader = new InputStreamReader(Files.newInputStream(filePath));

        CSVReader csvReader = new CSVReader(dataFileReader, '\t');

        List<String[]> csvLines = csvReader.readAll();

        model.addAttribute("experimentAccession", experimentAccession);
        model.addAttribute("csvLines", csvLines);

        return "experiment-analysis-methods";
    }



}
















