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

package uk.ac.ebi.atlas.commands.download;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

@Named
public class DataWriterFactory {

    @Value("#{configuration['diff.experiment.raw-counts.path.template']}")
    private String differentialExperimentRawCountsFileUrlTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialExperimentAnalyticsFileUrlTemplate;

    @Value("#{configuration['microarray.experiment.data.path.template']}")
    private String microarrayExperimentAnalyticsFileUrlTemplate;

    @Value("#{configuration['microarray.normalized.data.path.template']}")
    private String microarrayExperimentNormalizedFileUrlTemplate;

    @Value("#{configuration['microarray.log-fold-changes.data.path.template']}")
    private String microarrayExperimentLogFoldFileUrlTemplate;


    private TsvReaderUtils tsvReaderUtils;


    @Inject
    public DataWriterFactory(TsvReaderUtils tsvReaderUtils) {
        this.tsvReaderUtils = tsvReaderUtils;
    }


    public ExpressionsWriter getRnaSeqAnalyticsDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {

        final AnalyticsDataHeaderBuilder headerBuilder = new AnalyticsDataHeaderBuilder();
        headerBuilder.setExperiment(experiment);

        ExpressionsWriterImpl expressionsWriter = new ExpressionsWriterImpl(tsvReaderUtils);
        expressionsWriter.setFileUrlTemplate(differentialExperimentAnalyticsFileUrlTemplate);
        initWriter(expressionsWriter, experiment.getAccession(), responseWriter);

        return expressionsWriter;
    }

    public ExpressionsWriter getRnaSeqRawDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {

        ExpressionsWriterImpl expressionsWriter = new ExpressionsWriterImpl(tsvReaderUtils);
        expressionsWriter.setFileUrlTemplate(differentialExperimentRawCountsFileUrlTemplate);

        initWriter(expressionsWriter, experiment.getAccession(), responseWriter);

        return expressionsWriter;
    }


    public ExpressionsWriter getMicroarrayAnalyticsDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {
        AnalyticsDataHeaderBuilder headerBuilder = new AnalyticsDataHeaderBuilder();
        headerBuilder.setExperiment(experiment);

        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl(tsvReaderUtils);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentAnalyticsFileUrlTemplate);

        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter);

        return microarrayDataWriter;

    }

    public ExpressionsWriter getMicroarrayRawDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {

        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl(tsvReaderUtils);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentNormalizedFileUrlTemplate);

        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter);

        return microarrayDataWriter;
    }

    public ExpressionsWriter getMicroarrayLogFoldDataWriter(DifferentialExperiment experiment, PrintWriter responseWriter) {

        ExpressionsWriterImpl microarrayDataWriter = new ExpressionsWriterImpl(tsvReaderUtils);
        microarrayDataWriter.setFileUrlTemplate(microarrayExperimentLogFoldFileUrlTemplate);

        initWriter(microarrayDataWriter, experiment.getAccession(), responseWriter);

        return microarrayDataWriter;
    }


    private void initWriter(ExpressionsWriterImpl expressionsWriter, String experimentAccession,
                            PrintWriter responseWriter) {

        expressionsWriter.setResponseWriter(new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER));
        expressionsWriter.setExperimentAccession(experimentAccession);
    }


}
