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

package uk.ac.ebi.atlas.expdesign;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class MageTabLimpopoExpDesignParserTest {

    static final String EXPERIMENT_ACCESSION_E_MTAB_513 = "E-MTAB-513";

    static final String EXPERIMNET_ACCESSION_E_GEOD_26284 = "E-GEOD-26284";

    MageTabLimpopoExpDesignParser subject;

    @Before
    public void setUp() throws Exception {

        subject = new MageTabLimpopoExpDesignParser();
        subject.setIdfUrlTemplate("http://www.ebi.ac.uk/arrayexpress/files/{0}/{0}.idf.txt");
        subject.setIdfPathTemplate("/magetab/{0}/{0}.idf.txt");

    }

    @Test(expected = IllegalStateException.class)
    public void testForExperimentAccession() throws Exception {
        subject.forExperimentAccession(null);
        subject.build();
    }

    @Test
    public void testBuild() throws Exception {
        assertThat(subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_513).build(), is(subject));
    }

    @Test
    public void testExtractCharacteristics513() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_513).build();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("sex", "age", "organism part", "Organism", "ethnic group"));
    }

    @Test
    public void testExtractFactors513() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_513).build();
        assertThat(subject.extractFactors(), containsInAnyOrder("organism part"));
    }

    @Test
    public void testExtractRunAccessions513() throws Exception {
        subject.forExperimentAccession(EXPERIMENT_ACCESSION_E_MTAB_513).build();
        assertThat(subject.extractRunAccessions(), containsInAnyOrder("ERR030872", "ERR030873", "ERR030874", "ERR030875", "ERR030876", "ERR030877", "ERR030878", "ERR030879", "ERR030888", "ERR030889", "ERR030890", "ERR030891", "ERR030892", "ERR030893", "ERR030894", "ERR030895", "ERR030856", "ERR030857", "ERR030858", "ERR030859", "ERR030860", "ERR030861", "ERR030862", "ERR030863", "ERR030880", "ERR030881", "ERR030882", "ERR030883", "ERR030884", "ERR030885", "ERR030886", "ERR030887", "ERR030896", "ERR030897", "ERR030898", "ERR030899", "ERR030900", "ERR030901", "ERR030902", "ERR030903", "ERR030864", "ERR030865", "ERR030866", "ERR030867", "ERR030868", "ERR030869", "ERR030870", "ERR030871"));
    }

    @Test
    public void testExtractCharacteristics26284() throws Exception {
        subject.forExperimentAccession(EXPERIMNET_ACCESSION_E_GEOD_26284).build();
        assertThat(subject.extractCharacteristics(), containsInAnyOrder("sex", "biosource provider", "cell line", "cellular component", "organism part", "karyotype", "disease state", "cell type", "Organism"));
    }

    @Test
    public void testExtractFactors26284() throws Exception {
        subject.forExperimentAccession(EXPERIMNET_ACCESSION_E_GEOD_26284).build();
        assertThat(subject.extractFactors(), containsInAnyOrder("RNA", "cell line", "cellular component"));
    }

    @Test
    public void testExtractRunAccessions26284() throws Exception {
        subject.forExperimentAccession(EXPERIMNET_ACCESSION_E_GEOD_26284).build();
        assertThat(subject.extractRunAccessions(), containsInAnyOrder("SRR387660", "SRR387664", "SRR387659", "SRR387663", "SRR307897", "SRR307898", "SRR307899", "SRR307900", "SRR307901", "SRR307902", "SRR307903", "SRR307904", "SRR307905", "SRR307906", "SRR307907", "SRR307908", "SRR307909", "SRR307910", "SRR307911", "SRR307912", "SRR307913", "SRR307914", "SRR307915", "SRR307916", "SRR307917", "SRR307918", "SRR307919", "SRR307920", "SRR307921", "SRR307922", "SRR307923", "SRR307924", "SRR307925", "SRR307926", "SRR307927", "SRR307928", "SRR307929", "SRR307930", "SRR307931", "SRR307932", "SRR307933", "SRR315297", "SRR315298", "SRR315299", "SRR315300", "SRR315301", "SRR315302", "SRR315303", "SRR315304", "SRR315305", "SRR315306", "SRR315307", "SRR315308", "SRR315309", "SRR315310", "SRR315311", "SRR315312", "SRR315313", "SRR315314", "SRR315315", "SRR315316", "SRR315317", "SRR315318", "SRR315319", "SRR315320", "SRR315321", "SRR315322", "SRR315323", "SRR315324", "SRR315325", "SRR315326", "SRR315327", "SRR315328", "SRR315329", "SRR315330", "SRR315331", "SRR315332", "SRR315333", "SRR315334", "SRR315335", "SRR315336", "SRR315337", "SRR317035", "SRR317036", "SRR317037", "SRR317038", "SRR317039", "SRR317040", "SRR317041", "SRR317042", "SRR317043", "SRR317044", "SRR317045", "SRR317046", "SRR317047", "SRR317048", "SRR317049", "SRR317050", "SRR317051", "SRR317052", "SRR317053", "SRR317054", "SRR317055", "SRR317056", "SRR317057", "SRR317058", "SRR317059", "SRR317060", "SRR317061", "SRR317062", "SRR317063", "SRR317064", "SRR317065", "SRR317066", "SRR317067", "SRR317068", "SRR317069", "SRR387661", "SRR387662", "SRR534289", "SRR534290", "SRR534291", "SRR534292", "SRR534293", "SRR534294", "SRR534295", "SRR534296", "SRR534297", "SRR534298", "SRR534299", "SRR534300", "SRR534301", "SRR534302", "SRR534303", "SRR534304", "SRR534305", "SRR534306", "SRR534307", "SRR534308", "SRR534309", "SRR534310", "SRR534311", "SRR534312", "SRR534313", "SRR534314", "SRR534315", "SRR534316", "SRR534317", "SRR534318", "SRR534319", "SRR534320", "SRR534321", "SRR534322", "SRR534323", "SRR534324", "SRR534325", "SRR534326", "SRR534327", "SRR534328", "SRR534329", "SRR534330", "SRR534331", "SRR534332", "SRR534333", "SRR534334", "SRR534335", "SRR089333", "SRR089335", "SRR089332", "SRR089334", "SRR089336"));
    }
}