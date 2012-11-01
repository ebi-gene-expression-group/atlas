package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.GeneAnnotationLoader;

import javax.inject.Inject;
import javax.inject.Named;

@Named("geneNameUpdateCommand")
@Scope("prototype")
public class GeneNamesImportCommand {

    private GeneAnnotationLoader geneAnnotationLoader;

    @Inject
    public GeneNamesImportCommand(GeneAnnotationLoader geneAnnotationLoader) {
        this.geneAnnotationLoader = geneAnnotationLoader;
    }

    public void loadGeneNames() {
        geneAnnotationLoader.loadGeneNames();
    }

}
