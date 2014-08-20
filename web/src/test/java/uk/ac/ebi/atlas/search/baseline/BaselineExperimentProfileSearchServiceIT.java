package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentProfileSearchServiceIT {

    private static final Factor LIVER = new Factor("ORGANISM_PART", "liver");

    private static final Factor APPENDIX = new Factor("ORGANISM_PART", "appendix");
    private static final Factor ENDOMETRIUM = new Factor("ORGANISM_PART", "endometrium");
    private static final Factor BONE_MARROW = new Factor("ORGANISM_PART", "bone marrow");
    private static final Factor STOMACH = new Factor("ORGANISM_PART", "stomach");
    private static final Factor PROSTATE = new Factor("ORGANISM_PART", "prostate");


    @Inject
    private BaselineExperimentProfileSearchService subject;

    @Inject
    private RnaSeqBslnExpressionDao rnaSeqBslnExpressionDao;

    @Test
    public void singleGeneInMultipleExperiments() {
        BaselineProfilesList baselineProfilesList = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of("ENSG00000228278")));

        assertThat(baselineProfilesList, hasSize(2));

        BaselineProfile baselineProfile = baselineProfilesList.get(0);

        assertThat(baselineProfile.getId(), is("E-GEOD-30352"));
        assertThat(baselineProfile.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile.getConditions(), hasSize(1));
        assertThat(baselineProfile.getMinExpressionLevel(), is(1802D));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(1802D));
        assertThat(baselineProfile.getKnownExpressionLevel(LIVER), is(1802D));

        BaselineProfile baselineProfile2 = baselineProfilesList.get(1);
        assertThat(baselineProfile2.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile2.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile2.getConditions(), hasSize(6));
        assertThat(baselineProfile2.getMinExpressionLevel(), is(1D));
        assertThat(baselineProfile2.getMaxExpressionLevel(), is(1670D));
        assertThat(baselineProfile2.getKnownExpressionLevel(APPENDIX), is(2D));
        assertThat(baselineProfile2.getKnownExpressionLevel(BONE_MARROW), is(12D));
        assertThat(baselineProfile2.getKnownExpressionLevel(ENDOMETRIUM), is(1D));
        assertThat(baselineProfile2.getKnownExpressionLevel(LIVER), is(1670D));
        assertThat(baselineProfile2.getKnownExpressionLevel(PROSTATE), is(8D));
        assertThat(baselineProfile2.getKnownExpressionLevel(STOMACH), is(10D));
    }

    private static final String GENE_IN_CELL_LINES_EXPERIMENT = "ENSG00000258081";

    @Test
    public void onlyTissueExperimentsReturned() {
        List<RnaSeqBslnExpression> expressions = rnaSeqBslnExpressionDao.fetchNonSpecificExpression(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT));

        Matcher cellLinesExperimentExpression = hasProperty("experimentAccession", is("E-GEOD-26284"));
        assertThat(expressions,  hasItem(cellLinesExperimentExpression));

        BaselineProfilesList baselineProfilesList = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT)));

        Matcher cellLinesExperimentProfile = hasProperty("id", is("E-GEOD-26284"));
        Matcher illuminaBodyMapExperimentProfile = hasProperty("id", is("E-MTAB-513"));

        assertThat(baselineProfilesList, hasItem(illuminaBodyMapExperimentProfile));
        assertThat(baselineProfilesList, not(hasItem(cellLinesExperimentProfile)));
    }

}
