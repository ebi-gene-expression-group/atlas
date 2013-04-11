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

import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialExperimentFullDataWriter extends ExpressionsWriter {

    public static final String GENE_NAME = "Gene name";
    public static final String GENE_ID = "Gene Id";

    @Inject
    public DifferentialExperimentFullDataWriter(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider) {
        super(csvReaderBuilder, geneNamesProvider);
    }


    @Override
    protected String[] buildHeader(String[] header) {
        String[] headerWithoutFirstElement = ArrayUtils.remove(header, 0);
        return ArrayUtils.addAll(new String[]{GENE_NAME, GENE_ID}, headerWithoutFirstElement);
    }

}
