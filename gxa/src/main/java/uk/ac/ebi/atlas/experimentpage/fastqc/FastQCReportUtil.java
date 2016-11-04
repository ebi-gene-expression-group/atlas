package uk.ac.ebi.atlas.experimentpage.fastqc;

import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.MessageFormat;

@Named
public class FastQCReportUtil {

    private String qcFilePathTemplate;
    private String mappingQCFilePathTemplate;
    private String fastQCFilePathTemplate;

    @Inject
    public FastQCReportUtil(@Value("#{configuration['rnaseq.mrna.fast.qc.path.template']}") String qcFilePathTemplate,
                            @Value("#{configuration['rnaseq.mrna.fast.mapping.qc.path.template']}") String mappingQCFilePathTemplate,
                            @Value("#{configuration['rnaseq.mrna.fastqreport.path.template']}") String fastQCFilePathTemplate) {
        this.qcFilePathTemplate = qcFilePathTemplate;
        this.mappingQCFilePathTemplate = mappingQCFilePathTemplate;
        this.fastQCFilePathTemplate = fastQCFilePathTemplate;
    }

    /***** FAST QC index ****/
    public boolean hasFastQC(String experimentAccession, String species) throws IOException {
        String path = buildFastQCIndexHtmlPath(experimentAccession, species);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    public String buildFastQCIndexHtmlPath(String experimentAccession, String species)  {
        String specie_s = splitSpecies(species).toLowerCase();
        return MessageFormat.format(qcFilePathTemplate, experimentAccession, specie_s, "qc.html");
    }

    /***** FAST QC Mapping index ****/
    public boolean hasMappingQC(String experimentAccession, String species) throws IOException {
        String path = buildMappingQCIndexHtmlPath(experimentAccession, species);
        return Files.exists(FileSystems.getDefault().getPath(path));
    }

    public String buildMappingQCIndexHtmlPath(String experimentAccession, String species)  {
        String specie_s = splitSpecies(species).toLowerCase();
        return MessageFormat.format(mappingQCFilePathTemplate, experimentAccession, specie_s, "tophat2.html");
    }

    /***** FAST QC Report index (sub-folder of FAST QC) ****/
    public String buildFastQCReportIndexHtmlPath(String experimentAccession, String species)  {
        String specie_s = splitSpecies(species).toLowerCase();
        return MessageFormat.format(fastQCFilePathTemplate, experimentAccession, specie_s);
    }

    /**
     * Replace white space for "_" from species if it has more than one word
     * and if species has more than 2 words, it keeps only the first 2.
     */
    private String splitSpecies(String species) {
        String[] splitStr = species.split("\\s+");
        return splitStr.length > 1 ? splitStr[0] + "_" + splitStr[1] : species;
    }

}
