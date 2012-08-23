package uk.ac.ebi.atlas.services;

import uk.ac.ebi.atlas.model.ExpressionLevel;

import java.io.Closeable;

public interface ExpressionLevelStream extends Closeable {

    public ExpressionLevel readNext();

}
