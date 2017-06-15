package uk.ac.ebi.atlas.experimentpage.qc;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Set;

public class MicroarrayQCFiles {

    private final Set<Path> qcDirectory;

    public MicroarrayQCFiles(AtlasResource<Set<Path>> directoryResource){
        this(directoryResource.exists()? directoryResource.get() : ImmutableSet.<Path>of());
    }

    MicroarrayQCFiles(Set<Path> qcDirectory){
        this.qcDirectory = qcDirectory;
    }

    public Optional<Path> get(final String experimentAccession, final String arrayDesignAccession){
        return FluentIterable.from(qcDirectory).firstMatch(
                path -> path.getFileName().toString().equals(folderName(experimentAccession, arrayDesignAccession))
        ).transform(new Function<Path, Path>() {
            @Nullable
            @Override
            public Path apply(@Nullable Path path) {
                return Paths.get(path.toString(), "index.html");
            }
        });
    }

    static String folderName(String experimentAccession, String arrayDesignAccession){
        return MessageFormat.format("{0}_{1}_QM", experimentAccession, arrayDesignAccession);
    }

    public Collection<String> getArrayDesignsThatHaveQcReports(){
        return FluentIterable.from(qcDirectory).transform(new Function<Path, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Path path) {
                return experimentAndArrayDesign(path.getFileName().toString()).getRight();
            }
        }).toSet();
    }

    static Pair<String, String> experimentAndArrayDesign(String folderName){
        String [] xs = folderName.split("_");
        Preconditions.checkState(xs.length == 3,
                "Folder name of QC reports expected as {experiment}_{arrayDesign}_QM, got: "+folderName);
        return Pair.of(xs[0], xs[1]);
    }
}
