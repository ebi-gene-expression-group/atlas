package uk.ac.ebi.atlas.commands;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.gene.GeneAnnotationLoader;

import javax.inject.Inject;
import javax.inject.Named;

@Named("geneNameUpdateCommand")
@Scope("prototype")
public class GeneNameUpdateCommand {

    private GeneAnnotationLoader geneAnnotationLoader;

    @Inject
    public GeneNameUpdateCommand(GeneAnnotationLoader geneAnnotationLoader) {
        this.geneAnnotationLoader = geneAnnotationLoader;
    }

    public void loadGeneNames() {
        geneAnnotationLoader.loadGeneNames();
    }

}
