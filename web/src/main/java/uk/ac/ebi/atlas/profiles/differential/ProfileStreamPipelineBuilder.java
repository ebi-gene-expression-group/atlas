package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.Profile;

public interface ProfileStreamPipelineBuilder<P extends Profile, O extends ProfileStreamOptions> {
    Iterable<P> build(Iterable<P> profiles, O options);
}
