package axioms;

import expressions.Implication;
import expressions.LogicExpression;

public class Axiom2 implements AxiomSchema {
    // (A -> B) -> (A -> B -> C) -> (A -> C)

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication) {
            LogicExpression tmp1 = ((Implication) expression).lhs;
            if (!(tmp1 instanceof Implication)) {
                return false;
            }
            if (((Implication) expression).rhs instanceof Implication) {
                LogicExpression tmp2 = ((Implication) ((Implication) expression).rhs).lhs;
                LogicExpression tmp3 = ((Implication) ((Implication) expression).rhs).rhs;
                if (!(tmp2 instanceof Implication)) {
                    return false;
                }
                if (!(tmp3 instanceof Implication)) {
                    return false;
                }
                if (((Implication) tmp2).rhs instanceof Implication) {
                    LogicExpression a = ((Implication) tmp1).lhs;
                    LogicExpression b = ((Implication) tmp1).rhs;
                    LogicExpression a1 = ((Implication) tmp2).lhs;
                    LogicExpression b1 = ((Implication) ((Implication) tmp2).rhs).lhs;
                    LogicExpression c = ((Implication) ((Implication) tmp2).rhs).rhs;
                    LogicExpression a2 = ((Implication) tmp3).lhs;
                    LogicExpression c1 = ((Implication) tmp3).rhs;
                    return a.equals(a1) && c.equals(c1) && a.equals(a2) && b.equals(b1);
                }
            }
        }
        return false;
    }
}
