package axioms;

import expressions.Conjunction;
import expressions.Implication;
import expressions.LogicExpression;

public class Axiom4 implements AxiomSchema {
    // A&B->A

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication) {
            Implication i = (Implication) expression;
            if (i.lhs instanceof Conjunction) {
                Conjunction c = (Conjunction) i.lhs;
                return i.rhs.equals(c.lhs);
            }
        }
        return false;
    }
}
