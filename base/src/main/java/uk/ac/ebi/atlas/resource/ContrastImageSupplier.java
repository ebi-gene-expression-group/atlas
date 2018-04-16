package uk.ac.ebi.atlas.resource;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.ContrastImage;
import uk.ac.ebi.atlas.model.resource.ExternalImage;
import uk.ac.ebi.atlas.model.resource.ResourceType;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ContrastImageSupplier<E extends DifferentialExperiment> extends ExternallyAvailableContent.Supplier<E>{

    @Override
    public ExternallyAvailableContent.ContentType contentType(){
        return ExternallyAvailableContent.ContentType.PLOTS;
    }

    @Inject
    ContrastImageFactory contrastImageFactory;


    ExternallyAvailableContent.Description description(ResourceType resourceType, Contrast contrast){
        String description;
        String type;
        switch (resourceType) {
            case PLOT_GSEA_REACTOME:
                description = "Reactome pathways enrichment analysis";
                type = "icon-gsea-reactome";
                break;
            case PLOT_GSEA_INTERPRO:
                description = "Interpro domains enrichment analysis";
                type = "icon-gsea-interpro";
                break;
            case PLOT_GSEA_GO:
                description = "GO terms enrichment analysis";
                type = "icon-gsea-go";
                break;
            case PLOT_MA:
                description = "MA plot for the contrast across all genes";
                type = "icon-ma";
                break;
            default:
                description = "";
                type = "";
        }

        return ExternallyAvailableContent.Description.create(
                contrast.getDisplayName(),
                type,
                description
        );
    }



    @Named
    public static class RnaSeq extends ContrastImageSupplier<DifferentialExperiment> {

        @Override
        public Collection<ExternallyAvailableContent> get(DifferentialExperiment experiment) {
            return getForRnaSeqDifferentialExperiment(experiment.getAccession(), experiment.getDataColumnDescriptors());
        }

        Collection<ExternallyAvailableContent> getForRnaSeqDifferentialExperiment(String experimentAccession, List<Contrast> contrasts){
            ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();
            for(Contrast contrast: contrasts){
                for(ResourceType resourceType : ContrastImage.RESOURCE_TYPES){
                    ExternalImage externalImage = contrastImageFactory.getContrastImage(resourceType, experimentAccession, Optional.empty(), contrast.getId());
                    if(externalImage.exists()){
                        b.add(new ExternallyAvailableContent
                                (makeUri(MessageFormat.format("contrast_image-{0}-{1}", resourceType.name(), contrast.getId())),
                                        description(resourceType, contrast) , externalImage.get()
                                ));
                    }
                }
            }
            return b.build();
        }
    }

    @Named
    public static class Microarray extends ContrastImageSupplier<MicroarrayExperiment> {

        @Override
        public Collection<ExternallyAvailableContent> get(MicroarrayExperiment experiment) {
            return getForMicroarrayExperiment(experiment.getAccession(), experiment.getDataColumnDescriptors(),  experiment.getArrayDesignAccessions());
        }

        Collection<ExternallyAvailableContent> getForMicroarrayExperiment(String experimentAccession, List<Contrast> contrasts, Collection<String> arrayDesigns){
            ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();
            for(String arrayDesign: arrayDesigns){
                for(Contrast contrast: contrasts){
                    for(ResourceType resourceType : ContrastImage.RESOURCE_TYPES){
                        ExternalImage externalImage = contrastImageFactory.getContrastImage(resourceType, experimentAccession, Optional.of(arrayDesign), contrast.getId());
                        if(externalImage.exists()){
                            b.add(new ExternallyAvailableContent
                                    (makeUri(MessageFormat.format("contrast_image-{0}-{1}-{2}", resourceType.name(), contrast.getId(), arrayDesign)),
                                            description(resourceType, contrast) , externalImage.get()
                                    ));
                        }
                    }
                }
            }
            return b.build();
        }
    }
}
