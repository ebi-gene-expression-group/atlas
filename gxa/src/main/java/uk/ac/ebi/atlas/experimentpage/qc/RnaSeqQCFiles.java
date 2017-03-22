package uk.ac.ebi.atlas.experimentpage.qc;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.TsvFile;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class RnaSeqQCFiles {


    private final Set<Path> qcDirectory;

    public RnaSeqQCFiles(AtlasResource<Set<Path>> directoryResource) {
        this(directoryResource.exists() ? directoryResource.get() : ImmutableSet.<Path>of());
    }

    RnaSeqQCFiles(Set<Path> qcDirectory) {
        this.qcDirectory = qcDirectory;
    }

    public Map<String, AtlasResource<TsvReader>> get() {
        ImmutableMap.Builder<String, AtlasResource<TsvReader>> results = ImmutableMap.builder();

        for (Path path : qcDirectory) {
            results.put(path.getFileName().toString(),
                    new TsvFile.ReadOnly(
                            path.getParent().toString(), path.getFileName().toString()
                    ));
        }
        return results.build();
    }

}

