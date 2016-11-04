package uk.ac.ebi.atlas.tracks;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;

@Named
@Scope("singleton")
public class TracksUtil {

    private final String diffTracksPath;
    private final String baselineTracksPath;

    @Inject
    public TracksUtil(@Value("#{configuration['baseline.experiment.tracks.path']}") String baselineTracksPath, @Value("#{configuration['diff.experiment.tracks.path']}") String diffTracksPath) {
        this.baselineTracksPath = baselineTracksPath;
        this.diffTracksPath = diffTracksPath;
    }

    public boolean hasBaselineTracksPath(String experimentAccession, String assayGroupName) {
        String path = MessageFormat.format(baselineTracksPath, experimentAccession, assayGroupName);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    public boolean hasDiffTracksPath(String experimentAccession, String contrastName) {
        String path = MessageFormat.format(diffTracksPath, experimentAccession, contrastName);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

}
