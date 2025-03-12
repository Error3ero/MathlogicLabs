package axioms;

import expressions.Disjunction;
import expressions.Implication;
import expressions.LogicExpression;

public class Axiom8 implements AxiomSchema {
    // (A -> C) -> (B -> C) -> (A | B -> C)

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication implication) {
            if (implication.lhs instanceof Implication) {
                Implication i1 = (Implication) implication.lhs;
                LogicExpression a = i1.lhs;
                LogicExpression c = i1.rhs;
                if (implication.rhs instanceof Implication) {
                    Implication tmp = (Implication) implication.rhs;
                    if (tmp.lhs instanceof Implication) {
                        Implication i2 = (Implication) tmp.lhs;
                        LogicExpression b = i2.lhs;
                        LogicExpression c1 = i2.rhs;
                        if (tmp.rhs instanceof Implication) {
                            Implication i3 = (Implication) tmp.rhs;
                            if (i3.lhs instanceof Disjunction) {
                                Disjunction d = (Disjunction) i3.lhs;
                                LogicExpression a1 = d.lhs;
                                LogicExpression b1 = d.rhs;
                                LogicExpression c2 = i3.rhs;
                                return a.equals(a1) && b.equals(b1) && c.equals(c1) && c.equals(c2);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}


