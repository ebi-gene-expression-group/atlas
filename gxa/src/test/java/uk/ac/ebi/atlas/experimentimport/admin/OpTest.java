package uk.ac.ebi.atlas.experimentimport.admin;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OpTest {

    @Test
    public void namesParsedAllRight(){
        testNames(Op.COEXPRESSION_DELETE,Op.COEXPRESSION_IMPORT,Op.LIST);
        testNames(Op.LIST);
    }

    public void testNames(Op ... ops){
        List<String> l = new ArrayList<>();
        for(Op op: ops){
            l.add(op.name());
        }
        assertEquals(Arrays.asList(ops), Op.opsForParameter(StringUtils.join(l, ',')));
    }

    @Test
    public void synonymsWork(){
        assertEquals(Op.opsForParameter("UPDATE_PRIVATE"),Op.opsForParameter("UPDATE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongNameThrows1(){
        Op.opsForParameter("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongNameThrows2(){
        Op.opsForParameter("WRONG");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongNameThrows3(){
        Op.opsForParameter("LIST,WRONG");
    }

    @Test
    public void caseDoesNotMatter(){
        assertEquals(Op.opsForParameter("LIST,LOG"),Op.opsForParameter("list,Log"));
        assertEquals(Op.opsForParameter("LIST,LOG"),Op.opsForParameter("LIST,loG"));
    }

    @Test
    public void synonymAsPartOfClauseWorks(){
        List<Op> a = new ArrayList<>();
        a.addAll(Op.opsForParameter("UPDATE"));
        a.addAll(Op.opsForParameter("LIST"));
        assertEquals(a,Op.opsForParameter("UPDATE,LIST"));
    }

}
