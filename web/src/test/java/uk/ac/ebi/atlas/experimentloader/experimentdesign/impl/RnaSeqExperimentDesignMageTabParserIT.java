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

package uk.ac.ebi.atlas.experimentloader.experimentdesign.impl;

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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml"})
public class RnaSeqExperimentDesignMageTabParserIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String MULTIFACTOR_EXPERIMENT_ACESSION = "E-GEOD-26284";

    @Inject
    private RnaSeqExperimentDesignMageTabParser subject;

    @Test
    public void asTableDataShouldReturnTheRightStuff() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(EXPERIMENT_ACCESSION);

        assertThat(experimentDesign.asTableData().size(), is(48));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("ERR030856","Homo sapiens",null,null,"16 tissues mixture",null,"16 tissues mixture"));
        assertThat(experimentDesign.asTableData().get(47), arrayContaining("ERR030903","Homo sapiens","60 years","Caucasian","thyroid","female","thyroid"));

    }

    @Test
    public void asTableDataForMultifactorShouldReturnTheRightStuff() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(MULTIFACTOR_EXPERIMENT_ACESSION);

        assertThat(experimentDesign.asTableData().size(), is(171));
        assertThat(experimentDesign.asTableData().get(0), arrayContaining("SRR089332", "Homo sapiens", "Coriell Cell Repositories http://ccr.coriell.org/Sections/Search/Search.aspx?PgId=165&q=GM12878", "GM12878", "B cell", "whole cell", null, "relatively normal", null, "female", "total RNA", "GM12878", "whole cell"));
        assertThat(experimentDesign.asTableData().get(170), arrayContaining("SRR534335","Homo sapiens","PromoCell","hMSC-AT cell line","human mesenchymal stem cell from adipose tissue (hMSC-AT)","whole cell",null,null,"adipose",null,"total RNA","hMSC-AT cell line","whole cell"));
    }
}