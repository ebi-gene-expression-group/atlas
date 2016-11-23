package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.KryoReaderFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

/*
TODO remove me and use superclass
 */
@Named
public class ProteomicsBaselineProfileInputStreamFactory extends BaselineProfileInputStreamFactory {

    @Inject
    public ProteomicsBaselineProfileInputStreamFactory(DataFileHub dataFileHub,
                                                       KryoReaderFactory kryoReaderFactory) {
        super(dataFileHub, kryoReaderFactory);

    }

}
