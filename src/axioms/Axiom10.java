package axioms;

import expressions.Implication;
import expressions.LogicExpression;
import expressions.Negation;

public class Axiom10 implements AxiomSchema {
    // !!A -> A

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            if (implication.lhs instanceof Negation n1) {
                if (n1.lhs instanceof Negation n2) {
                    return implication.rhs.equals(n2.lhs);
                }
            }
        }
        return false;
    }
}
