package uk.ac.ebi.atlas.model.resource;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.species.Species;

import java.nio.file.Path;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class BioentityPropertyFile extends AtlasResource<Stream<BioentityProperty>> {

    public final Species species;
    private static final String STUFF_IN_CURLY_BRACKETS_AT_THE_END = "\\{.*\\}$";
    private static final String STUFF_IN_SQUARE_BRACKETS_AT_THE_END = "\\[Source.*\\]$";

    protected Stream<BioentityProperty> lineOfProperties(String bioentityIdentifier, String[] propertyNames, String[] propertyValues) {
        Preconditions.checkState(propertyNames.length == propertyValues.length);

        return IntStream
                .range(0, propertyNames.length)
                .boxed()
                .flatMap(index ->
                        Stream.of(propertyValues[index].split("@@"))
                                .filter(StringUtils::isNotEmpty)
                                .map(propertyValue -> bioentityProperty(bioentityIdentifier, propertyNames[index], propertyValue))
                );

    }

    protected BioentityProperty bioentityProperty(String bioentityIdentifier, String propertyName, String propertyValue) {
        return new BioentityProperty(bioentityIdentifier, species.getEnsemblName(), propertyName, cleanUpPropertyValue(propertyValue));
    }

    String cleanUpPropertyValue(String propertyValue) {
        return propertyValue.replaceFirst(STUFF_IN_CURLY_BRACKETS_AT_THE_END, "").replaceFirst(STUFF_IN_SQUARE_BRACKETS_AT_THE_END, "").trim();
    }

    public BioentityPropertyFile(Path path, Species species) {
        super(path);
        this.species = species;
    }

}
