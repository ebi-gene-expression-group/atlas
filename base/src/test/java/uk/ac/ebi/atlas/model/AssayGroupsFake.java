package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.mockito.Mockito;
import uk.ac.ebi.atlas.model.baseline.ExperimentRun;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssayGroupsFake {

    public static AssayGroups get() {
        AssayGroups assayGroupsMock = Mockito.mock(AssayGroups.class);

        ExperimentRun runMock1 = mock(ExperimentRun.class);
        ExperimentRun runMock2 = mock(ExperimentRun.class);

        FactorGroup factorGroupMock = mock(FactorGroup.class);

        String RUN_ACCESSION1 = "run1";
        String RUN_ACCESSION2 = "run2";

        when(runMock1.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock1.getAccession()).thenReturn(RUN_ACCESSION1);
        when(runMock2.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock2.getAccession()).thenReturn(RUN_ACCESSION2);

        when(assayGroupsMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup("g2", RUN_ACCESSION2)).iterator());
        when(assayGroupsMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newHashSet("g1", "g2"));
        return assayGroupsMock;
    }
}