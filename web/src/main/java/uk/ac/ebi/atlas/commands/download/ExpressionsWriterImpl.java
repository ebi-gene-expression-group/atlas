package uk.ac.ebi.atlas.commands.download;

import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

public class ExpressionsWriterImpl extends ExpressionsWriter {

    private HeaderBuilder headerBuilder;

    public ExpressionsWriterImpl(CsvReaderBuilder csvReaderBuilder, GeneNamesProvider geneNamesProvider) {
        super(csvReaderBuilder, geneNamesProvider);
    }

    @Override
    protected String[] buildHeader(String[] header) {
        return headerBuilder.buildHeader(header);
    }

    public void setHeaderBuilder(HeaderBuilder headerBuilder) {
        this.headerBuilder = headerBuilder;
    }
}
