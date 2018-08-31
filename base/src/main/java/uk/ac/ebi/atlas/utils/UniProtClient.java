package uk.ac.ebi.atlas.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;

@Named
@Scope("prototype")
public class UniProtClient {

    public static final String UNIPROT_URL =
            "http://www.ebi.uniprot.org/uniprot/?query=accession:{0}&format=tab&columns=id, database(reactome)";

    private static final Logger LOGGER = LoggerFactory.getLogger(UniProtClient.class);

    private RestTemplate restTemplate;

    @Inject
    public UniProtClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Wouldn't it be nice if the pipeline did this instead? :)
    public Collection<String> fetchReactomeIds(String uniprotId) {
        String url = MessageFormat.format(UNIPROT_URL, uniprotId);

        try {
            String result = restTemplate.getForObject(url, String.class);
            return parseResult(result);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    protected Collection<String> parseResult(String entryString) {

        String ids = StringUtils.substringAfterLast(entryString, "\t");
        if (!StringUtils.isBlank(ids)) {
            return Lists.newArrayList(Splitter.on(";").omitEmptyStrings().trimResults().split(ids));
        } else {
            return Lists.newArrayList();
        }
    }
}
