package axioms;

import expressions.Implication;
import expressions.LogicExpression;
import expressions.Negation;

public class Axiom9 implements AxiomSchema {
    // (A -> B) -> (A -> !B) -> !A

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.lhs instanceof Implication) {
                Implication i1 = (Implication) implication.lhs;
                LogicExpression a = i1.lhs;
                LogicExpression b = i1.rhs;
                if (implication.rhs instanceof Implication tmp) {
                    if (tmp.lhs instanceof Implication) {
                        Implication i2 = (Implication) tmp.lhs;
                        LogicExpression a1 = i2.lhs;
                        if (i2.rhs instanceof Negation) {
                            Negation n = (Negation) i2.rhs;
                            LogicExpression b1 = n.lhs;
                            if (tmp.rhs instanceof Negation) {
                                Negation n2 = (Negation) tmp.rhs;
                                LogicExpression a2 = n2.lhs;
                                return a.equals(a1) && a.equals(a2) && b.equals(b1);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
