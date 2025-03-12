import org.junit.Test;
import parser.Parser;

import static org.junit.Assert.*;

public class Task1Tests {
    private static final Parser p = new Parser();

    @Test
    public void testBinaryOr() {
        assertEquals("(|,A,B)", p.parse("A|B").getNode());
    }

    @Test
    public void testBinaryAnd() {
        assertEquals("(&,A,B)", p.parse("A&B").getNode());
    }

    @Test
    public void testNot() {
        assertEquals("(!A)", p.parse("!A").getNode());
    }

    @Test
    public void testTernaryOr() {
        assertEquals("(|,(|,A,B),C)", p.parse("A|B|C").getNode());
    }

    @Test
    public void testTernaryAnd() {
        assertEquals("(&,(&,A,B),C)", p.parse("A&B&C").getNode());
    }

    @Test
    public void testMoreOr() {
        assertEquals("(|,(|,(|,(|,A,B),C),D),E)", p.parse("A|B|C|D|E").getNode());
    }

    @Test
    public void testMoreAnd() {
        assertEquals("(&,(&,(&,(&,A,B),C),D),E)", p.parse("A&B&C&D&E").getNode());
    }

    @Test
    public void testBinaryImplication() {
        assertEquals("(->,A,B)", p.parse("A->B").getNode());
    }

    @Test
    public void testTernaryImplication() {
        assertEquals("(->,A,(->,B,C))", p.parse("A->B->C").getNode());
    }

    @Test
    public void testMoreImplication() {
        assertEquals("(->,A,(->,B,(->,C,(->,D,E))))", p.parse("A->B->C->D->E").getNode());
    }

    @Test
    public void test1() {
        assertEquals("(->,(&,(!A),(!B)),(!(|,A,B)))", p.parse("!A&!B->!(A|B)").getNode());
    }

    @Test
    public void test2() {
        assertEquals("(->,P1" + (char) 39 + ",(->,(!QQ),(|,(&,(!R10),S),(&,(&,(!T),U),V))))", p.parse("P1" + (char) 39 + "->!QQ->!R10&S|!T&U&V").getNode());
    }

    @Test
    public void test3() {
        assertEquals("(->,(&,A,B),C)", p.parse("A&B->C").getNode());
    }

    @Test
    public void test4() {
        assertEquals("(|,A,(&,B,C))", p.parse("A|B&C").getNode());
    }

    @Test
    public void test5() {
        assertEquals("(|,(&,A,B),C)", p.parse("A&B|C").getNode());
    }

    @Test
    public void test6() {
        assertEquals("(|,(|,(&,(&,A,B),C),D),E)", p.parse("A&B&C|D|E").getNode());
    }

    @Test
    public void test7() {
        assertEquals("(|,(|,(|,(&,(&,(&,A,B),C),D),E),F),G)", p.parse("A&B&C&D|E|F|G").getNode());
    }

    @Test
    public void test8() {
        assertEquals("(->,(&,A,B),(&,B,A))", p.parse("A&B->B&A").getNode());
    }

    @Test
    public void test9() {
        assertEquals("(&,(&,A,(->,B,B)),A)", p.parse("A&(B->B)&A").getNode());
    }

    @Test
    public void test10() {
        assertEquals("(&,(!G),(->,H,(|,I,J)))", p.parse("(!G&(H->(I|J)))").getNode());
    }

    @Test
    public void test11() {
        assertEquals("(|,(|,(|,(&,(&,(&,ABAS1,B" + (char) 39 + "NIGGA),COCK),DICK),ENUM),FAGGOT),GAY)", p.parse("ABAS1&B" + (char) 39 + "NIGGA&COCK&DICK|ENUM|FAGGOT|GAY").getNode());
    }

    @Test
    public void testWrongSymbols() {
        boolean caught = false;
        try {
            System.err.println("Get expression: " + p.parse("p|Q").getNode());
        } catch (Exception e) {
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    public void testEmpty() {
        boolean caught = false;
        try {
            assertEquals("", p.parse("").getNode());
        } catch (Exception e) {
            caught = true;
        }
        assertFalse(caught);
    }

    @Test
    public void test12() {
        assertEquals("(->,(!(!A)),(!(!(!(!A)))))", p.parse("!!A->!!!!A").getNode());
    }
}
