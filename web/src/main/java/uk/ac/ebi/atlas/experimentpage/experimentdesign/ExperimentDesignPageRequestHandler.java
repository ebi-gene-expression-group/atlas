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

package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import com.google.gson.Gson;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public abstract class ExperimentDesignPageRequestHandler<T extends Experiment> {

    private DownloadURLBuilder downloadURLBuilder;

    protected ArrayDesignTrader arrayDesignTrader;

    protected static final String ALL_ARRAY_DESIGNS_ATTRIBUTE = "allArrayDesigns";

    @Inject
    void setDownloadURLBuilder(DownloadURLBuilder downloadURLBuilder) {
        this.downloadURLBuilder = downloadURLBuilder;
    }

    @Inject
    public void setArrayDesignTrader(ArrayDesignTrader arrayDesignTrader) {
        this.arrayDesignTrader = arrayDesignTrader;
    }

    public String handleRequest(Model model, HttpServletRequest request) {
        T experiment = (T) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        String experimentAccession = experiment.getAccession();

        ExperimentDesign experimentDesign = experiment.getExperimentDesign();

        // does the serialisation to JSON
        Gson gson = new Gson();
        // add table data to model
        List<String> assayHeaders = experimentDesign.getAssayHeaders();
        model.addAttribute("assayHeaders", gson.toJson(assayHeaders));
        model.addAttribute("sampleHeaders", gson.toJson(experimentDesign.getSampleHeaders()));
        model.addAttribute("factorHeaders", gson.toJson(experimentDesign.getFactorHeaders()));
        model.addAttribute("tableData", gson.toJson(experimentDesign.asTableData()));

        //analysed row accessions are added to the model separately,
        //because design table rows can be hidden or shown depending if they are or not part of the analysed subset
        String runAccessions = gson.toJson(getAnalysedRowsAccessions(experiment));
        model.addAttribute("runAccessions", runAccessions);

        // add general experiment attributes to model
        model.addAttribute("experimentAccession", experimentAccession);

        downloadURLBuilder.addDataDownloadUrlsToModel(model, request);

        extendModel(model, experiment, experimentAccession);

        return "experiment-experiment-design";

    }

    protected abstract void extendModel(Model model, T experiment, String experimentAccession);

    protected abstract Set<String> getAnalysedRowsAccessions(T experiment);

}
















