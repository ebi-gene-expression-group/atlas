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

package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class TsvReaderIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    @Value("#{configuration['experiment.analysis-method.path.template']}")
    private String analysisMethodsTemplate;

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String experimentDesignTemplate;

    @Inject
    private FileTsvReaderBuilder fileTsvReaderBuilder;

    private TsvReader subject;

    @Before
    public void setUp() throws Exception {
        subject = fileTsvReaderBuilder.forTsvFilePathTemplate(analysisMethodsTemplate)
                                  .withExperimentAccession(EXPERIMENT_ACCESSION).build();
    }

    @Test
    public void readLine() {
        String[] firstLine = subject.readLine(0L);
        assertThat(firstLine, arrayContaining("# Pipeline version", "0.1.6"));
    }

    @Test
    public void readAll() {

        // given
        List<String[]> result = subject.readAll();
        String[] firstLine = result.get(0);
        String[] lastLine = result.get(result.size() - 1);

        // then
        assertThat(firstLine, arrayContaining("Analyzed Libraries", "<a href=\"experiments/E-MTAB-513/experiment-design\">Paired-end only</a>"));
        assertThat(lastLine, arrayContaining("Normalized Counts per Gene", "Obtained from the <a href=\"http://cufflinks.cbcb.umd.edu/manual.html#fpkm_tracking_format\">genes.fpkm_tracking files</a>, then averaged for all biological replicates (if any)"));
    }

    @Test
    public void readExpDesignAll() {

        // given
        subject = fileTsvReaderBuilder.forTsvFilePathTemplate(experimentDesignTemplate).build();
        List<String[]> result = subject.readAll();
        String[] firstLine = result.get(0);
        String[] lastLine = result.get(result.size() - 1);

        // then
        assertThat(firstLine, arrayContaining("Run", "Sample Characteristic[Organism]", "Sample Characteristic Ontology Term[Organism]", "Sample Characteristic[age]", "Sample Characteristic Ontology Term[age]", "Sample Characteristic[ethnic group]", "Sample Characteristic Ontology Term[ethnic group]", "Sample Characteristic[organism part]", "Sample Characteristic Ontology Term[organism part]", "Sample Characteristic[sex]", "Sample Characteristic Ontology Term[sex]", "Factor Value[organism part]", "Factor Value Ontology Term[organism part]"));
        assertThat(lastLine, arrayContaining("ERR030903", "Homo sapiens", "NCBITaxon/NCBITaxon:9606", "60 years", "", "Caucasian", "EFO/EFO:0003156", "thyroid", "UBERON/UBERON:0002046", "female", "EFO/EFO:0001265", "thyroid", "UBERON/UBERON:0002046"));
    }
}
