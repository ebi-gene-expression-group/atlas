package uk.ac.ebi.atlas.commands.download;

import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialExperimentFullDataWriter extends ExpressionsWriter {

    public static final String GENE_NAME = "Gene name";
    public static final String GENE_ID = "Gene Id";

    @Inject
    public DifferentialExperimentFullDataWriter(GeneNamesProvider geneNamesProvider, CsvReaderBuilder csvReaderBuilder) {
        super(csvReaderBuilder, geneNamesProvider);
    }


    @Override
    protected String[] buildHeader(String[] header) {
        String[] headerWithoutFirstElement = ArrayUtils.remove(header, 0);
        return ArrayUtils.addAll(new String[]{GENE_NAME, GENE_ID}, headerWithoutFirstElement);
    }

}
