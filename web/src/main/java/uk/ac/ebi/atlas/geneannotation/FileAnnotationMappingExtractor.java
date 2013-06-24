package uk.ac.ebi.atlas.geneannotation;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Maps;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

@Named("fileMappingExtractor")
public class FileAnnotationMappingExtractor implements AnnotationMappingExtractor {

    private TsvReaderUtils tsvReaderUtils;

    @Inject
    public FileAnnotationMappingExtractor(TsvReaderUtils tsvReaderUtils) {
        this.tsvReaderUtils = tsvReaderUtils;
    }

    @Override
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

}
