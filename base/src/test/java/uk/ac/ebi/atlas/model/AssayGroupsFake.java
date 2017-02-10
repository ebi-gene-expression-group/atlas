package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentRun;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssayGroupsFake {

    public static List<AssayGroup> get() {

        ExperimentRun runMock1 = mock(ExperimentRun.class);
        ExperimentRun runMock2 = mock(ExperimentRun.class);

        FactorGroup factorGroupMock = mock(FactorGroup.class);

        String RUN_ACCESSION1 = "run1";
        String RUN_ACCESSION2 = "run2";

        when(runMock1.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock1.getAccession()).thenReturn(RUN_ACCESSION1);
        when(runMock2.getFactorGroup()).thenReturn(factorGroupMock);
        when(runMock2.getAccession()).thenReturn(RUN_ACCESSION2);

        return ImmutableList.of(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup("g2", RUN_ACCESSION2));
    }
}