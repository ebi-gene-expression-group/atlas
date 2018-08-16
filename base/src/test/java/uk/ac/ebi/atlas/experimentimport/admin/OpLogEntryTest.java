package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OpLogEntryTest {
    private static final int MAX_DURATION_IN_MILLIS = 10000;

    @Test
    public void testToAndFromArray() {
        for (Op op: Op.values()) {
            toArrayAndFromArrayAreOpposites(
                    OpLogEntry.newlyStartedOp(op));
            toArrayAndFromArrayAreOpposites(
                    OpLogEntry.newlyStartedOp(op).toFailure(""));
            toArrayAndFromArrayAreOpposites(
                    OpLogEntry.newlyStartedOp(op).toFailure(new NullPointerException().toString()));
            toArrayAndFromArrayAreOpposites(
                    OpLogEntry.newlyStartedOp(op).toSuccess());
            toArrayAndFromArrayAreOpposites(
                    OpLogEntry.nullOp("banana\tplease"));

        }
    }

    @Test
    public void testInProgress() {
        for (Op op: Op.values()) {
            assertThat(OpLogEntry.newlyStartedOp(op).isInProgress(), is(true));
            assertThat(OpLogEntry.newlyStartedOp(op).toFailure("").isInProgress(), is(false));
            assertThat(OpLogEntry.newlyStartedOp(op).toSuccess().isInProgress(), is(false));
        }
    }

    @Test
    public void testStatus() {
        long start = new Random().nextLong();
        int duration = new Random().nextInt(MAX_DURATION_IN_MILLIS);
        assertThat(
                OpLogEntry.successfulOp(Op.UPDATE_PUBLIC, start, start + duration).statusMessage(),
                containsString(duration / 1000 + " seconds"));
    }


    private void toArrayAndFromArrayAreOpposites(OpLogEntry opLogEntry) {
        assertThat(OpLogEntry.fromArray(opLogEntry.toArray()), is(opLogEntry));
    }

    @Test
    public void weDoNotBreakOnNonsenseLines() {
        badLinesReturnNullObject("OLD_OP_NAME 1465303135292 1465303135663");
        badLinesReturnNullObject("banana");
        badLinesReturnNullObject("");
    }

    private void badLinesReturnNullObject(String line) {
        JsonObject result = OpLogEntry.fromArray(line.split(" ")).toJson();
        assertThat(result.has("error"), is(true));
    }
}
