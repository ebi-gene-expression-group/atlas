
package uk.ac.ebi.atlas.controllers.rest;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

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


        Map<BioentityPropertyName, Set<String>> m = ImmutableMap.of(
                BioentityPropertyName.GO, (Set<String>) ImmutableSet.of("go"),
                BioentityPropertyName.INTERPROTERM, ImmutableSet.of("interproterm"),
                BioentityPropertyName.SYNONYM, ImmutableSet.of(SYNONYM_1, SYNONYM_2)
        );


        when(propertyDaoMock.fetchTooltipProperties(IDENTIFIER)).thenReturn(m);
    }

    @Test
    public void testInitTemplate() throws Exception {
        subject.initTemplate();

        verify(htmlTemplateResourceMock).getInputStream();
    }

    @Test
    public void testGetTooltipContent() throws Exception {

        subject.initTemplate();

        String tooltipContent = subject.getTooltipContent(GENE_NAME, IDENTIFIER);
        assertThat(tooltipContent, containsString(GENE_NAME));
        assertThat(tooltipContent, containsString(GOTERM));
        assertThat(tooltipContent, containsString(SYNONYM_1));
        assertThat(tooltipContent, containsString(SYNONYM_2));

    }
    @Test
    public void testGetTooltipContentJson() throws Exception {

        JsonObject tooltipContent = subject.getTooltipContentJsonObject(IDENTIFIER);
        assertThat(tooltipContent.get("goterms").getAsJsonArray().get(0).getAsString(), is(GOTERM));
        assertThat(tooltipContent.get("interproterms").getAsJsonArray().get(0).getAsString(), is(INTERPROTERM));
        assertThat(tooltipContent.get("synonyms").getAsJsonArray().size(), is(2));
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
