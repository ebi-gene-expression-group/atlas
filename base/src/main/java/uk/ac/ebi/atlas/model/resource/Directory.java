package uk.ac.ebi.atlas.model.resource;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Set;

public class Directory extends AtlasResource<Set<Path>> {
    public Directory(Path parentDirectory, String template, String... args) {
        super(parentDirectory.resolve(MessageFormat.format(template, (Object[]) args)));
    }

    @Override
    public Set<Path> get() {
        Preconditions.checkState(path.toFile().isDirectory());
        ImmutableSet.Builder<Path> result = ImmutableSet.builder();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path path : directoryStream) {
                result.add(path);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return result.build();
    }
}
