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

package uk.ac.ebi.atlas.solr.index;

import org.junit.Test;

import java.io.IOException;

public class BioentityPropertiesBuilderTest {

    private static final String[] DESIGN_ELEMENT_HEADERS = new String[]{
            "gene", "design_element"};


    private static final String[] DESIGN_ELEMENT_VALUES = new String[]{
            "ID1", "MAPPING1"};

    private static final String[] GENE_PROPERTY_HEADERS = new String[]{
            "gene", "description", "ensprotein", "enstranscript", "entrezgene", "go", "goterm", "interpro", "interproterm", "refseq", "symbol", "unigene", "uniprot", "synonym"
    };

    private static final String[] GENE_PROPERTY_VALUES = new String[]{
            "AGAP000002", "WW domain-containing protein [Source:VB Community Annotation;Acc:AGAP000002]", "AGAP000002-PA",
            "AGAP000002-RA", "1272272@@4576126@@5666737", "GO:0003674@@GO:0005515", "molecular_function@@protein binding",
            "IPR001202@@IPR008973", "C2 calcium/lipid-binding domain, CaLB@@WW/Rsp5/WWP", "XP_001237378@@XP_001687751@@XP_311158",
            "KIBRLG", "Aga.19012@@Aga.29661"
    };

    private static final String SPECIES = "anopheles_gambiae";
    private static final String BIOENTITY_TYPE = "gene";

//    private BioentityPropertiesBuilder subject;


    @Test
    public void shouldBuildASinglePropertyForEachDesignElementMapping() throws IOException {
//        subject = new BioentityPropertiesStream(csvReaderMock, bioentityPropertiesBuilderMock, SPECIES);
//        //when
//        List<BioentityProperty> bioentityProperties = subject.buildBioentityProperties(DESIGN_ELEMENT_VALUES);
//        //then
//        BioentityProperty bioentityProperty = new BioentityProperty("ID1", "gene", SPECIES, "design_element", "MAPPING1");
//        assertThat(bioentityProperties, hasItems(bioentityProperty));
    }


    @Test
    public void shouldBuildPropertiesSplittingValuesOnSeparator() throws Exception {
//        given(csvReaderMock.readNext()).willReturn(GENE_PROPERTY_HEADERS);
//        subject = new BioentityPropertiesStream(csvReaderMock, bioentityPropertiesBuilderMock, SPECIES);
//        List<BioentityProperty> bioentityProperties = subject.buildBioentityProperties(GENE_PROPERTY_VALUES);
//        assertThat(bioentityProperties, hasSize(20));
    }
}
