package uk.ac.ebi.atlas.commands.download;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class AnalyticsDataHeaderBuilder implements HeaderBuilder {
    private DifferentialExperiment experiment;


     @Override
     public String[] buildHeader(String[] header) {
         checkNotNull(experiment, "Experiment should be not null!");

         List<String> result = new ArrayList<>();
         result.add(GENE_NAME);
         result.add(getSecondColumnName());

         String[] headerWithoutFirstElement = ArrayUtils.remove(header, 0);
         for (String columnHeader : headerWithoutFirstElement) {
             result.add(replaceContrastIdWithName(columnHeader));
         }

         return result.toArray(new String[result.size()]);
     }

     protected String replaceContrastIdWithName(String columnHeader) {
         String contrastId = StringUtils.substringBefore(columnHeader, ".");
         String displayName = experiment.getContrast(contrastId).getDisplayName();
         return StringUtils.replace(columnHeader, contrastId, displayName);
     }

     public void setExperiment(DifferentialExperiment experiment) {
         this.experiment = experiment;
     }

    protected String getSecondColumnName() {
        return GENE_ID;
    }
}
