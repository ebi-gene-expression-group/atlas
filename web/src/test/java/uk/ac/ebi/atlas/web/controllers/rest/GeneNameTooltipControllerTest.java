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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneNameTooltipControllerTest {

    public static final String IDENTIFIER = "identifier";
    public static final String GENE_NAME = "geneName";
    public static final String GOTERM = "goterm";
    public static final String INTERPROTERM = "interproterm";
    public static final String SYNONYM = "synonym";
    public static final String SYNONYM_1 = "synonym1";
    public static final String SYNONYM_2 = "synonym2";
    private GeneNameTooltipController subject;

    @Mock
    private BioEntityPropertyDao propertyDaoMock;

    @Mock
    private Resource htmlTemplateResourceMock;

    @Mock
    private InputStream inputStreamMock;

    @Before
    public void setUp() throws Exception {
        subject = new GeneNameTooltipController(propertyDaoMock, htmlTemplateResourceMock);

        InputStream inputStream = new ByteArrayInputStream("{0} {1} {2} {3}".getBytes());

        when(htmlTemplateResourceMock.getInputStream()).thenReturn(inputStream);
    }

    @Test
    public void testInitTemplate() throws Exception {
        subject.initTemplate();

        verify(htmlTemplateResourceMock).getInputStream();
    }

    @Test
    public void testGetTooltipContent() throws Exception {

        SortedSetMultimap<String, String> hashMultimap = TreeMultimap.create();
        hashMultimap.put(GOTERM, GOTERM);
        hashMultimap.put(INTERPROTERM, INTERPROTERM);
        hashMultimap.put(SYNONYM, SYNONYM_1);
        hashMultimap.put(SYNONYM, SYNONYM_2);

        when(propertyDaoMock.fetchTooltipProperties(IDENTIFIER)).thenReturn(hashMultimap);
        subject.initTemplate();

        String tooltipContent = subject.getTooltipContent(GENE_NAME, IDENTIFIER);
        assertThat(tooltipContent, containsString(GENE_NAME));
        assertThat(tooltipContent, containsString(GOTERM));
        assertThat(tooltipContent, containsString(SYNONYM_1));
        assertThat(tooltipContent, containsString(SYNONYM_2));

    }

    @Test
    public void buildSynonyms() throws Exception {

        Multimap<String, String> hashMultimap = HashMultimap.create();
        hashMultimap.put(SYNONYM, "a");
        hashMultimap.put(SYNONYM, "b");

        String identifier = subject.buildSynonyms(hashMultimap);
        assertThat(identifier, is("<span class='gxaPropertyValueMarkup'>b</span> <span class='gxaPropertyValueMarkup'>a</span>"));
    }

    @Test
    public void formatShouldWrapValuesInSpanElements() throws Exception {

        Collection<String> values = Lists.newArrayList("VALUE_1", "VALUE_2");

        String formattedValues = subject.format(values, true, 20);

        assertThat(formattedValues, is(
                "<span class='gxaPropertyValueMarkup'>VALUE_1</span>" +
                        " " +
                        "<span class='gxaPropertyValueMarkup'>VALUE_2</span>"));
    }

    @Test
    public void formatCanBeConfiguredToReturnEmptyStringWhenValuesIsEmpty() throws Exception {

        Collection<String> values = Lists.newArrayList();

        String formattedValues = subject.format(values, false, 20);

        assertThat(formattedValues, is(""));
    }

    @Test
    public void formatCanBeConfiguredToReturnNAWhenValuesIsEmpty() throws Exception {

        Collection<String> values = Lists.newArrayList();

        String formattedValues = subject.format(values, true, 20);

        assertThat(formattedValues, is("NA"));
    }

    @Test
    public void testLengthRestrictionForListOfTerms() throws Exception {

        Collection<String> values = Lists.newArrayList("VALUE_1", "VALUE_2");

        String formattedValues = subject.format(values, true, 1);

        assertThat(formattedValues, is(
                "<span class='gxaPropertyValueMarkup'>VALUE_1</span> <span class='gxaPropertyValueMarkup'>(...and 2 more)</span>"));

    }

}
