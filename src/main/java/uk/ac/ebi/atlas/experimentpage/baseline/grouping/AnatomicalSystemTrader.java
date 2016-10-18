package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import au.com.bytecode.opencsv.CSVReader;
import com.atlassian.util.concurrent.LazyReference;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collection;

@Named
public class AnatomicalSystemTrader {

    private final String anatomicalSystemsPath;
    private LazyReference<Multimap<String, AnatomicalSystem>> anatomicalSystemsMap =
            new  LazyReference<Multimap<String, AnatomicalSystem>>() {
        @Override
        protected Multimap<String, AnatomicalSystem> create() throws Exception {
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(Files.newInputStream(FileSystems.getDefault().getPath(anatomicalSystemsPath))), '\t')) {
                csvReader.readNext();

                ImmutableMultimap.Builder<String, AnatomicalSystem> b = ImmutableMultimap.builder();
                for (String[] row : csvReader.readAll()) {
                    if (!row[0].startsWith("#")) {
                        b.put(row[2], AnatomicalSystem.create(row[0], row[1]));
                    }
                }
                return b.build();

            } catch (IOException e) {
                throw Throwables.propagate(e);
            }
        }
    };

    public AnatomicalSystemTrader() {
        this.anatomicalSystemsPath = getClass().getClassLoader().getResource("anatomical_systems.txt").getPath();
    }

    public Collection<AnatomicalSystem> getAnatomicalSystemsIncluding(String ontologyTermId){
        return anatomicalSystemsMap.get().get(ontologyTermId);
    }
}
