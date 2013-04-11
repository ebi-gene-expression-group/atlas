package uk.ac.ebi.atlas.commands.download;

import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

public class MicroarrayAnalyticsDataWriter extends MicroarrayDataWriter {

    private MicroarrayAnalyticsDataHeaderBuilder headerBuilder;

    public MicroarrayAnalyticsDataWriter(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider, DesignElementMappingProvider designElementMappingProvider, MicroarrayAnalyticsDataHeaderBuilder headerBuilder) {
        super(csvReaderBuilder, geneNamesProvider, designElementMappingProvider);
        this.headerBuilder = headerBuilder;
    }

    @Override
    protected String[] buildHeader(String[] header) {
        return headerBuilder.buildHeader(header);
    }

}
