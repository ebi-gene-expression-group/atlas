package uk.ac.ebi.atlas.geneannotation.arraydesign;

import uk.ac.ebi.atlas.geneannotation.AnnotationEnvironment;
import uk.ac.ebi.atlas.utils.DesignElementKeyGenerator;

import javax.inject.Inject;
import javax.inject.Named;

@Named("designElementMappingProvider")
public class DesignElementMappingProvider {
    private AnnotationEnvironment annotationEnvironment;

    @Inject
    public DesignElementMappingProvider(AnnotationEnvironment annotationEnvironment) {
        this.annotationEnvironment = annotationEnvironment;
    }

    public String getEnsGeneId(String arrayDesign, String designElement) {
        String value = annotationEnvironment.geneDesignElementsToGeneNames().get(DesignElementKeyGenerator.getKey(arrayDesign, designElement));
        return value == null ? "" : value;
    }

}
