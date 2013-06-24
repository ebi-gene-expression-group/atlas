package uk.ac.ebi.atlas.geneannotation;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Map;

@Named("mappingExtractor")
public class FileAnnotationMappingExtractor {

    private TsvReaderUtils tsvReaderUtils;

    @Inject
    public FileAnnotationMappingExtractor(TsvReaderUtils tsvReaderUtils) {
        this.tsvReaderUtils = tsvReaderUtils;
    }

    public Map<String, String> extractAnnotationsMap(String serverUrl, String... urlVariables) {
        Map<String, String> result = Maps.newHashMap();

        String annotationFileLocation = MessageFormat.format(serverUrl, urlVariables);
        CSVReader csvReader = tsvReaderUtils.build(annotationFileLocation);
        try {
            //Skip first line with header
            String[] inputLine = csvReader.readNext();
            while ((inputLine = csvReader.readNext()) != null) {
                result.put(inputLine[0], inputLine[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected Map<String, String> convertJson(String jsonString) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> allMap = gson.fromJson(jsonString, mapType);
        return gson.fromJson(allMap.get("exportText"), mapType);
    }

}
