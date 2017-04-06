package uk.ac.ebi.atlas.experimentimport.admin;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class OpLogEntry {

    static Long UNFINISHED = new Long(-1);

    static String FAILED = "FAILED: ";

    private final boolean failed;
    private final Op op;
    private final Long start;
    private final Long finish;
    private final String message;



    private OpLogEntry(boolean failed, Op op, Long start, Long finish, String message){
        this.failed = failed;
        this.op = op;
        this.start = start;
        this.finish = finish;
        this.message = message;
    }

    private static long snapshotCurrentTime(){
        return System.currentTimeMillis();
    }

    static OpLogEntry newlyStartedOp(Op op){
        return new OpLogEntry(
                false, op, snapshotCurrentTime(), UNFINISHED, ""
        );
    }

    OpLogEntry toFailure(String text){
        return failedOp(op, start, snapshotCurrentTime(), text);
    }


    OpLogEntry toSuccess(){
        return successfulOp(op, start, snapshotCurrentTime());
    }

    static OpLogEntry failedOp(Op op, Long start, Long finish, String message){
        return new OpLogEntry(
                true,
                op,
                start,
                finish,
                message
        );
    }


    static OpLogEntry successfulOp(Op op, Long start, Long finish){
        return new OpLogEntry(
                false, op, start, finish, ""
        );
    }

    static OpLogEntry NULL(String message) {
        return new OpLogEntry(true, Op.LIST, 0L, 0L, message);
    }

    public boolean isInProgress(){
        return finish.equals(UNFINISHED);
    }

    public String statusMessage(){
        StringBuilder sb = new StringBuilder("Operation ");
        sb.append(op);
        sb.append(" started at ");
        sb.append(new DateTime(start).toString());
        if(isInProgress()){
            sb.append(" and not finished. ");
        } else {
            sb.append(" and ");
            sb.append(failed? "failed" :"succeeded");
            sb.append(" after ");
            sb.append(timeTakenInSeconds());
            sb.append(" seconds. ");
        }
        if(StringUtils.isNotEmpty(message)){
            sb.append(message);
        }
        return sb.toString();
    }



    static OpLogEntry fromArray(String[] lines){
        try {
            Preconditions.checkArgument(lines.length >=3);
            return new OpLogEntry(
                    lines[0].startsWith(FAILED),
                    Op.valueOf(lines[0].replace(FAILED, "").trim()),
                    Long.parseLong(lines[1]),
                    Long.parseLong(lines[2]),
                    Joiner.on(" ").join(ArrayUtils.subarray(lines, 3, lines.length))
            );
        } catch (IllegalArgumentException e){
            return NULL("Invalid op log entry, could not read: "+ Joiner.on("\t").join(lines));
        }
    }

    String[] toArray(){
        return new String[] {
                failed? FAILED+op.name() : op.name(),
                start.toString(),
                finish.toString(),
                message
        };
    }

    private int timeTakenInSeconds(){
        return new Period((
                finish.equals(UNFINISHED)
                        ? System.currentTimeMillis()
                        : finish)
                - start).toStandardSeconds().getSeconds();
    }

    JsonObject toJson(){
        JsonObject result = new JsonObject();
        result.addProperty("op",failed? FAILED+op.name() : op.name());
        result.addProperty("started", new DateTime(start).toString());
        if(isInProgress()){
            result.addProperty("in_progress",true);
        }
        result.addProperty("time_taken_seconds",
                timeTakenInSeconds());
        if(failed){
            result.addProperty("error", message);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpLogEntry that = (OpLogEntry) o;
        return failed == that.failed &&
                op == that.op &&
                Objects.equal(start, that.start) &&
                Objects.equal(finish, that.finish) &&
                Objects.equal(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(failed, op, start, finish, message);
    }

}
