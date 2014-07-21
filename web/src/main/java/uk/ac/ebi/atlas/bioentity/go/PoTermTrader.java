package uk.ac.ebi.atlas.bioentity.go;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Created by barrera on 21/07/2014.
 * */
@Named
@Scope("singleton")
public class PoTermTrader  {

    private ImmutableMap<String, String> poAccessionToTerm;

    @Inject
    public PoTermTrader(GoPoTermTSVReaderFactory goPoTermTSVReaderFactory) {
        poAccessionToTerm = readAll(goPoTermTSVReaderFactory);
    }

    private ImmutableMap<String, String> readAll(GoPoTermTSVReaderFactory goPoTermTSVReaderFactory) {
        try (GoPoTermTSVReader goPoTermTSVReader = goPoTermTSVReaderFactory.createPoTerms()) {

            return goPoTermTSVReader.readAll();

        } catch (IOException e) {
            throw new PoTraderException("Cannot read from " + goPoTermTSVReaderFactory.getFilePath(), e);
        }
    }

    public String getTerm(String accession) {
        return poAccessionToTerm.get(accession);
    }

    private class PoTraderException extends RuntimeException {
        public PoTraderException(String msg, IOException e) {
            super(msg, e);
        }
    }

}