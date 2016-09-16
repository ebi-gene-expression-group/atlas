
package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.*;
import com.google.gson.JsonObject;
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
    public void testGetTooltipContentJson() throws Exception {

        SortedSetMultimap<String, String> hashMultimap = TreeMultimap.create();
        hashMultimap.put(GOTERM, GOTERM);
        hashMultimap.put(INTERPROTERM, INTERPROTERM);
        hashMultimap.put(SYNONYM, SYNONYM_1);
        hashMultimap.put(SYNONYM, SYNONYM_2);

        when(propertyDaoMock.fetchTooltipProperties(IDENTIFIER)).thenReturn(hashMultimap);

        JsonObject tooltipContent = subject.getTooltipContentJsonObject(IDENTIFIER);
        assertThat(tooltipContent.get("goterms").getAsJsonArray().get(0).getAsString(), is(GOTERM));
        assertThat(tooltipContent.get("interproterms").getAsJsonArray().get(0).getAsString(), is(INTERPROTERM));
        assertThat(tooltipContent.get("synonyms").getAsJsonArray().size(), is(2));
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
