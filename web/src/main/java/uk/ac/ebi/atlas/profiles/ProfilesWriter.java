package uk.ac.ebi.atlas.profiles;

import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commands.download.GeneProfilesTSVWriter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamPipelineBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class ProfilesWriter<P extends Profile, K> {

    private static final Logger LOGGER = Logger.getLogger(ProfilesWriter.class);

    private ProfileStreamPipelineBuilder<P, ProfileStreamOptions> pipelineBuilder;
    private GeneProfilesTSVWriter<P, K> tsvWriter;

    public ProfilesWriter(ProfileStreamPipelineBuilder<P, ? extends ProfileStreamOptions> pipelineBuilder, GeneProfilesTSVWriter<P, K> tsvWriter) {
        // add cast to avoid requiring additional class type parameter
        this.pipelineBuilder = (ProfileStreamPipelineBuilder<P, ProfileStreamOptions>) pipelineBuilder;
        this.tsvWriter = tsvWriter;
    }

    public long write(PrintWriter outputWriter, ObjectInputStream<P> inputStream, ProfileStreamOptions options, Set<K> conditions)  {

        tsvWriter.setResponseWriter(outputWriter);

        try (ObjectInputStream<P> source = inputStream) {

            Iterable<P> profiles = new IterableObjectInputStream<>(source);

            Iterable<P> profilesPipeline = pipelineBuilder.build(profiles, options);

            return tsvWriter.write(profilesPipeline, conditions);

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }

}
