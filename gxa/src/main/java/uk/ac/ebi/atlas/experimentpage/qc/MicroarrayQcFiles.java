package uk.ac.ebi.atlas.experimentpage.qc;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class MicroarrayQcFiles {
    private final Set<Path> qcDirectory;

    public MicroarrayQcFiles(AtlasResource<Set<Path>> directoryResource) {
        this(directoryResource.exists() ? directoryResource.get() : ImmutableSet.of());
    }

    private MicroarrayQcFiles(Set<Path> qcDirectory) {
        this.qcDirectory = qcDirectory;
    }

    public Optional<Path> get(final String experimentAccession, final String arrayDesignAccession) {
        return qcDirectory.stream()
                .filter(
                        path ->
                                path.getFileName().toString()
                                        .equals(folderName(experimentAccession, arrayDesignAccession)))
                .findFirst()
                .map(path -> Paths.get(path.toString(), "index.html"));
    }

    static String folderName(String experimentAccession, String arrayDesignAccession) {
        return MessageFormat.format("{0}_{1}_QM", experimentAccession, arrayDesignAccession);
    }

    public Collection<String> getArrayDesignsThatHaveQcReports() {
        return qcDirectory.stream()
                .map(path -> experimentAndArrayDesign(path.getFileName().toString()).getRight())
                .collect(toSet());
    }

    private static Pair<String, String> experimentAndArrayDesign(String folderName) {
        String[] xs = folderName.split("_");
        Preconditions.checkState(
                xs.length == 3,
                "Folder name of QC reports expected as {experiment}_{arrayDesign}_QM, got: " + folderName);
        return Pair.of(xs[0], xs[1]);
    }
}
