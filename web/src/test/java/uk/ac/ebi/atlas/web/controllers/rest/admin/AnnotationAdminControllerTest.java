///*
// * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *
// *
// * For further details of the Gene Expression Atlas project, including source code,
// * downloads and documentation, please see:
// *
// * http://gxa.github.com/gxa
// */
//
//package uk.ac.ebi.atlas.web.controllers.rest.admin;
//
//import com.google.common.collect.Sets;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//import uk.ac.ebi.atlas.commands.GeneNamesImportCommand;
//import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
//
//
//import java.util.Set;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.verify;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AnnotationAdminControllerTest {
//
//    public static final String ARRAY_DESIGN = "arrayDesign";
//    public static final String SPECIES = "species";
//    public static final String UPDATED = "Updated";
//
//    public static final ArrayDesignType ARRAY_DESIGN_TYPE = ArrayDesignType.MICRO_ARRAY;
//
//    AnnotationAdminController subject;
//
//    @Mock
//    private GeneNamesImportCommand geneNamesImportCommandMock;
//
//
//    @Before
//    public void setUp() throws Exception {
//        subject = new AnnotationAdminController();
//    }
//
//    @Test
//    public void testLoadGeneNames() throws Exception {
//        Set<String> set = Sets.newHashSet(SPECIES);
//
//        String result = subject.loadGeneNames(set);
//        assertThat(result, is(UPDATED));
//        verify(geneNamesImportCommandMock).loadGeneNames(Mockito.eq(set));
//    }
//
//    @Test
//    public void testLoadDesignElements() throws Exception {
//
//        String result = subject.loadDesignElements(ARRAY_DESIGN, ARRAY_DESIGN_TYPE.getName());
//        assertThat(result, is(UPDATED));
//        verify(designElementLoaderMock).loadMappings(ARRAY_DESIGN, ARRAY_DESIGN_TYPE);
//    }
//
//}