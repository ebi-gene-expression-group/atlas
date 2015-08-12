package uk.ac.ebi.atlas.experimentpage.baseline;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptionsWrapperAsGeneSets;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.annotation.Resource;
import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineProfilesHeatMapEMTab1733IT {

    public static final String E_MTAB_1733 = "E-MTAB-1733";
    public static final String ORGANISM_PART = "ORGANISM_PART";

    public static final Factor LIVER = new Factor(ORGANISM_PART, "liver");
    public static final Factor APPENDIX = new Factor(ORGANISM_PART, "appendix");
    public static final Factor ENDOMETRIUM = new Factor(ORGANISM_PART, "endometrium");
    public static final Factor STOMACH = new Factor(ORGANISM_PART, "stomach");
    public static final Factor PROSTATE = new Factor(ORGANISM_PART, "prostate");

    public static final Factor THYROID = new Factor(ORGANISM_PART, "thyroid");
    public static final Factor TESTIS = new Factor(ORGANISM_PART, "testis");
    public static final Factor SPLEEN = new Factor(ORGANISM_PART, "spleen");
    public static final Factor SMALL_INTESTINE = new Factor(ORGANISM_PART, "small intestine");
    public static final Factor SKIN = new Factor(ORGANISM_PART, "skin");
    public static final Factor SALIVARY_GLAND = new Factor(ORGANISM_PART, "salivary gland");
    public static final Factor PLACENTA = new Factor(ORGANISM_PART, "placenta");
    public static final Factor PANCREAS = new Factor(ORGANISM_PART, "pancreas");
    public static final Factor LYMPH_NODE = new Factor(ORGANISM_PART, "lymph node");
    public static final Factor LUNG = new Factor(ORGANISM_PART, "lung");
    public static final Factor KIDNEY = new Factor(ORGANISM_PART, "kidney");
    public static final Factor HEART = new Factor(ORGANISM_PART, "heart");
    public static final Factor GALL_BLADDER = new Factor(ORGANISM_PART, "gall bladder");
    public static final Factor ESOPHAGUS = new Factor(ORGANISM_PART, "esophagus");
    public static final Factor DUODENUM = new Factor(ORGANISM_PART, "duodenum");
    public static final Factor COLON = new Factor(ORGANISM_PART, "colon");
    public static final Factor CEREBRAL_CORTEX = new Factor(ORGANISM_PART, "cerebral cortex");
    public static final Factor BONE_MARROW = new Factor(ORGANISM_PART, "bone marrow");
    public static final Factor BLADDER = new Factor(ORGANISM_PART, "bladder");
    public static final Factor ANIMAL_OVARY = new Factor(ORGANISM_PART, "animal ovary");
    public static final Factor ADRENAL_GLAND = new Factor(ORGANISM_PART, "adrenal gland");
    public static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose tissue");

    @Resource(name = "baselineProfilesHeatMap")
    private BaselineProfilesHeatMap subject;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    BaselineRequestContextBuilder baselineRequestContextBuilder;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    private BaselineRequestContext baselineRequestContext;

    @Inject
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Before
    public void initRequestContext() {
        populateRequestContext();
    }

    private void populateRequestContext() {
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(E_MTAB_1733);

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        baselineRequestContext = baselineRequestContextBuilder.forExperiment(baselineExperiment)
                .withPreferences(requestPreferences)
                .build();
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1733?displayLevels=true&_specific=on&geneQuery=R-HSA-73887&geneSetMatch=true
    @Test
    public void eMTAB1733_NotSpecific_GeneSets_Averaged_Expression() throws GenesNotFoundException {
        setNotSpecific();
        setGeneQuery("R-HSA-73887");

        BaselineProfilesList profiles = subject.fetch(asGeneSet(baselineRequestContext));

        assertThat(profiles.getTotalResultCount(), is(1));
        assertThat(profiles.extractGeneNames(), contains("R-HSA-73887"));

        BaselineProfile baselineProfile0 = profiles.get(0);
        assertThat(baselineProfile0.getId(), is(nullValue()));
        assertThat(baselineProfile0.getName(), is("R-HSA-73887"));
        assertThat(baselineProfile0.getConditions(), hasSize(27));
        assertThat(baselineProfile0.getMinExpressionLevel(), CoreMatchers.is(3.0));
        assertThat(baselineProfile0.getMaxExpressionLevel(), CoreMatchers.is(30.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ADIPOSE), CoreMatchers.is(14.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ADRENAL_GLAND), CoreMatchers.is(15.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ANIMAL_OVARY), CoreMatchers.is(13.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(APPENDIX), CoreMatchers.is(27.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(BLADDER), CoreMatchers.is(24.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(BONE_MARROW), CoreMatchers.is(30.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(CEREBRAL_CORTEX), CoreMatchers.is(7.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(COLON), CoreMatchers.is(16.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(DUODENUM), CoreMatchers.is(19.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ENDOMETRIUM), CoreMatchers.is(14.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ESOPHAGUS), CoreMatchers.is(15.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(GALL_BLADDER), CoreMatchers.is(21.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(HEART), CoreMatchers.is(9.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(KIDNEY), CoreMatchers.is(14.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(LIVER), CoreMatchers.is(12.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(LUNG), CoreMatchers.is(25.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(LYMPH_NODE), CoreMatchers.is(21.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(PANCREAS), CoreMatchers.is(3.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(PLACENTA), CoreMatchers.is(18.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(PROSTATE), CoreMatchers.is(13.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SALIVARY_GLAND), CoreMatchers.is(9.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SKIN), CoreMatchers.is(13.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SMALL_INTESTINE), CoreMatchers.is(19.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SPLEEN), CoreMatchers.is(19.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(STOMACH), CoreMatchers.is(13.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(TESTIS), CoreMatchers.is(12.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(THYROID), CoreMatchers.is(12.0));
    }

    private BaselineProfileStreamOptions asGeneSet(BaselineRequestContext baselineRequestContext) throws GenesNotFoundException {
        loadGeneIdsIntoRequestContext.load(baselineRequestContext, baselineRequestContext.getFilteredBySpecies());
        return new BaselineProfileStreamOptionsWrapperAsGeneSets(baselineRequestContext);
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(GeneQuery.create(geneQuery));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
