package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.model.baseline.*;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpeciesGrouperTest {

    private static final String FACTOR_TYPE = "type";
    private static final String HOMO_SAPIENS = "Homo sapiens";
    private static final String HOMO_SAPIENS_ENSEMBL = "homo sapiens Ensembl";
    private static final String FACTOR_NAME = "name";
    private static final String EXPERIMENT_ACCESSION = "accession";
    private static final String RUN_ACCESSION1 = "run1";
    private static final String RUN_ACCESSION2 = "run2";
    public static final String ORGANISM_PART = "ORGANISM_PART";
    public static final String G1 = "g1";
    public static final String G2 = "g2";

    private BaselineExperimentBuilder subject;

    @Mock
    private FactorGroup factorGroupMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private ExperimentalFactors experimentalFactors;

    private Map<String, String> nameMap = Maps.newHashMap();

    @Mock
    private AssayGroups assayGroupsMock;

    @Mock
    private AssayGroups assayGroupsOrganismPartMock;

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineExperimentBuilder();

        nameMap.put(FACTOR_TYPE, FACTOR_NAME);

        when(assayGroupsMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup(G1, RUN_ACCESSION1), new AssayGroup(G2, RUN_ACCESSION2)).iterator());
        when(assayGroupsMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newHashSet(G1, G2));

        when(assayGroupsOrganismPartMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup(G1, RUN_ACCESSION1), new AssayGroup(G2, RUN_ACCESSION2)).iterator());
        when(assayGroupsOrganismPartMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsOrganismPartMock.getAssayGroupIds()).thenReturn(Sets.newHashSet(G1, G2));
    }

    @Test
    public void buildSingleEnsemblSpeciesGroupedByAssayGroupId() throws Exception {

        BaselineExperiment experiment = subject.forSpecies(new Species(HOMO_SAPIENS,HOMO_SAPIENS_ENSEMBL,"kingdom","ensembl_db"))
                .ofType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .withAccession(EXPERIMENT_ACCESSION)
                .withExperimentDesign(experimentDesignMock)
                .withExperimentalFactors(experimentalFactors)
                .withAssayGroups(assayGroupsMock)
                .withPubMedIds(Collections.<String>emptySet())
                .create();

        ImmutableMap<String, String> speciesGroupedByAssayGroupId = SpeciesGrouper.buildEnsemblSpeciesGroupedByAssayGroupId(experiment);

        assertThat(speciesGroupedByAssayGroupId.size(), is(2));
        assertThat(speciesGroupedByAssayGroupId, hasEntry(G1, HOMO_SAPIENS_ENSEMBL));
        assertThat(speciesGroupedByAssayGroupId, hasEntry(G2, HOMO_SAPIENS_ENSEMBL));
    }

}