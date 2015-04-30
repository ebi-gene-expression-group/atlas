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

package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class TwoColourExperimentMageTabParserIT {

    private static final String TWO_COLOUR_EXPERIMENT_ACCESSION = "E-GEOD-43049";

    @Inject
    private TwoColourExperimentMageTabParser subject;

    @Ignore
    public void cultureConditionFactorValuesAreSelectedForTheCorrectChannel() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(TWO_COLOUR_EXPERIMENT_ACCESSION).getExperimentDesign();

        assertThat(experimentDesign.asTableData().size(), is(12));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("GSM1055612.Cy3", "A-AGIL-28", "Homo sapiens", "Caco-2", "Conventional", "Conventional"));
        assertThat(experimentDesign.asTableData().get(1), arrayContaining("GSM1055612.Cy5", "A-AGIL-28", "Homo sapiens", "Caco-2", "Apical anaerobic", "Apical anaerobic"));
        assertThat(experimentDesign.asTableData().get(10), arrayContaining("GSM1055617.Cy3","A-AGIL-28","Homo sapiens","Caco-2","Conventional","Conventional"));
        assertThat(experimentDesign.asTableData().get(11), arrayContaining("GSM1055617.Cy5","A-AGIL-28","Homo sapiens","Caco-2","Apical anaerobic","Apical anaerobic"));

    }
}