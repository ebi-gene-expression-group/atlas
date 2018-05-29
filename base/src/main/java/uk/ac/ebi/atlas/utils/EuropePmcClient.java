package uk.ac.ebi.atlas.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.model.Publication;

import javax.inject.Inject;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

@Component
public class EuropePmcClient {

    private final static String URL = "https://www.ebi.ac.uk/europepmc/webservices/rest/search?query={0}&format=json";

    private RestTemplate restTemplate;

    private ObjectMapper mapper;

    @Inject
    public EuropePmcClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        this.mapper = new ObjectMapper();
    }

    // Identifier should be either DOI or Pubmed ID
    public Optional<Publication> getPublicationByIdentifier(String identifier) {
        return parseResponseWithOneResult(identifier);
    }

    private Optional<Publication> parseResponseWithOneResult(String query) {
        // Enclose query in quotes as EuropePmc only searches up to the slash for DOIs not enclosed in quotes
        query = "\" " + query + "\"";
        ResponseEntity<String> response = restTemplate.getForEntity(MessageFormat.format(URL, query), String.class);

        if(response.getStatusCode().is2xxSuccessful()) {

            try {
                JsonNode responseAsJson = mapper.readTree(response.getBody());

                if (responseAsJson.has("resultList")) {
                    JsonNode publication = responseAsJson.get("resultList").get("result").get(0);

                    return Optional.of(mapper.readValue(publication, Publication.class));
                }

            } catch (IOException e) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
