package uk.ac.ebi.atlas.bioentity.go;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class GoTermTrader {

    private ImmutableMap<String, String> goAccessionToTerm;

    @Inject
    public GoTermTrader(GoTermTSVReaderFactory goTermTSVReaderFactory) {
        goAccessionToTerm = readAll(goTermTSVReaderFactory);
    }

    private ImmutableMap<String, String> readAll(GoTermTSVReaderFactory goTermTSVReaderFactory) {
        try (GoTermTSVReader goTermTSVReader = goTermTSVReaderFactory.create()) {

            return goTermTSVReader.readAll();

        } catch (IOException e) {
            throw new GoTraderException("Cannot read from " + goTermTSVReaderFactory.getFilePath(), e);
        }
    }

    public String getTerm(String accession) {
        return goAccessionToTerm.get(accession);
    }

    private class GoTraderException extends RuntimeException {
        public GoTraderException(String msg, IOException e) {
            super(msg, e);
        }
    }

}
