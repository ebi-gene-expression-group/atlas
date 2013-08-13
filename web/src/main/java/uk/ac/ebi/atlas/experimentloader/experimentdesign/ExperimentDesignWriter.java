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

package uk.ac.ebi.atlas.experimentloader.experimentdesign;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.AbstractSDRFNode;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

public class ExperimentDesignWriter<T extends AbstractSDRFNode> {

    private static final String SAMPLE_NAME_HEADER_TEMPLATE = "Sample Characteristics[{0}]";
    private static final String FACTOR_NAME_HEADER_TEMPLATE = "Factor Values[{0}]";

    private CSVWriter csvWriter;
    private MageTabParser<T> mageTabParser;
    private ExperimentType experimentType;

    ExperimentDesignWriter(CSVWriter csvWriter, MageTabParser<T> mageTabParser, ExperimentType experimentType){
        this.csvWriter = csvWriter;
        this.mageTabParser = mageTabParser;
        this.experimentType = experimentType;
    }

    public void write(String experimentAccession) throws IOException {
        try {

            ExperimentDesign experimentDesign = mageTabParser.parse(experimentAccession);
            String[] columnHeaders = buildColumnHeaders(experimentType, experimentDesign);
            csvWriter.writeNext(columnHeaders);
            csvWriter.writeAll(experimentDesign.asTableData());
            csvWriter.flush();
        }finally {
            csvWriter.close();
        }

    }

    String[] buildColumnHeaders(ExperimentType experimentType, ExperimentDesign experimentDesign) {

        List<String> headers = Lists.newArrayList(getCommonColumnHeaders(experimentType));
        headers.addAll(toHeaders(experimentDesign.getSampleHeaders(), SAMPLE_NAME_HEADER_TEMPLATE));
        headers.addAll(toHeaders(experimentDesign.getFactorHeaders(), FACTOR_NAME_HEADER_TEMPLATE));

        return headers.toArray(new String[headers.size()]);
    }

    List<String> toHeaders(SortedSet<String> propertyNames, final String headerTemplate) {
        List<String> headers = new ArrayList<>();
        for (String propertyName: propertyNames){
            headers.add(MessageFormat.format(headerTemplate, propertyName));
        }
        return headers;
    }

    protected List<String> getCommonColumnHeaders(ExperimentType experimentType){
        switch(experimentType.getParent()){
            case MICROARRAY:
                return Lists.newArrayList("Assay", "Array");
            case BASELINE:
            case DIFFERENTIAL:
                return Lists.newArrayList("Run");
            default:
                throw new IllegalStateException("Invalid parent type: " + experimentType.getParent());
        }
    }
}
