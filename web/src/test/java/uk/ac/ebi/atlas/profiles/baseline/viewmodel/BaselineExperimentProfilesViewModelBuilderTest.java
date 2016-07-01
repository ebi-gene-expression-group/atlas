package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import autovalue.shaded.com.google.common.common.collect.Lists;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSlice;
import uk.ac.ebi.atlas.utils.ColourGradient;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentProfilesViewModelBuilderTest {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose", OntologyTerm.create("ontologyTerm"));
    private static final Factor ADRENAL = new Factor(ORGANISM_PART, "adrenal");
    private static final Factor BRAIN = new Factor(ORGANISM_PART, "brain");
    private static final Factor BREAST = new Factor(ORGANISM_PART, "breast");

    private Color startColour = Color.LIGHT_GRAY;
    private Color endColour = Color.BLUE;
    private Color blankColour = Color.WHITE;
    private double colourScale = 1;
    private ColourGradient colorGradient = new ColourGradient(startColour, endColour, blankColour, colourScale);
    private BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder = new BaselineExpressionViewModelBuilder(colorGradient);
    private BaselineExperimentProfilesViewModelBuilder subject = new BaselineExperimentProfilesViewModelBuilder(baselineExpressionViewModelBuilder);
    private List<Factor> orderedFactors = ImmutableList.of(ADIPOSE, ADRENAL, BRAIN, BREAST);
    private List<Factor> reversedFactors = ImmutableList.of(BREAST,BRAIN,ADRENAL,ADIPOSE);

    private static final FactorGroup EMPTY_FACTOR_SET = new FactorSet();

    @Mock
    private BaselineExperiment experiment1;
    private FactorGroup experiment1FilterFactors = new FactorSet(new Factor("ORGANISM", "Homo sapiens"));
    private SortedSet<Factor> experiment1nonFilterFactors = ImmutableSortedSet.of(ADIPOSE, BRAIN);
    private SortedSet<Factor> nonFilterFactors_EMPTY = ImmutableSortedSet.of();

    @Mock
    private BaselineExperiment experiment2;

    private BaselineExperimentProfile profile1;
    private BaselineExperimentProfile profile2;
    private Collection<BaselineExperimentProfile> profiles;

    @Mock
    private BaselineExperimentSlice experimentSlice1;

    @Mock
    private BaselineExperimentSlice experimentSlice2;

    @Before
    public void before() {
        when(experimentSlice1.experiment()).thenReturn(experiment1);
        when(experimentSlice1.filterFactors()).thenReturn(experiment1FilterFactors);
        when(experimentSlice1.nonFilterFactors()).thenReturn(experiment1nonFilterFactors);
        when(experimentSlice1.experimentAccession()).thenReturn("EXP1");
        when(experimentSlice1.experimentDisplayName()).thenReturn("EXP1NAME");
        when(experimentSlice1.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        when(experimentSlice2.experiment()).thenReturn(experiment2);
        when(experimentSlice2.filterFactors()).thenReturn(EMPTY_FACTOR_SET);
        when(experimentSlice2.nonFilterFactors()).thenReturn(nonFilterFactors_EMPTY);
        when(experimentSlice2.experimentAccession()).thenReturn("EXP2");
        when(experimentSlice2.experimentDisplayName()).thenReturn("EXP2NAME");
        when(experimentSlice2.getExperimentType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        profile1 = new BaselineExperimentProfile(experimentSlice1);
        profile2 = new BaselineExperimentProfile(experimentSlice2);
        profiles = ImmutableList.of(profile1, profile2);

    }

    @Test
    public void buildProfilesViewModel() {

        JsonElement profiles = subject.buildJson(new BaselineExperimentProfilesList(ImmutableList.of(profile1, profile2)), orderedFactors);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(profiles);

        String expected = "{\n"+
                "  \"searchResultTotal\": 0,\n" +
                "  \"rows\": [\n" +
                "    {\n" +
                "      \"id\": \"EXP1\",\n" +
                "      \"name\": \"EXP1NAME\",\n" +
                "      \"experimentType\": \"RNASEQ_MRNA_BASELINE\",\n" +
                "      \"expressions\": [\n" +
                "        {\n" +
                "          \"factorName\": \"adipose\",\n" +
                "          \"color\": \"\",\n" +
                "          \"svgPathId\": \"ontologyTerm\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"adrenal\",\n" +
                "          \"color\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"brain\",\n" +
                "          \"color\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"breast\",\n" +
                "          \"color\": \"\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"serializedFilterFactors\": \"ORGANISM:Homo sapiens\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"EXP2\",\n" +
                "      \"name\": \"EXP2NAME\",\n" +
                "      \"experimentType\": \"RNASEQ_MRNA_BASELINE\",\n" +
                "      \"expressions\": [\n" +
                "        {\n" +
                "          \"factorName\": \"adipose\",\n" +
                "          \"color\": \"\",\n" +
                "          \"svgPathId\": \"ontologyTerm\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"adrenal\",\n" +
                "          \"color\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"brain\",\n" +
                "          \"color\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"breast\",\n" +
                "          \"color\": \"\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"serializedFilterFactors\": \"\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        assertThat(json, is(expected));
    }

    @Test
    public void expressionsHaveTheOrderDefinedByFilterFactors() {

        List<List<String>> resultOrdered = expressionsFromResult(subject.buildJson(new BaselineExperimentProfilesList
                (profiles),
                orderedFactors));

        List<List<String>> resultReversed = expressionsFromResult(subject.buildJson(new
                BaselineExperimentProfilesList(profiles),reversedFactors));

        List<String> orderExpected = new ArrayList<>();
        for(Factor f: orderedFactors){
            orderExpected.add(f.getValue());
        }

        for(List<String> ordered: resultOrdered){
            assertEquals(orderExpected, ordered);
        }

        for(List<String> reversed: resultReversed){
            assertEquals(orderExpected, Lists.reverse(reversed));
        }

    }

    private List<List<String>> expressionsFromResult(JsonElement result){
        List<List<String>> rowsInExpressions = new ArrayList<>();
        for(JsonElement e : result.getAsJsonObject().get("rows").getAsJsonArray()){
            List<String> expressionsForThisRow = new ArrayList<>();
            for(JsonElement f: e.getAsJsonObject().get("expressions").getAsJsonArray()){
                expressionsForThisRow.add(f.getAsJsonObject().get("factorName").getAsString());
            }
            rowsInExpressions.add(expressionsForThisRow);
        }
        return rowsInExpressions;
    }


}