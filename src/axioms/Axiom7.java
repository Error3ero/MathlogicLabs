package axioms;

import expressions.Disjunction;
import expressions.Implication;
import expressions.LogicExpression;

public class Axiom7 implements AxiomSchema {
    // B->A|B

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication) {
            Implication i = (Implication) expression;
            LogicExpression b = i.lhs;
            if (i.rhs instanceof Disjunction) {
                Disjunction d = (Disjunction) i.rhs;
                return b.equals(d.rhs);
            }
        }
        return false;
    }
}
