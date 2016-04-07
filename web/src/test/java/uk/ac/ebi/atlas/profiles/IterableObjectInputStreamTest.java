package uk.ac.ebi.atlas.profiles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.*;

public class IterableObjectInputStreamTest {



    @Test
    public void testSetup() {

        DummyObjectInputStream stream = new DummyObjectInputStream(3);
        Assert.assertNotNull(stream.readNext());
        Assert.assertNotNull(stream.readNext());
        Assert.assertNotNull(stream.readNext());
        Assert.assertNull(stream.readNext());

    }

    @Test
    public void showTheProblem(){
        IterableObjectInputStream<Object> o = new IterableObjectInputStream<>(new DummyObjectInputStream(1));
        Assert.assertTrue(o.iterator().hasNext());
        Assert.assertFalse(o.iterator().hasNext());

    }

    class DummyObjectInputStream implements ObjectInputStream<Object> {

        volatile int count;
        public DummyObjectInputStream(int elements){
            count = elements;
        }
        @Override
        public Object readNext() {
            if(count>0){
                count = count-1;
                return new Object();
            } else
            return null;
        }

        @Override
        public void close() throws IOException {

        }
    }
}