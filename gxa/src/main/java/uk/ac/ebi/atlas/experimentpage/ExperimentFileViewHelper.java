package uk.ac.ebi.atlas.experimentpage;

import javax.inject.Named;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;

@Named
public abstract class ExperimentFileViewHelper {
    protected String fileTemplate;
    protected String urlTemplate;

    public boolean hasFile(String experimentAccession) {
        String path = MessageFormat.format(fileTemplate, experimentAccession);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    public String generateUrl(String experimentAccession) {
        return MessageFormat.format(urlTemplate, experimentAccession);
    }

}
