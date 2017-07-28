package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import au.com.bytecode.opencsv.CSVReader;
import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

@Named
public class OrganismPartGroupingService {

    private final LazyReference<Multimap<String, ColumnGroup>> anatomicalSystemsMap ;
    private final LazyReference<Multimap<String, ColumnGroup>> organsMap;

    @Inject
    public OrganismPartGroupingService(@Value("#{configuration['anatomical.systems.file']}") String anatomicalSystemsPath, @Value("#{configuration['organs.file']}") String organsPath) {
        this.anatomicalSystemsMap = makeMap.apply(anatomicalSystemsPath);
        this.organsMap = makeMap.apply(organsPath);
    }

    public Map<ColumnGroup, Set<OntologyTerm>> getAnatomicalSystemsGrouping(Collection<OntologyTerm> ontologyTerms) {
        return getGrouping(ontologyTerms, term -> anatomicalSystemsMap.get().get(term.accession()));
    }

    public Map<ColumnGroup, Set<OntologyTerm>> getOrgansGrouping(Collection<OntologyTerm> ontologyTerms) {
        return getGrouping(ontologyTerms, term -> organsMap.get().get(term.accession()));
    }

    private Map<ColumnGroup, Set<OntologyTerm>> getGrouping(Collection<OntologyTerm> ontologyTerms, Function<OntologyTerm, Collection<ColumnGroup>> columnGroups) {

        Map<ColumnGroup, Set<OntologyTerm>> groupings = new HashMap<>();

        for (OntologyTerm ontologyTerm : ontologyTerms) {
            for (ColumnGroup a : columnGroups.apply(ontologyTerm)) {
                if (!groupings.containsKey(a)) {
                    groupings.put(a, new HashSet<>());
                }
                groupings.get(a).add(ontologyTerm);

            }
        }

        return groupings;
    }


    private Function<String, LazyReference<Multimap<String, ColumnGroup>>> makeMap = path -> new LazyReference<Multimap<String, ColumnGroup>>() {
        @Override
        protected Multimap<String, ColumnGroup> create() {
            return getColumnGroups(FileSystems.getDefault().getPath(path));
        }
    };


    private Multimap<String, ColumnGroup> getColumnGroups(Path path) {

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(Files.newInputStream(path)), '\t')) {
            ImmutableMultimap.Builder<String, ColumnGroup> b = ImmutableMultimap.builder();
            csvReader.readAll().stream().filter(row -> !row[0].startsWith("#")).forEach(row -> {
                b.put(row[2], ColumnGroup.create(row[0], row[1]));
            });
            return b.build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
