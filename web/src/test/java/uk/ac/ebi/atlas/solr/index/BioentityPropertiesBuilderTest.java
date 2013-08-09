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

import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BioentityPropertiesBuilderTest {

    private static final List<String> DESIGN_ELEMENT_NAMES = Lists.newArrayList(
            "design_element");

    private static final String BIOENTITY_IDENTIFIER = "AGAP000002";

    private static final List<String> DESIGN_ELEMENT_VALUES = Lists.newArrayList(
            "MAPPING1");

    private static final List<String> DESIGN_ELEMENT_EMPTY_VALUE = Lists.newArrayList(
            "");

    private static final List<String> GENE_PROPERTY_NAMES = Lists.newArrayList(
            "description", "ensprotein", "enstranscript", "entrezgene", "go", "goterm", "interpro", "interproterm", "refseq", "symbol", "unigene", "uniprot", "synonym"
    );

    private static final List<String> GENE_PROPERTY_VALUES = Lists.newArrayList(
            "WW domain-containing protein [Source:VB Community Annotation;Acc:AGAP000002]", "AGAP000002-PA",
            "AGAP000002-RA", "1272272@@4576126@@5666737", "GO:0003674@@GO:0005515", "molecular_function@@protein binding",
            "IPR001202@@IPR008973", "C2 calcium/lipid-binding domain, CaLB@@WW/Rsp5/WWP", "XP_001237378@@XP_001687751@@XP_311158",
            "KIBRLG", "Aga.19012@@Aga.29661"
    );

    private static final String SPECIES = "anopheles_gambiae";
    private static final String BIOENTITY_TYPE = "gene";

    private BioentityPropertiesBuilder subject;

    @Test
    public void shouldBuildASinglePropertyForEachDesignElementMapping() throws IOException {
        subject = new BioentityPropertiesBuilder();
        //when
        List<BioentityProperty> bioentityProperties = subject
                .forPropertyNames(DESIGN_ELEMENT_NAMES)
                .forBioentityType(BIOENTITY_TYPE)
                .forSpecies(SPECIES)
                .withBioentityIdentifier(BIOENTITY_IDENTIFIER)
                .withPropertyValues(DESIGN_ELEMENT_VALUES)
                .build();
        //then
        BioentityProperty bioentityProperty = new BioentityProperty(BIOENTITY_IDENTIFIER, BIOENTITY_TYPE, SPECIES, DESIGN_ELEMENT_NAMES.get(0), DESIGN_ELEMENT_VALUES.get(0));
        assertThat(bioentityProperties, hasSize(1));
        assertThat(bioentityProperties.get(0), is(bioentityProperty));
    }

    @Test
    public void shouldBuildAnEmptyList() throws IOException {
        subject = new BioentityPropertiesBuilder();
        //when
        List<BioentityProperty> bioentityProperties = subject
                .forPropertyNames(DESIGN_ELEMENT_NAMES)
                .forBioentityType(BIOENTITY_TYPE)
                .forSpecies(SPECIES)
                .withBioentityIdentifier(BIOENTITY_IDENTIFIER)
                .withPropertyValues(DESIGN_ELEMENT_EMPTY_VALUE)
                .build();
        //then
        assertThat(bioentityProperties, hasSize(0));
    }

    @Test
    public void shouldBuildPropertiesSplittingValuesOnSeparator() throws Exception {
        subject = new BioentityPropertiesBuilder();
        //when
        List<BioentityProperty> bioentityProperties = subject
                .forPropertyNames(GENE_PROPERTY_NAMES)
                .forBioentityType(BIOENTITY_TYPE)
                .forSpecies(SPECIES)
                .withBioentityIdentifier(BIOENTITY_IDENTIFIER)
                .withPropertyValues(GENE_PROPERTY_VALUES)
                .build();
        //then
        assertThat(bioentityProperties, hasSize(21));
    }
}
