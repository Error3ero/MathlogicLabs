package axioms;

import expressions.AbstractExpression;
import expressions.Implication;
import expressions.LogicExpression;

public class Axiom1 implements AxiomSchema {
    // A->B->A

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication) {
            LogicExpression a = ((AbstractExpression) expression).lhs;
            LogicExpression tmp = ((AbstractExpression) expression).rhs;
            if (tmp instanceof Implication) {
                LogicExpression b = ((AbstractExpression) tmp).lhs;
                LogicExpression c = ((AbstractExpression) tmp).rhs;
                return a.equals(c);
            }
        }
        return false;
    }
}
