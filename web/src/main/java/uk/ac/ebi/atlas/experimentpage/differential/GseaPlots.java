package uk.ac.ebi.atlas.experimentpage.differential;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class GseaPlots {

    private final boolean go;
    private final boolean interpro;
    private final boolean reactome;

    public GseaPlots(String gseaPathTemplate, String experimentAccession, String contrastId) {
        this.go = hasFile(gseaPathTemplate, experimentAccession, contrastId, "go");
        this.interpro = hasFile(gseaPathTemplate, experimentAccession, contrastId, "interpro");
        this.reactome = hasFile(gseaPathTemplate, experimentAccession, contrastId, "reactome");
    }

    private boolean hasFile(String gseaPathTemplate, String experimentAccession, String contrastId, String geneSetType) {
        String imagePath = MessageFormat.format(gseaPathTemplate, experimentAccession, contrastId, geneSetType);

        return Files.exists(Paths.get(imagePath));
    }

}
