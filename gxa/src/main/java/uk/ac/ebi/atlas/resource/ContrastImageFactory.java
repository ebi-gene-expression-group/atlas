package uk.ac.ebi.atlas.resource;


import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.ContrastImage;
import uk.ac.ebi.atlas.model.resource.ExternalImage;
import uk.ac.ebi.atlas.model.resource.ResourceType;

import javax.inject.Named;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

@Named
public class ContrastImageFactory extends ExternallyAvailableContent.Supplier<DifferentialExperiment>{

    @Value("#{configuration['experiment.gsea-plot.path.template']}")
    String gseaPathTemplate;

    @Value("#{configuration['experiment.rnaseq.ma-plot.path.template']}")
    String rnaSeqPathTemplate;

    @Value("#{configuration['experiment.microarray.ma-plot.path.template']}")
    String microarrayPathTemplate;


    ExternalImage getContrastImage(ResourceType resourceType, String experimentAccession, Optional<String>
            arrayDesign,
                                   String contrastId ){
        String pathTemplate = "";
        switch (resourceType) {
            case PLOT_GSEA_INTERPRO:
                pathTemplate = gseaPathTemplate.replace("{2}", "interpro");
                break;
            case PLOT_GSEA_GO:
                pathTemplate = gseaPathTemplate.replace("{2}", "go");
                break;
            case PLOT_GSEA_REACTOME:
                pathTemplate = gseaPathTemplate.replace("{2}", "reactome");
                break;
            case PLOT_MA:
                pathTemplate = arrayDesign.isPresent()? microarrayPathTemplate : rnaSeqPathTemplate;
                break;
        }

        String uriTemplate = (arrayDesign.isPresent()
                ? "/external-resources/{0}/{1}/{2}/"
                :"/external-resources/{0}/{1}/")+resourceType.fileName();


        return arrayDesign.isPresent()
        ?   new ContrastImage(resourceType,pathTemplate,uriTemplate,
                experimentAccession,arrayDesign.get(), contrastId)
                :new ContrastImage(resourceType,pathTemplate,uriTemplate,
                experimentAccession, contrastId);
    }

    ExternalImage getContrastImage(ResourceType resourceType, String experimentAccession,
                                   String contrastId ){
        return getContrastImage(resourceType, experimentAccession, Optional.<String>absent(), contrastId);
    }

    Collection<ExternallyAvailableContent> getForRnaSeqDifferentialExperiment(String experimentAccession, List<Contrast> contrasts){
        ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();
        for(Contrast contrast: contrasts){
            for(ResourceType resourceType : ContrastImage.RESOURCE_TYPES){
                ExternalImage externalImage = getContrastImage(resourceType, experimentAccession, Optional.<String>absent(), contrast.getId());
                if(externalImage.exists()){
                    b.add(ExternallyAvailableContent.create
                            (URI.create(MessageFormat.format("contrast_image-{0}-{1}", resourceType.name(), contrast.getId())),
                                    resourceType, externalImage.get(), contrast.getDisplayName()
                    ));
                }
            }
        }
        return b.build();
    }

    Collection<ExternallyAvailableContent> getForMicroarrayExperiment(String experimentAccession, List<Contrast> contrasts, Collection<String> arrayDesigns){
        ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();
        for(String arrayDesign: arrayDesigns){
            for(Contrast contrast: contrasts){
                for(ResourceType resourceType : ContrastImage.RESOURCE_TYPES){
                    ExternalImage externalImage = getContrastImage(resourceType, experimentAccession, Optional.of(arrayDesign), contrast.getId());
                    if(externalImage.exists()){
                        b.add(ExternallyAvailableContent.create
                                (URI.create(MessageFormat.format("contrast_image-{0}-{1}-{2}", resourceType.name(), contrast.getId(), arrayDesign)),
                                        resourceType, externalImage.get(), contrast.getDisplayName()
                                ));
                    }
                }
            }
        }
        return b.build();
    }

    //TODO maybe : have separate suppliers for the two kinds of experiments instead of putting them together here
    @Override
    public Collection<ExternallyAvailableContent> get(DifferentialExperiment experiment) {
        if(experiment instanceof MicroarrayExperiment){
            return getForMicroarrayExperiment(experiment.getAccession(), experiment.getDataColumnDescriptors(),  ((MicroarrayExperiment) experiment).getArrayDesignAccessions());
        } else {
            return getForRnaSeqDifferentialExperiment(experiment.getAccession(), experiment.getDataColumnDescriptors());
        }
    }


}
