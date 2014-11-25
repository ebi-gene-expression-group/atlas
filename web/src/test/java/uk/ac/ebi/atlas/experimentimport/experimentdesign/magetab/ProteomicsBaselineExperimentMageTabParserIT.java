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

import com.google.common.base.Joiner;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.utils.OntologyTermUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ProteomicsBaselineExperimentMageTabParserIT {

    private static final String E_PROT_1 = "E-PROT-1";
    private static final String DEVELOPMENTAL_STAGE = "developmental stage";
    private static final String ORGANISM_PART = "organism part";
    private static final String ORGANISM = "organism";

    @Inject
    private ProteomicsBaselineExperimentMageTabParser subject;

    @Test
    public void experimentDesign() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(E_PROT_1).getExperimentDesign();

        assertThat(experimentDesign.getFactorHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM_PART));
        assertThat(experimentDesign.getSampleHeaders(), contains(DEVELOPMENTAL_STAGE, ORGANISM, ORGANISM_PART));
        Iterator<SampleCharacteristic> sampleCharacteristicIterator = experimentDesign.getSampleCharacteristics("Adult_Ovary").iterator();

        SampleCharacteristic sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), Matchers.is(ORGANISM_PART));
        assertThat(sampleCharacteristic.value(), Matchers.is("ovary"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), Matchers.is("http://www.ebi.ac.uk/efo/EFO_0000973"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), Matchers.is(ORGANISM));
        assertThat(sampleCharacteristic.value(), Matchers.is("Homo sapiens"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), Matchers.is("http://purl.obolibrary.org/obo/NCBITaxon_9606"));

        sampleCharacteristic = sampleCharacteristicIterator.next();
        assertThat(sampleCharacteristic.header(), Matchers.is(DEVELOPMENTAL_STAGE));
        assertThat(sampleCharacteristic.value(), Matchers.is("adult"));
        assertThat(OntologyTermUtils.joinURIs(sampleCharacteristic.valueOntologyTerms()), Matchers.is("http://www.ebi.ac.uk/efo/EFO_0001272"));

        assertThat(experimentDesign.asTableData(), hasSize(30));
        assertThat(experimentDesign.asTableData(), contains(
                arrayContaining("Adult_Adrenalgland", "adult", "Homo sapiens", "adrenal gland", "adult", "adrenal gland"),
                arrayContaining("Adult_Bcells", "adult", "Homo sapiens", "B cell", "adult", "B cell"),
                arrayContaining("Adult_CD4Tcells", "adult", "Homo sapiens", "CD4-positive T cell", "adult", "CD4-positive T cell"),
                arrayContaining("Adult_CD8Tcells", "adult", "Homo sapiens", "CD8-positive T cell", "adult", "CD8-positive T cell"),
                arrayContaining("Adult_Colon", "adult", "Homo sapiens", "colon", "adult", "colon"),
                arrayContaining("Adult_Esophagus", "adult", "Homo sapiens", "esophagus", "adult", "esophagus"),
                arrayContaining("Adult_Frontalcortex", "adult", "Homo sapiens", "frontal cortex", "adult", "frontal cortex"),
                arrayContaining("Adult_Gallbladder", "adult", "Homo sapiens", "gallbladder", "adult", "gallbladder"),
                arrayContaining("Adult_Heart", "adult", "Homo sapiens", "heart", "adult", "heart"),
                arrayContaining("Adult_Kidney", "adult", "Homo sapiens", "kidney", "adult", "kidney"),
                arrayContaining("Adult_Liver", "adult", "Homo sapiens", "liver", "adult", "liver"),
                arrayContaining("Adult_Lung", "adult", "Homo sapiens", "lung", "adult", "lung"),
                arrayContaining("Adult_Monocytes", "adult", "Homo sapiens", "monocyte", "adult", "monocyte"),
                arrayContaining("Adult_NKcells", "adult", "Homo sapiens", "natural killer cell", "adult", "natural killer cell"),
                arrayContaining("Adult_Ovary", "adult", "Homo sapiens", "ovary", "adult", "ovary"),
                arrayContaining("Adult_Pancreas", "adult", "Homo sapiens", "pancreas", "adult", "pancreas"),
                arrayContaining("Adult_Platelets", "adult", "Homo sapiens", "platelet", "adult", "platelet"),
                arrayContaining("Adult_Prostate", "adult", "Homo sapiens", "prostate", "adult", "prostate"),
                arrayContaining("Adult_Rectum", "adult", "Homo sapiens", "rectum", "adult", "rectum"),
                arrayContaining("Adult_Retina", "adult", "Homo sapiens", "retina", "adult", "retina"),
                arrayContaining("Adult_Spinalcord", "adult", "Homo sapiens", "spinal cord", "adult", "spinal cord"),
                arrayContaining("Adult_Testis", "adult", "Homo sapiens", "testis", "adult", "testis"),
                arrayContaining("Adult_Urinarybladder", "adult", "Homo sapiens", "urinary bladder", "adult", "urinary bladder"),
                arrayContaining("Fetal_Brain", "fetus", "Homo sapiens", "brain", "fetus", "brain"),
                arrayContaining("Fetal_Gut", "fetus", "Homo sapiens", "gut", "fetus", "gut"),
                arrayContaining("Fetal_Heart", "fetus", "Homo sapiens", "heart", "fetus", "heart"),
                arrayContaining("Fetal_Liver", "fetus", "Homo sapiens", "liver", "fetus", "liver"),
                arrayContaining("Fetal_Ovary", "fetus", "Homo sapiens", "ovary", "fetus", "ovary"),
                arrayContaining("Fetal_Placenta", "fetus", "Homo sapiens", "placenta", "fetus", "placenta"),
                arrayContaining("Fetal_Testis", "fetus", "Homo sapiens", "testis", "fetus", "testis"))
        );
    }
}