package uk.ac.ebi.atlas.gene;

import au.com.bytecode.opencsv.CSVReader;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.collections.TransactionWorker;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;

import java.io.*;

public class GeneAnnotationLoader {

    private BDBConfiguration configuration = new BDBConfiguration();

    public void loadAnnotations(InputStream annotationsInputStream) {

        Reader dataFileReader = new InputStreamReader(annotationsInputStream);
        CSVReader csvReader = new CSVReader(dataFileReader, '\t');

        Environment environment = configuration.environment();
        Database database = configuration.geneDatabase(environment);
        final StoredMap<String, String> map = configuration.geneNames(database);


        try {
            String[] line;
            while ((line = csvReader.readNext()) != null) {

                TransactionRunner tr = new TransactionRunner(environment);
                try {
                    final String[] finalLine = line;
                    tr.run(new TransactionWorker() {
                        public void doWork() {
                            try {
                                if (!map.containsKey(finalLine[0]))
                                    map.put(finalLine[0], finalLine[1]);
                                else
                                    System.out.println("Key " + finalLine[0] + ": " + finalLine[1] +
                                            " already exists.");
                            } catch (Exception e) {
                                System.err.println("doWork: " + e);
                            }
                        }
                    });
                } catch (DatabaseException e) {
                    System.err.println("AccessExample: " + e);
                    System.exit(1);
                } catch (Exception e) {
                    System.err.println("AccessExample: " + e);
                    System.exit(1);
                }


            }


            csvReader.close();
            database.close();
            environment.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws FileNotFoundException {
        GeneAnnotationLoader loader = new GeneAnnotationLoader();
        FileInputStream inputStream = new FileInputStream("/Users/nsklyar/Downloads/mart_export_hs.txt");

        loader.loadAnnotations(inputStream);

    }
}
