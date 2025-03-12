import axioms.*;
import expressions.Context;
import expressions.Proof;
import expressions.Variable;
import org.junit.Test;
import parser.Parser;

import java.util.Scanner;

import static org.junit.Assert.*;

public class Task2Test {
    private static final Parser p = new Parser();

    @Test
    public void testEmptyProof() {
        assertEquals("|-", p.parse("|-").getProof());
    }

    @Test
    public void testSingleContextProof() {
        assertEquals("A|-", p.parse("A|-").getProof());
        assertEquals("A&B|-", p.parse("A&B|-").getProof());
        assertEquals("A&B|-A", p.parse("A&B|-A").getProof());
    }

    @Test
    public void testMultipleContextProof() {
        assertEquals("A,B,C|-", p.parse("A,B,C|-").getProof());
        assertEquals("A,B,C|-A", p.parse("A,B,C|-A").getProof());
        assertEquals("A,B,C|-A|B|C", p.parse("A,B,C|-A|B|C").getProof());
        assertEquals("A|A,A->A,A&A,A|-", p.parse("A|A,A->A,A&A,A|-").getProof());
    }

    @Test
    public void testLonelyContext() {
        assertEquals("A,B,C", p.parse("A, B, C").getProof());
        assertEquals("A|!A,B->D,C", p.parse("A | !A, B -> D, C").getProof());
    }

    @Test
    public void testAxiom1() {
        Axiom1 a = new Axiom1();
        assertTrue(a.equalsAxiom(p.parse("A->B->A")));
        assertTrue(a.equalsAxiom(p.parse("A&B->B->A&B")));
        assertTrue(a.equalsAxiom(p.parse("A->!!B->A")));
        assertTrue(a.equalsAxiom(p.parse("A->A->A")));
        assertTrue(a.equalsAxiom(p.parse("(A|B&!ABACABA)->A&B|!C->(A|B&!ABACABA)")));
        assertFalse(a.equalsAxiom(p.parse("A->A")));
        assertFalse(a.equalsAxiom(p.parse("A|B|A")));
        assertFalse(a.equalsAxiom(p.parse("A->A|A")));
        assertFalse(a.equalsAxiom(p.parse("A->A->A->A")));
    }

    @Test
    public void testAxiom2() {
        Axiom2 a = new Axiom2();
        assertTrue(a.equalsAxiom(p.parse("(A -> B) -> (A -> B -> C) -> (A -> C)")));
        assertFalse(a.equalsAxiom(p.parse("A->A->A->A->A")));
        assertFalse(a.equalsAxiom(p.parse("A->V->A")));
    }

    @Test
    public void testAxiom3() {
        Axiom3 a = new Axiom3();
        assertTrue(a.equalsAxiom(p.parse("A->B->A&B")));
        assertTrue(a.equalsAxiom(p.parse("A->A->A&A")));
        assertTrue(a.equalsAxiom(p.parse("A&A->A&A->(A&A)&(A&A)")));
        assertFalse(a.equalsAxiom(p.parse("A->A&A")));
        assertFalse(a.equalsAxiom(p.parse("A&A->A&A")));
        assertFalse(a.equalsAxiom(p.parse("A->B")));
    }

    @Test
    public void testAxiom4() {
        Axiom4 a = new Axiom4();
        assertTrue(a.equalsAxiom(p.parse("A&B->A")));
        assertFalse(a.equalsAxiom(p.parse("A&B->B")));
    }

    @Test
    public void testAxiom5() {
        Axiom5 a = new Axiom5();
        assertTrue(a.equalsAxiom(p.parse("A&B->B")));
        assertFalse(a.equalsAxiom(p.parse("A&B->A")));
    }

    @Test
    public void testAxiom6() {
        Axiom6 a = new Axiom6();
        assertTrue(a.equalsAxiom(p.parse("A->A|B")));
        assertTrue(a.equalsAxiom(p.parse("A->A|A")));
        assertFalse(a.equalsAxiom(p.parse("B->A|B")));
    }

    @Test
    public void testAxiom7() {
        Axiom7 a = new Axiom7();
        assertTrue(a.equalsAxiom(p.parse("B->A|B")));
        assertTrue(a.equalsAxiom(p.parse("A->A|A")));
        assertFalse(a.equalsAxiom(p.parse("A->A|B")));
    }

    @Test
    public void testAxiom8() {
        Axiom8 a = new Axiom8();
        assertTrue(a.equalsAxiom(p.parse("(A -> C) -> (B -> C) -> (A | B -> C)")));
        assertTrue(a.equalsAxiom(p.parse("(A -> C) -> (A -> C) -> (A | A -> C)")));
        assertTrue(a.equalsAxiom(p.parse("(A -> A) -> (A -> A) -> (A | A -> A)")));
        assertTrue(a.equalsAxiom(p.parse("((A->D->G->H) -> C) -> (B -> C) -> ((A->D->G->H) | B -> C)")));
        assertFalse(a.equalsAxiom(p.parse("(A -> C) -> (B -> B) -> (A | B -> C)")));
    }

    @Test
    public void testAxiom9() {
        Axiom9 a = new Axiom9();
        assertTrue(a.equalsAxiom(p.parse("(A -> B) -> (A -> !B) -> !A")));
        assertTrue(a.equalsAxiom(p.parse("(!A -> B) -> (!A -> !B) -> !!A")));
        assertTrue(a.equalsAxiom(p.parse("(A -> A) -> (A -> !A) -> !A")));
        assertFalse(a.equalsAxiom(p.parse("(A -> A) -> (A -> !A) -> A")));
    }

    @Test
    public void testAxiom10() {
        Axiom10 a = new Axiom10();
        assertTrue(a.equalsAxiom(p.parse("!!A -> A")));
        assertTrue(a.equalsAxiom(p.parse("!!!A -> !A")));
        assertTrue(a.equalsAxiom(p.parse("!!(A&B) -> (A&B)")));
        assertTrue(a.equalsAxiom(p.parse("!!!!B -> !!B")));
        assertFalse(a.equalsAxiom(p.parse("(A -> B) -> (A -> !B) -> !A")));
    }

    @Test
    public void testEqualsWithout() {
        Context a = new Context();
        a.addHypothesis(p.parse("A->A"));
        a.addHypothesis(p.parse("A->B"));
        a.addHypothesis(p.parse("A|C"));
        a.addHypothesis(p.parse("D"));

        Context b = new Context();
        b.addHypothesis(p.parse("A->A"));
        b.addHypothesis(p.parse("A->B"));
        b.addHypothesis(p.parse("A|C"));

        Context c = new Context();
        c.addHypothesis(p.parse("A->A"));
        c.addHypothesis(p.parse("A->B"));
        c.addHypothesis(p.parse("D"));

        assertTrue(a.equalsWithout(b, 3));
        assertTrue(a.equalsWithout(c, 2));
        assertFalse(a.equalsWithout(b, 1));
        assertFalse(a.equalsWithout(c, 0));
    }

    @Test
    public void testSetEquals() {
        Context a = new Context();
        a.addHypothesis(p.parse("A->A"));
        a.addHypothesis(p.parse("A->B"));
        a.addHypothesis(p.parse("A|C"));

        Context b = new Context();
        b.addHypothesis(p.parse("A|C"));
        b.addHypothesis(p.parse("A->A"));
        b.addHypothesis(p.parse("A->B"));

        assertTrue(a.setEquals(b));

        Context c = new Context();
        c.addHypothesis(p.parse("A->A"));
        c.addHypothesis(p.parse("A->A"));
        c.addHypothesis(p.parse("A->A"));
        c.addHypothesis(p.parse("A->B"));
        c.addHypothesis(p.parse("A|C"));

        Context d = new Context();
        d.addHypothesis(p.parse("A->A"));
        d.addHypothesis(p.parse("A|C"));
        d.addHypothesis(p.parse("A->A"));
        d.addHypothesis(p.parse("A->B"));
        d.addHypothesis(p.parse("A->A"));

        assertTrue(c.setEquals(d));
    }

    @Test
    public void testIncorrect() {
        testProofProcessor("""
                        |-A->B->C->D
                        |-A&B
                        |-A&B->A
                        |-A&B->B
                        |-A
                        |-B
                        |-B->C->D
                        |-C->D
                        C|-D
                        B|-C->D
                        """,
                """
                        [1] |-A->B->C->D [Incorrect]
                        [2] |-A&B [Incorrect]
                        [3] |-A&B->A [Ax. sch. 4]
                        [4] |-A&B->B [Ax. sch. 5]
                        [5] |-A [M.P. 2, 3; from Incorrect]
                        [6] |-B [M.P. 2, 4; from Incorrect]
                        [7] |-B->C->D [M.P. 5, 1; from Incorrect]
                        [8] |-C->D [M.P. 6, 7]
                        [9] C|-D [Ded. 8]
                        [10] B|-C->D [Ded. 7]
                        """);
    }

    @Test
    public void testMultipleExplanation() {
        testProofProcessor("""
                        B|-A->B
                        |-B->A->B
                        B|-A->B
                        B,A|-B
                        """,
                """
                        [1] B|-A->B [Incorrect]
                        [2] |-B->A->B [Ax. sch. 1]
                        [3] B|-A->B [Ded. 2]
                        [4] B,A|-B [Hyp. 1]
                        """);
    }

    @Test
    public void test1() {
        testProofProcessor("""
                        |-A&B->A
                        |-A&B->B
                        A&B|-A
                        A&B|-B
                        A&B|-B->A->B&A
                        A&B|-A->B&A
                        A&B|-B&A
                        |-A&B->B&A
                        """,
                """
                        [1] |-A&B->A [Ax. sch. 4]
                        [2] |-A&B->B [Ax. sch. 5]
                        [3] A&B|-A [Ded. 1]
                        [4] A&B|-B [Ded. 2]
                        [5] A&B|-B->A->B&A [Ax. sch. 3]
                        [6] A&B|-A->B&A [M.P. 4, 5]
                        [7] A&B|-B&A [M.P. 3, 6]
                        [8] |-A&B->B&A [Ded. 7]
                        """);
    }

    @Test
    public void test2() {
        testProofProcessor("""
                        A->B,C,D|-E->D
                        A->B,C,D,D|-D
                        A->B,C,D,D|-D->E
                        A->B,C,D|-E
                        D,A->B,C,D|-E
                        E,D,C|-(A->B)->D""",
                """
                        [1] A->B,C,D|-E->D [Incorrect]
                        [2] A->B,C,D,D|-D [Hyp. 4]
                        [3] A->B,C,D,D|-D->E [Incorrect]
                        [4] A->B,C,D|-E [Incorrect]
                        [5] D,A->B,C,D|-E [M.P. 2, 3; from Incorrect]
                        [6] E,D,C|-(A->B)->D [Ded. 1; from Incorrect]
                        """);
    }

    @Test
    public void test3() {
        testProofProcessor("""
                        |-A->A->A
                        |-(A->A->A)->(A->((A->A)->A))->(A->A)
                        |-(A->((A->A)->A))->(A->A)
                        |-A->((A->A)->A)
                        |-A->A""",
                """
                        [1] |-A->A->A [Ax. sch. 1]
                        [2] |-(A->A->A)->(A->((A->A)->A))->(A->A) [Ax. sch. 2]
                        [3] |-(A->((A->A)->A))->(A->A) [M.P. 1, 2]
                        [4] |-A->((A->A)->A) [Ax. sch. 1]
                        [5] |-A->A [M.P. 4, 3]
                        """);
    }

    @Test
    public void test4() {
        testProofProcessor("""
                        |-(A->A)->(A->A->B)->(A->B)
                        (A->A)|-(A->A->B)->(A->B)
                        (A->A)|-(A->A)
                        |-(A->A->B)->(A->B)
                        """,
                """
                        [1] |-(A->A)->(A->A->B)->(A->B) [Ax. sch. 2]
                        [2] (A->A)|-(A->A->B)->(A->B) [Ded. 1]
                        [3] (A->A)|-(A->A) [Hyp. 1]
                        [4] |-(A->A->B)->(A->B) [Incorrect]
                        """);
    }

    @Test
    public void test5() {
        testProofProcessor("""
                        |-A&!A->A
                        |-A&!A->!A
                        |-((A&!A)->A)->((A&!A)->!A)->!(A&!A)
                        |-((A&!A)->!A)->!(A&!A)
                        |-!(A&!A)
                        """,
                """
                        [1] |-A&!A->A [Ax. sch. 4]
                        [2] |-A&!A->!A [Ax. sch. 5]
                        [3] |-((A&!A)->A)->((A&!A)->!A)->!(A&!A) [Ax. sch. 9]
                        [4] |-((A&!A)->!A)->!(A&!A) [M.P. 1, 3]
                        [5] |-!(A&!A) [M.P. 2, 4]
                        """);
    }

    @Test
    public void test6() {
        testProofProcessor("""
                        |-A->(A->B)->A
                        |-A
                        |-(A->B)->A
                        |-((A->B)->A)->((A->B)->!B)->!(A->B)
                        |-((A->B)->!B)->!(A->B)
                        A, !B, B|-!A
                        |-!A->(A->B)->!A
                        A, !B, B|-!(A->B)
                        """,
                """
                        [1] |-A->(A->B)->A [Ax. sch. 1]
                        [2] |-A [Incorrect]
                        [3] |-(A->B)->A [M.P. 2, 1; from Incorrect]
                        [4] |-((A->B)->A)->((A->B)->!B)->!(A->B) [Incorrect]
                        [5] |-((A->B)->!B)->!(A->B) [M.P. 3, 4; from Incorrect]
                        [6] A, !B, B|-!A [Incorrect]
                        [7] |-!A->(A->B)->!A [Ax. sch. 1]
                        [8] A, !B, B|-!(A->B) [Incorrect]
                        """);
    }

    @Test
    public void test7() {
        testProofProcessor("""
                        A|-A
                        A,A|-A
                        A|-A->A
                        A|-A&(A->A)
                        |-A->(A&(A->A))
                        """,
                """
                        [1] A|-A [Hyp. 1]
                        [2] A,A|-A [Hyp. 2]
                        [3] A|-A->A [Ded. 2]
                        [4] A|-A&(A->A) [Incorrect]
                        [5] |-A->(A&(A->A)) [Ded. 4; from Incorrect]
                        """);
    }

    @Test
    public void test8() {
        testProofProcessor("""
                A|-C->B->A
                C|-A->B->A
                C|-(A->B->A)->C->A->B->A
                |-C->A->B->A
                |-A
                |-A->C->A
                |-C->A
                |-(C->A)->(C->A->B->A)->C->B->A
                |-(C->A->B->A)->C->B->A
                |-C->B->A
                """, """
                [1] A|-C->B->A [Incorrect]
                [2] C|-A->B->A [Ax. sch. 1]
                [3] C|-(A->B->A)->C->A->B->A [Ax. sch. 1]
                [4] |-C->A->B->A [Ded. 2]
                [5] |-A [Incorrect]
                [6] |-A->C->A [Ax. sch. 1]
                [7] |-C->A [M.P. 5, 6; from Incorrect]
                [8] |-(C->A)->(C->A->B->A)->C->B->A [Ax. sch. 2]
                [9] |-(C->A->B->A)->C->B->A [M.P. 7, 8]
                [10] |-C->B->A [M.P. 4, 9]
                """);
    }

    @Test
    public void test9() {
        testProofProcessor("""
                        A, C|-A->B->A
                        A, C|-A
                        A, C|-C->B->A
                        A, C|-B->A
                        """,
                """
                        [1] A, C|-A->B->A [Ax. sch. 1]
                        [2] A, C|-A [Hyp. 1]
                        [3] A, C|-C->B->A [Incorrect]
                        [4] A, C|-B->A [M.P. 2, 1]
                        """);
        testProofProcessor("""
                        A, C|-A
                        A, C|-C->B->A
                        A, C|-A->B->A
                        A, C|-B->A
                        """,
                """
                        [1] A, C|-A [Hyp. 1]
                        [2] A, C|-C->B->A [Incorrect]
                        [3] A, C|-A->B->A [Ax. sch. 1]
                        [4] A, C|-B->A [M.P. 1, 3]
                        """);
        testProofProcessor("""
                        B|-C->A->C
                        C,A|-B->C
                        C,A,B|-C
                        """,
                """
                        [1] B|-C->A->C [Ax. sch. 1]
                        [2] C,A|-B->C [Ded. 1]
                        [3] C,A,B|-C [Hyp. 1]
                        """);
        testProofProcessor("""
                        C,A|-B->C
                        B|-C->A->C
                        C,A,B|-C
                        """,
                """
                        [1] C,A|-B->C [Incorrect]
                        [2] B|-C->A->C [Ax. sch. 1]
                        [3] C,A,B|-C [Hyp. 1]
                        """);
    }

    @Test
    public void test10() {
        testProofProcessor("""
                        |-A->B
                        |-A->B
                        """,
                """
                        [1] |-A->B [Incorrect]
                        [2] |-A->B [Ded. 1; from Incorrect]
                        """);
        testProofProcessor("""
                        A,B,C|-A->B
                        A,B,C|-A->B
                        A,B,C|-A->B
                        """,
                """
                        [1] A,B,C|-A->B [Incorrect]
                        [2] A,B,C|-A->B [Ded. 1; from Incorrect]
                        [3] A,B,C|-A->B [Ded. 2]
                        """);
    }

    @Test
    public void test11() {
        testProofProcessor("""
                        |-A&B->A
                        A&B|-A
                        A&B|-A->A|B
                        A&B|-A|B
                        """,
                """
                        [1] |-A&B->A [Ax. sch. 4]
                        [2] A&B|-A [Ded. 1]
                        [3] A&B|-A->A|B [Ax. sch. 6]
                        [4] A&B|-A|B [M.P. 2, 3]
                        """);
        testProofProcessor("""
                        |- A -> (B -> A)
                        |- (A -> (B -> C)) -> ((A -> B) -> (A -> C))
                        |- A & B -> A
                        |- A & B -> B
                        |- A -> (B -> (A & B))
                        |- A -> (A | B)
                        |- B -> (A | B)
                        |- (A -> C) -> ((B -> C) -> ((A | B) -> C))
                        |- (A -> B) -> ((A -> !B) -> !A)
                        |- !!A -> A
                        |- A -> !!A
                        |- (A -> B) -> (!B -> !A)
                        |- (A & (B | C)) -> ((A & B) | (A & C))
                        |- ((A & B) | (A & C)) -> (A & (B | C))
                        |- (A | (B & C)) -> ((A | B) & (A | C))
                        |- ((A | B) & (A | C)) -> (A | (B & C))
                        |- (A -> B) -> ((C -> D) -> ((A | C) -> (B | D)))
                        |- (A -> B) -> ((C -> D) -> ((A & C) -> (B & D)))
                        |- (A -> B) -> ((B -> C) -> (A -> C))
                        |- !(A & B) -> (!A | !B)
                        |- (!A | !B) -> !(A & B)
                        |- !(A | B) -> (!A & !B)
                        |- (!A & !B) -> !(A | B)
                        |- A | !A
                        |- !(A & !A)
                        """,
                """
                        [1] |- A -> (B -> A) [Ax. sch. 1]
                        [2] |- (A -> (B -> C)) -> ((A -> B) -> (A -> C)) [Incorrect]
                        [3] |- A & B -> A [Ax. sch. 4]
                        [4] |- A & B -> B [Ax. sch. 5]
                        [5] |- A -> (B -> (A & B)) [Ax. sch. 3]
                        [6] |- A -> (A | B) [Ax. sch. 6]
                        [7] |- B -> (A | B) [Ax. sch. 7]
                        [8] |- (A -> C) -> ((B -> C) -> ((A | B) -> C)) [Ax. sch. 8]
                        [9] |- (A -> B) -> ((A -> !B) -> !A) [Ax. sch. 9]
                        [10] |- !!A -> A [Ax. sch. 10]
                        [11] |- A -> !!A [Incorrect]
                        [12] |- (A -> B) -> (!B -> !A) [Incorrect]
                        [13] |- (A & (B | C)) -> ((A & B) | (A & C)) [Incorrect]
                        [14] |- ((A & B) | (A & C)) -> (A & (B | C)) [Incorrect]
                        [15] |- (A | (B & C)) -> ((A | B) & (A | C)) [Incorrect]
                        [16] |- ((A | B) & (A | C)) -> (A | (B & C)) [Incorrect]
                        [17] |- (A -> B) -> ((C -> D) -> ((A | C) -> (B | D))) [Incorrect]
                        [18] |- (A -> B) -> ((C -> D) -> ((A & C) -> (B & D))) [Incorrect]
                        [19] |- (A -> B) -> ((B -> C) -> (A -> C)) [Incorrect]
                        [20] |- !(A & B) -> (!A | !B) [Incorrect]
                        [21] |- (!A | !B) -> !(A & B) [Incorrect]
                        [22] |- !(A | B) -> (!A & !B) [Incorrect]
                        [23] |- (!A & !B) -> !(A | B) [Incorrect]
                        [24] |- A | !A [Incorrect]
                        [25] |- !(A & !A) [Incorrect]
                        """);
        testProofProcessor("""
                        |- A -> (B -> A)
                        |- (A -> (B -> C)) -> ((A -> B) -> (A -> C))
                        |- A & B -> A
                        |- A & B -> B
                        |- A -> (B -> (A & B))
                        |- A -> (A | B)
                        |- B -> (A | B)
                        |- (A -> C) -> ((B -> C) -> ((A | B) -> C))
                        |- (A -> B) -> ((A -> !B) -> !A)
                        |- !!A -> A
                        |- A -> !!A
                        |- (A -> B) -> (!B -> !A)
                        |- A -> A
                        |- (A -> (B -> C)) -> (B -> (A -> C))
                        |- (A -> B) -> ((B -> C) -> (A -> C))
                        |- A -> (B -> C)
                        |- (A -> B) -> (A -> B)
                        |- A -> (!B -> !(A -> B))
                        |- (A -> B) -> ((A -> !B) -> !A)
                        |- (A -> B) -> (!B -> !A)
                        |- (A -> B) -> (A -> B)
                        |- (A -> (B -> C)) -> ((A & B) -> C)
                        |- (A & B) -> C
                        |- A -> (B -> (A & B))
                        |- (A -> B) -> ((C -> D) -> ((A | C) -> (B | D)))
                        """,
                """
                        [1] |- A -> (B -> A) [Ax. sch. 1]
                        [2] |- (A -> (B -> C)) -> ((A -> B) -> (A -> C)) [Incorrect]
                        [3] |- A & B -> A [Ax. sch. 4]
                        [4] |- A & B -> B [Ax. sch. 5]
                        [5] |- A -> (B -> (A & B)) [Ax. sch. 3]
                        [6] |- A -> (A | B) [Ax. sch. 6]
                        [7] |- B -> (A | B) [Ax. sch. 7]
                        [8] |- (A -> C) -> ((B -> C) -> ((A | B) -> C)) [Ax. sch. 8]
                        [9] |- (A -> B) -> ((A -> !B) -> !A) [Ax. sch. 9]
                        [10] |- !!A -> A [Ax. sch. 10]
                        [11] |- A -> !!A [Incorrect]
                        [12] |- (A -> B) -> (!B -> !A) [Incorrect]
                        [13] |- A -> A [Incorrect]
                        [14] |- (A -> (B -> C)) -> (B -> (A -> C)) [Incorrect]
                        [15] |- (A -> B) -> ((B -> C) -> (A -> C)) [Incorrect]
                        [16] |- A -> (B -> C) [Incorrect]
                        [17] |- (A -> B) -> (A -> B) [Incorrect]
                        [18] |- A -> (!B -> !(A -> B)) [Incorrect]
                        [19] |- (A -> B) -> ((A -> !B) -> !A) [Ax. sch. 9]
                        [20] |- (A -> B) -> (!B -> !A) [Ded. 12; from Incorrect]
                        [21] |- (A -> B) -> (A -> B) [Ded. 17; from Incorrect]
                        [22] |- (A -> (B -> C)) -> ((A & B) -> C) [Incorrect]
                        [23] |- (A & B) -> C [M.P. 16, 22; from Incorrect]
                        [24] |- A -> (B -> (A & B)) [Ax. sch. 3]
                        [25] |- (A -> B) -> ((C -> D) -> ((A | C) -> (B | D))) [Incorrect]
                        """);
    }

    @Test
    public void testContextMatching() {
        testProofProcessor("""
                        A,B,C|-D
                        B,A,C|-D->E
                        C,A,B|-E
                        """,
                """
                        [1] A,B,C|-D [Incorrect]
                        [2] B,A,C|-D->E [Incorrect]
                        [3] C,A,B|-E [M.P. 1, 2; from Incorrect]
                        """);
    }

    @Test
    public void testModusPonesContext() {
        testProofProcessor("""
                        |-A
                        C|-A->B
                        C|-B
                        """,
                """
                        [1] |-A [Incorrect]
                        [2] C|-A->B [Incorrect]
                        [3] C|-B [Incorrect]
                        """);
    }

    private static void testProofProcessor(String source, String expected) {
        Scanner sc = new Scanner(source);
        Parser p = new Parser();
        ProofProcessor proofProcessor = new ProofProcessor();
//        System.out.println("=======");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
//            System.out.println(line);
            Proof proof = (Proof) p.parse(line);
            proof.representation = line;
            proofProcessor.add(proof);
        }
//        System.out.println("=======");
        sc.close();
        proofProcessor.process();
        assertEquals(expected, proofProcessor.getProof());
    }
}
