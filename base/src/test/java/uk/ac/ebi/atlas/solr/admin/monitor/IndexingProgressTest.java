
package uk.ac.ebi.atlas.solr.admin.monitor;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IndexingProgressTest {

    private IndexingProgress subject;

    private Path tempDirectoryPath;
    private Path tempFilePath1;
    private Path tempFilePath2;

    @After
    public void tearDown() throws IOException {
        Files.delete(tempFilePath1);
        Files.delete(tempFilePath2);
        Files.delete(tempDirectoryPath);
    }

    @Before
    public void setUp() throws Exception {
        tempDirectoryPath = Paths.get(System.getProperty("java.io.tmpdir"), "data-files/bioentity_properties");

        tempDirectoryPath = Files.createDirectories(tempDirectoryPath);
        tempFilePath1 = Files.write(tempDirectoryPath.resolve("temp-file1.tsv"), Lists.newArrayList("hello"), Charset.defaultCharset());
        tempFilePath2 = Files.write(tempDirectoryPath.resolve("temp-file2.tsv"), Lists.newArrayList("bye"), Charset.defaultCharset());

        subject = new IndexingProgress();
    }

    @Test
    public void testMinutesToCompletion() throws Exception {
        //given
        subject.completed(tempFilePath1,9);
        //when
        int minutesToCompletion = subject.minutesToCompletion(100);
        //then
        assertThat(minutesToCompletion, is(2));
    }

    @Test
    public void testProgress() throws Exception {
        //given
        subject.completed(tempFilePath1,9);
        //when
        int progress = subject.progress(100);
        //then
        assertThat(progress, is(6));

        //given
        subject.completed(tempFilePath1,9);
        //when
        progress = subject.progress(30);
        //then
        assertThat(progress, is(40));
    }

    @Test
    public void testIterator() throws Exception {
        //given
        subject.completed(tempFilePath1,9);
        //when
        Iterator iterator = subject.iterator();

        //then
        assertThat(iterator.next().toString(), containsString(tempFilePath1.toString()));
        //and
        assertThat(iterator.hasNext(), is(false));
    }


}

