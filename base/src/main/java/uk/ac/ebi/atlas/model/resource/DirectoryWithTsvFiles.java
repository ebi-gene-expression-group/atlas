package uk.ac.ebi.atlas.model.resource;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Map;

public class DirectoryWithTsvFiles extends AtlasResource<Map<String, AtlasResource<TsvReader>>> {

    public DirectoryWithTsvFiles(String dataFilesLocation, String template, String... args) {
        super(Paths.get(dataFilesLocation, MessageFormat.format(template, (Object []) args)));
    }

    @Override
    public Map<String, AtlasResource<TsvReader>> get() {
        Preconditions.checkState(path.toFile().isDirectory());
        ImmutableMap.Builder<String, AtlasResource<TsvReader>> results = ImmutableMap.builder();

        for(String name: path.toFile().list()){
            results.put(name,
                    new TsvFile.ReadOnly(
                    path.toString(), name
            ));
        }
        return results.build();
    }
}
