package uk.ac.ebi.atlas.experimentpage.differential;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.List;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class CanStreamSupplier<E extends Experiment> extends ExternallyAvailableContent.Supplier<E> {

    protected Function<HttpServletResponse, Void> streamFolder(
            final String folderName, final List<Pair<String, Function<Writer, Void>>> documents) {

        return response -> {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + folderName + ".zip\"");
            response.setContentType("application/octet-stream");
            try {
                ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
                for (Pair<String, Function<Writer, Void>> p : documents) {
                    ZipEntry ze = new ZipEntry(p.getLeft());
                    zipOutputStream.putNextEntry(ze);
                    p.getRight().apply(new PrintWriter(zipOutputStream));
                    zipOutputStream.closeEntry();
                }
                zipOutputStream.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return null;
        };

    }

    protected Function<HttpServletResponse, Void> streamFile(Pair<String, Function<Writer, Void>> p) {
        return streamFile(p.getLeft(), p.getRight());
    }

    protected Function<HttpServletResponse, Void> streamFile(
            final String fileName, final Function<Writer, Void> document) {

        return response -> {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("text/plain; charset=utf-8");

            try (Writer writer = response.getWriter()) {
                document.apply(writer);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return null;
        };

    }


    protected Function<Writer, Void> readFromStreamAndWriteTsv(
            final AtlasResource<ObjectInputStream<String[]>> resource,
            final Function<String[], String[]> whatToDoWithTheHeaders) {

        return writer -> {
            try (ObjectInputStream<String[]> stream = resource.get();
                 CSVWriter csvWriter =
                         new CSVWriter(writer, '\t', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER)) {
                String[] headers = whatToDoWithTheHeaders.apply(stream.readNext());

                csvWriter.writeNext(headers);

                String[] inputLine;
                while ((inputLine = stream.readNext()) != null) {
                    csvWriter.writeNext(inputLine);
                }
                csvWriter.flush();
            } catch (IOException e) {
                throw new UncheckedIOException("Exception thrown while reading next csv line: " + e.getMessage(), e);
            }
            return null;
        };

    }


    protected Function<Writer, Void> readFromResourceAndWriteTsv(
            final AtlasResource<TsvStreamer> resource,
            final Function<String[], String[]> whatToDoWithTheHeaders) {

        return writer -> {
            try (CSVReader csvReader = new CSVReader(resource.getReader(), '\t');
                 CSVWriter csvWriter =
                         new CSVWriter(writer, '\t', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER)) {
                String[] headers = whatToDoWithTheHeaders.apply(csvReader.readNext());

                csvWriter.writeNext(headers);

                String[] inputLine;
                while ((inputLine = csvReader.readNext()) != null) {
                    csvWriter.writeNext(inputLine);
                }
                csvWriter.flush();
            } catch (IOException e) {
                throw new UncheckedIOException("Exception thrown while reading next csv line: " + e.getMessage(), e);
            }
            return null;
        };

    }
}
