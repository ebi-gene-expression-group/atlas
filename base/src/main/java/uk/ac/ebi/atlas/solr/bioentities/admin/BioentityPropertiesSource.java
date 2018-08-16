package uk.ac.ebi.atlas.solr.bioentities.admin;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.resource.BioentityPropertyFile;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.bioentities.BioentityProperty;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class BioentityPropertiesSource {
    private static final Pattern ANNOTATION_FILE_NAME_PATTERN =
            Pattern.compile("\\w+\\.(\\w+gene|mature_mirna)\\.tsv");
    private static final Pattern ARRAY_DESIGN_FILE_NAME_PATTERN =
            Pattern.compile("\\w+\\.A-\\w+-\\d+\\.tsv");
    private static final Pattern PATTERN =
            Pattern.compile("\\w+\\.reactome\\.tsv");

    private final Path annotationsDirPath;
    private final Path arrayDesignsDirPath;
    private final Path reactomeDirPath;
    private final SpeciesFactory speciesFactory;

    public BioentityPropertiesSource(Path annotationsDirPath,
                                     Path arrayDesignsDirPath,
                                     Path reactomeDirPath,
                                     SpeciesFactory speciesFactory) {
        this.annotationsDirPath = annotationsDirPath;
        this.arrayDesignsDirPath = arrayDesignsDirPath;
        this.reactomeDirPath = reactomeDirPath;
        this.speciesFactory = speciesFactory;
    }

    private Species speciesFromFileName(Path file) {
        return speciesFactory.create(file.toFile().getName().split("\\.", 2)[0]);
    }

    class AnnotationFile extends BioentityPropertyFile {

        AnnotationFile(Path path) {
            super(path, speciesFromFileName(path));
        }

        @Override
        public Stream<BioentityProperty> get() {
            try {
                String[] header =
                        Files.lines(path).findFirst()
                                .map(s1 -> s1.split("\t"))
                                .orElseThrow(() -> new RuntimeException("Empty file: " + path));

                return Files.lines(path)
                        .map(s -> s.split("\t", -1)).skip(1)
                        .flatMap(line -> lineOfProperties(line[0], header, line));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class ArrayDesignMappingFile extends  BioentityPropertyFile {
        ArrayDesignMappingFile(Path path) {
            super(path, speciesFromFileName(path));
        }

        @Override
        public Stream<BioentityProperty> get() {
            try {
                return Files.lines(path)
                        .map(s -> s.split("\t", -1))
                        .flatMap(
                                line -> (line[1].isEmpty() ?
                                        Stream.empty() :
                                        Stream.of(bioentityProperty(
                                                line[0], BioentityPropertyName.DESIGN_ELEMENT.name, line[1]))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class ReactomePropertyFile extends BioentityPropertyFile {
        ReactomePropertyFile(Path path) {
            super(path, speciesFromFileName(path));
        }

        @Override
        public Stream<BioentityProperty> get() {
            try {
                return Files.lines(path).map(s -> s.split("\t")).skip(1).flatMap(line -> lineOfProperties(
                        line[0],
                        new String[]{BioentityPropertyName.PATHWAYID.name, BioentityPropertyName.PATHWAYNAME.name},
                        (String[]) ArrayUtils.subarray(line, 1, line.length)
                ));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

   private <T extends BioentityPropertyFile> Stream<T> getBioentityPropertyFiles(Path directory,
                                                                                 Pattern fileNamePattern,
                                                                                 Function<Path, T> makeFromPath) {
        try {
            return Files.list(directory)
                    .filter(path -> fileNamePattern.asPredicate().test(path.toFile().getName()))
                    .map(makeFromPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Stream<AnnotationFile> getAnnotationFiles() {
        return getBioentityPropertyFiles(annotationsDirPath, ANNOTATION_FILE_NAME_PATTERN, AnnotationFile::new);
    }

    Stream<ArrayDesignMappingFile> getArrayDesignMappingFiles() {
        return getBioentityPropertyFiles(
                arrayDesignsDirPath, ARRAY_DESIGN_FILE_NAME_PATTERN, ArrayDesignMappingFile::new);
    }

    Stream<ReactomePropertyFile> getReactomePropertyFiles() {
        return getBioentityPropertyFiles(reactomeDirPath, PATTERN, ReactomePropertyFile::new);
    }
}
