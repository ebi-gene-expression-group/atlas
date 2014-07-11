package uk.ac.ebi.atlas.bioentity.interpro;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class InterProTermTrader {

    private ImmutableMap<String, String> interProAccessionToTerm;

    @Inject
    public InterProTermTrader(InterProTermTSVReaderFactory interProTermTSVReaderFactory) {
        interProAccessionToTerm = readAll(interProTermTSVReaderFactory);
    }

    private ImmutableMap<String, String> readAll(InterProTermTSVReaderFactory interProTermTSVReaderFactory) {
        try (InterProTermTSVReader interProTermTSVReader = interProTermTSVReaderFactory.create()) {

            return interProTermTSVReader.readAll();

        } catch (IOException e) {
            throw new InterProTraderException("Cannot read from " + interProTermTSVReaderFactory.getFilePath(), e);
        }
    }

    public String getTerm(String accession) {
        return interProAccessionToTerm.get(accession);
    }

    private class InterProTraderException extends RuntimeException {
        public InterProTraderException(String msg, IOException e) {
            super(msg, e);
        }
    }

}
