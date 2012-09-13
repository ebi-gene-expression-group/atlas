package uk.ac.ebi.atlas.commands;

import com.google.common.base.Function;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import java.util.List;

public interface ProcessTranscriptProfileStreamCommand extends Function<ObjectInputStream<TranscriptProfile>, List<TranscriptExpression>> {

}
