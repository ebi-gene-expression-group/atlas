package uk.ac.ebi.atlas.profiles.stream;

import com.esotericsoftware.kryo.io.UnsafeInput;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerBiologicalReplicate;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerReplicateProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.differential.ContrastTest;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.model.resource.KryoFile;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProfileStreamKryoLayerTest {
    MockDataFileHub dataFileHub;

    CreatesProfilesFromTsvFiles source;

    @Mock
    Experiment experiment;

    String accession = "accession";

    @Mock
    ProfileStreamOptions profileStreamOptions;

    @Before
    public void setUp() throws IOException {
        dataFileHub = Mockito.spy(MockDataFileHub.create());
        source = mock(CreatesProfilesFromTsvFiles.class);
        source.dataFileHub = dataFileHub;

        when(experiment.getAccession()).thenReturn(accession);

    }

    @Test
    public void createAsksDataFileHubForFiles() throws Exception {
        new ProfileStreamKryoLayer(source).create(experiment, profileStreamOptions, Optional.empty());

        verify(dataFileHub).getKryoFile(experiment.getAccession(), profileStreamOptions);
    }

    @Test
    public void createDelegatesToSourceIfNoFilesPresent() throws Exception {
        ObjectInputStream objectInputStream = mock(ObjectInputStream.class);
        Optional<Collection<String>> keepGeneIds = Optional.empty();
        when(source.create(experiment, profileStreamOptions,keepGeneIds)).thenReturn(objectInputStream);

        assertThat(new ProfileStreamKryoLayer(source).create(experiment, profileStreamOptions,keepGeneIds), is(objectInputStream));
    }

    @Test
    public void whenFilePresentCreateDoesNotAskSource() {
        DataFileHub mockHub = mock(DataFileHub.class);
        source.dataFileHub = mockHub;

        KryoFile kryoFile = mock(KryoFile.class);

        when(kryoFile.exists()).thenReturn(true);

        KryoFile.Handle handle = mock(KryoFile.Handle.class);
        when(handle.read()).thenReturn(new UnsafeInput());

        when(kryoFile.get()).thenReturn(handle);


        when(mockHub.getKryoFile(experiment.getAccession(), profileStreamOptions)).thenReturn(kryoFile);

        source = Mockito.spy(source);
        new ProfileStreamKryoLayer(source).create(experiment, profileStreamOptions, Optional.empty());

        verify(source).filterExpressions(experiment, profileStreamOptions);
        verifyNoMoreInteractions(source);
    }

    @Test
    public void persistPersistsSomething() throws Exception {
        assertThat(dataFileHub.getKryoFile(experiment.getAccession(), profileStreamOptions).exists(), is(false));

        new ProfileStreamKryoLayer(source).persist(experiment, profileStreamOptions);

        assertThat(dataFileHub.getKryoFile(experiment.getAccession(), profileStreamOptions).exists(), is(true));
    }

    @Test
    public void kryoPersistsAllOurObjectsAndWeGetThemBack() {

        ObjectInputStream objectInputStream = mock(ObjectInputStream.class);

        when(source.filterExpressions(experiment, profileStreamOptions)).thenReturn(Predicates.alwaysTrue());

        Profile p1 = new BaselineProfile("id_1", "name_1");
        p1.add(new AssayGroup("g1", "r1"), new BaselineExpression(1.2));

        Profile p2 = new RnaSeqProfile("id_2", "name_2");
        p2.add(ContrastTest.get(1).get(0), new DifferentialExpression(0.05, 1.3));

        Profile p3 = new MicroarrayProfile("id_3", "name_3", "design_element");
        p3.add(ContrastTest.get(1).get(0), new MicroarrayExpression(0.05, 1.3,1.4));

        Profile p4 = new BaselineExpressionPerReplicateProfile("id_4","id_4");
        p4.add(new AssayGroup("g1", "assay") , new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(new BiologicalReplicate("assay"), new BaselineExpression(1.2))));

        given(objectInputStream.readNext())
                .willReturn(p1)
                .willReturn(p2)
                .willReturn(p3)
                .willReturn(p4)
                .willReturn(null);

        when(source.create(experiment, profileStreamOptions,Optional.empty())).thenReturn(objectInputStream);


        new ProfileStreamKryoLayer(source).persist(experiment, profileStreamOptions);

        assertThat(
                ImmutableList.copyOf(new IterableObjectInputStream<>(new ProfileStreamKryoLayer(source).create(experiment, profileStreamOptions, Optional.empty()))),
                is(ImmutableList.<Object>of(p1, p2, p3, p4))
        );


    }
}