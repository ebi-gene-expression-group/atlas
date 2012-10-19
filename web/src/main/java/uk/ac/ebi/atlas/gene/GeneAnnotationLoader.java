package uk.ac.ebi.atlas.gene;

import au.com.bytecode.opencsv.CSVReader;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.collections.TransactionWorker;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import uk.ac.ebi.atlas.utils.biomart.MartQuery;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import static java.util.Arrays.asList;
import static uk.ac.ebi.atlas.utils.biomart.HttpClientHelper.httpPost;

public class GeneAnnotationLoader {

    private BDBConfiguration configuration = new BDBConfiguration();

    public void loadAnnotations(InputStream annotationsInputStream) {

        Reader dataFileReader = new InputStreamReader(annotationsInputStream);
        CSVReader csvReader = new CSVReader(dataFileReader, '\t');

        Environment environment = configuration.environment("/Users/nsklyar/Data/bdb/gene");
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

    public static void main(String[] args) throws IOException {
        GeneAnnotationLoader loader = new GeneAnnotationLoader();
//        FileInputStream inputStream = new FileInputStream("/Users/nsklyar/Downloads/mart_export_hs.txt");

        MartQuery query = new MartQuery(
                "default",
                "hsapiens_gene_ensembl")
                .addAttributes(asList("ensembl_gene_id", "external_gene_id"));

        HttpClient httpClient = new DefaultHttpClient();

        InputStream inputStream = httpPost(httpClient, URI.create("http://www.ensembl.org/biomart/martservice?"),
                asList(new BasicNameValuePair("query", query.toString())));

        loader.loadAnnotations(inputStream);

    }
}
