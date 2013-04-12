package uk.ac.ebi.atlas.commands.download;

import org.apache.commons.lang3.ArrayUtils;

class RnaSeqRawDataHeaderBuilder implements HeaderBuilder {

    @Override
    public String[] buildHeader(String[] header) {
        String[] headerWithoutFirstElement = ArrayUtils.remove(header, 0);
              return ArrayUtils.addAll(new String[]{GENE_NAME, getSecondColumnName()}, headerWithoutFirstElement);
    }

    protected String getSecondColumnName() {return GENE_ID;}
}
