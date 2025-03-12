package axioms;

import expressions.Disjunction;
import expressions.Implication;
import expressions.LogicExpression;

public class Axiom6 implements AxiomSchema {
    // A->A|B

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication) {
            Implication i = (Implication) expression;
            LogicExpression a = i.lhs;
            if (i.rhs instanceof Disjunction) {
                Disjunction d = (Disjunction) i.rhs;
                return a.equals(d.lhs);
            }
        }
        return false;
    }
}
