package uk.ac.ebi.atlas.commands.download;

class MicroarrayNormalizedDataHeaderBuilder extends RnaSeqRawDataHeaderBuilder {

    @Override
    protected String getSecondColumnName() {
        return DESIGN_ELEMENT;
    }
}
