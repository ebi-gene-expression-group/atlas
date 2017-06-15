package uk.ac.ebi.atlas.commons.streams;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ObjectInputStreamsTest {


    ObjectInputStream<Integer> oneTwoThreeNull(){
        ObjectInputStream<Integer> inputStream = (ObjectInputStream<Integer>) mock(ObjectInputStream.class);
        when(inputStream.readNext())
                .thenReturn(1, 2, 3, null);
        return inputStream;
    }

    @Test
    public void alwaysTrue(){
        ObjectInputStream<Integer> out = ObjectInputStreams.filter(oneTwoThreeNull(), Predicates.<Integer>alwaysTrue());
        assertThat(out.readNext(), is(1));
        assertThat(out.readNext(), is(2));
        assertThat(out.readNext(), is(3));
        assertNull(out.readNext());
    }

    @Test
    public void alwaysFalse(){
        ObjectInputStream<Integer> out = ObjectInputStreams.filter(oneTwoThreeNull(), Predicates.<Integer>alwaysFalse());
        assertNull(out.readNext());
    }

    void keepOnlyOne(final Integer toKeep){
        ObjectInputStream<Integer> out = ObjectInputStreams.filter(oneTwoThreeNull(), integer -> integer.equals(toKeep));
        assertThat(out.readNext(), is(toKeep));
        assertNull(out.readNext());
    }

    @Test
    public void keepOne(){
        keepOnlyOne(1);
        keepOnlyOne(2);
        keepOnlyOne(3);
    }
}