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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.sdrf.node.ScanNode;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RnaSeqExpDesignMageTabParserIT {

    private static final String EXPERIMENT_ACCESSION_E_MTAB_513 = "E-MTAB-513";

    private static final String EXPERIMENT_ACCESSION_E_GEOD_26284 = "E-GEOD-26284";

    @Inject
    private RnaSeqExpDesignMageTabParser subject;

    @Test
    public void testExtractFactors513() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_513);
        assertThat(subject.extractFactors(), containsInAnyOrder("organism part"));
    }

    @Test
    public void testExtractRunAccessions513() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_513);
        assertThat(subject.extractRunAccessions(), containsInAnyOrder("ERR030872", "ERR030873", "ERR030874", "ERR030875", "ERR030876", "ERR030877", "ERR030878", "ERR030879", "ERR030888", "ERR030889", "ERR030890", "ERR030891", "ERR030892", "ERR030893", "ERR030894", "ERR030895", "ERR030856", "ERR030857", "ERR030858", "ERR030859", "ERR030860", "ERR030861", "ERR030862", "ERR030863", "ERR030880", "ERR030881", "ERR030882", "ERR030883", "ERR030884", "ERR030885", "ERR030886", "ERR030887", "ERR030896", "ERR030897", "ERR030898", "ERR030899", "ERR030900", "ERR030901", "ERR030902", "ERR030903", "ERR030864", "ERR030865", "ERR030866", "ERR030867", "ERR030868", "ERR030869", "ERR030870", "ERR030871"));
    }

    @Test
    public void testGetScanNodeForRunAccession513() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_513);
        assertThat(subject.getScanNodeForRunAccession("ERR030872"), is(not(nullValue())));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetScanNodeForNonExistingRun513() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_513);
        subject.getScanNodeForRunAccession("ERR123456");
    }

    @Test
    public void testFindCharacteristicValueForScanNode513() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_513);

        // ERR030872	Homo sapiens	60	female	Human thyroid total RNA, lot 0908003	thyroid
        ScanNode scanNode = subject.getScanNodeForRunAccession("ERR030872");
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "Organism"), hasItem("Homo sapiens"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "age"), hasItem("60"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "sex"), hasItem("female"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "organism part"), hasItem("thyroid"));

        // ERR030902	Homo sapiens	19	male	Human testes total RNA, lot 05060392	testes
        scanNode = subject.getScanNodeForRunAccession("ERR030902");
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "Organism"), hasItem("Homo sapiens"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "age"), hasItem("19"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "sex"), hasItem("male"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "organism part"), hasItem("testis"));

        // ERR030871	Homo sapiens	  	  	  	16 Tissues mixture
        scanNode = subject.getScanNodeForRunAccession("ERR030871");
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "Organism"), hasItem("Homo sapiens"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "organism part"), hasItem("16 tissues mixture"));
    }

    @Test
    public void testFindFactorValueForScanNode513() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_MTAB_513);

        // ERR030872	Homo sapiens	60	female	Human thyroid total RNA, lot 0908003	thyroid
        ScanNode scanNode = subject.getScanNodeForRunAccession("ERR030872");
        assertThat(subject.findFactorValueForScanNode(scanNode, "organism part"), hasItem("thyroid"));

        // ERR030902	Homo sapiens	19	male	Human testes total RNA, lot 05060392	testes
        scanNode = subject.getScanNodeForRunAccession("ERR030902");
        assertThat(subject.findFactorValueForScanNode(scanNode, "organism part"), hasItem("testis"));

        // ERR030871	Homo sapiens	  	  	  	16 Tissues mixture
        scanNode = subject.getScanNodeForRunAccession("ERR030871");
        assertThat(subject.findFactorValueForScanNode(scanNode, "organism part"), hasItem("16 tissues mixture"));
    }

    @Test
    public void testExtractFactors26284() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_26284);
        assertThat(subject.extractFactors(), containsInAnyOrder("RNA", "cell line", "cellular component"));
    }

    @Test
    public void testExtractRunAccessions26284() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_26284);
        assertThat(subject.extractRunAccessions(), containsInAnyOrder("SRR387660", "SRR387664", "SRR387659", "SRR387663", "SRR307897", "SRR307898", "SRR307899", "SRR307900", "SRR307901", "SRR307902", "SRR307903", "SRR307904", "SRR307905", "SRR307906", "SRR307907", "SRR307908", "SRR307909", "SRR307910", "SRR307911", "SRR307912", "SRR307913", "SRR307914", "SRR307915", "SRR307916", "SRR307917", "SRR307918", "SRR307919", "SRR307920", "SRR307921", "SRR307922", "SRR307923", "SRR307924", "SRR307925", "SRR307926", "SRR307927", "SRR307928", "SRR307929", "SRR307930", "SRR307931", "SRR307932", "SRR307933", "SRR315297", "SRR315298", "SRR315299", "SRR315300", "SRR315301", "SRR315302", "SRR315303", "SRR315304", "SRR315305", "SRR315306", "SRR315307", "SRR315308", "SRR315309", "SRR315310", "SRR315311", "SRR315312", "SRR315313", "SRR315314", "SRR315315", "SRR315316", "SRR315317", "SRR315318", "SRR315319", "SRR315320", "SRR315321", "SRR315322", "SRR315323", "SRR315324", "SRR315325", "SRR315326", "SRR315327", "SRR315328", "SRR315329", "SRR315330", "SRR315331", "SRR315332", "SRR315333", "SRR315334", "SRR315335", "SRR315336", "SRR315337", "SRR317035", "SRR317036", "SRR317037", "SRR317038", "SRR317039", "SRR317040", "SRR317041", "SRR317042", "SRR317043", "SRR317044", "SRR317045", "SRR317046", "SRR317047", "SRR317048", "SRR317049", "SRR317050", "SRR317051", "SRR317052", "SRR317053", "SRR317054", "SRR317055", "SRR317056", "SRR317057", "SRR317058", "SRR317059", "SRR317060", "SRR317061", "SRR317062", "SRR317063", "SRR317064", "SRR317065", "SRR317066", "SRR317067", "SRR317068", "SRR317069", "SRR387661", "SRR387662", "SRR534289", "SRR534290", "SRR534291", "SRR534292", "SRR534293", "SRR534294", "SRR534295", "SRR534296", "SRR534297", "SRR534298", "SRR534299", "SRR534300", "SRR534301", "SRR534302", "SRR534303", "SRR534304", "SRR534305", "SRR534306", "SRR534307", "SRR534308", "SRR534309", "SRR534310", "SRR534311", "SRR534312", "SRR534313", "SRR534314", "SRR534315", "SRR534316", "SRR534317", "SRR534318", "SRR534319", "SRR534320", "SRR534321", "SRR534322", "SRR534323", "SRR534324", "SRR534325", "SRR534326", "SRR534327", "SRR534328", "SRR534329", "SRR534330", "SRR534331", "SRR534332", "SRR534333", "SRR534334", "SRR534335", "SRR089333", "SRR089335", "SRR089332", "SRR089334", "SRR089336"));
    }

    @Test
    public void testGetScanNodeForRunAccession26284() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_26284);
        assertThat(subject.getScanNodeForRunAccession("SRR387660"), is(not(nullValue())));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetScanNodeForNonExistingRun26284() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_26284);
        subject.getScanNodeForRunAccession("SRR123456");
    }

    @Test
    public void testFindCharacteristicValueForScanNode26284() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_26284);

        // SRR307901	Homo sapiens	  	AG445	whole cell	lung	fibroblast		  	Coriell	AG445	whole cell	long polyA RNA
        ScanNode scanNode = subject.getScanNodeForRunAccession("SRR307901");
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "Organism"), hasItem("Homo sapiens"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "sex"), is(emptyCollectionOf(String.class)));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "cell line"), hasItem("AG445"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "cellular component"), hasItem("whole cell"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "organism part"), hasItem("lung"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "cell type"), hasItem("fibroblast"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "biosource provider"), hasItem("Coriell"));

        // SRR089336	Homo sapiens	female	K562	cytosol			leukemia	cancer	ATCC	K562	cytosol	long polyA RNA
        scanNode = subject.getScanNodeForRunAccession("SRR089336");
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "Organism"), hasItem("Homo sapiens"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "sex"), hasItem("female"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "cell line"), hasItem("K562"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "cellular component"), hasItem("cytosol"));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "organism part"), is(emptyCollectionOf(String.class)));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "cell type"), is(emptyCollectionOf(String.class)));
        assertThat(subject.findCharacteristicValueForSDRFNode(scanNode, "biosource provider"), hasItem("ATCC"));
    }

    @Test
    public void testFindFactorValueForScanNode26284() throws Exception {
        subject.init(EXPERIMENT_ACCESSION_E_GEOD_26284);

        // SRR307901	Homo sapiens	  	AG445	whole cell	lung	fibroblast		  	Coriell	AG445	whole cell	long polyA RNA
        ScanNode scanNode = subject.getScanNodeForRunAccession("SRR307901");
        assertThat(subject.findFactorValueForScanNode(scanNode, "cell line"), hasItem("AG445"));
        assertThat(subject.findFactorValueForScanNode(scanNode, "cellular component"), hasItem("whole cell"));
        assertThat(subject.findFactorValueForScanNode(scanNode, "RNA"), hasItem("long polyA RNA"));

        // SRR089336	Homo sapiens	female	K562	cytosol			leukemia	cancer	ATCC	K562	cytosol	long polyA RNA
        scanNode = subject.getScanNodeForRunAccession("SRR089336");
        assertThat(subject.findFactorValueForScanNode(scanNode, "cell line"), hasItem("K562"));
        assertThat(subject.findFactorValueForScanNode(scanNode, "cellular component"), hasItem("cytosol"));
        assertThat(subject.findFactorValueForScanNode(scanNode, "RNA"), hasItem("long polyA RNA"));
    }
}