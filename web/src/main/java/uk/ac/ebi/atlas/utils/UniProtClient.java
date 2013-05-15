package uk.ac.ebi.atlas.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Collection;

@Named
@Scope("prototype")
public class UniProtClient {

    public static final String UNIPROT_URL = "http://www.uniprot.org/uniprot/?query=accession:{0}&format=tab&columns=id,database(reactome)";

    private RestTemplate restTemplate;

    @Inject
    public UniProtClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Collection<String> fetchReactomeIds(String uniprotId) {
        String url = MessageFormat.format(UNIPROT_URL, uniprotId);
        String result = restTemplate.getForObject(url, String.class);

        return parseResult(result);
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
