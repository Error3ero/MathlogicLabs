package axioms;

import expressions.Conjunction;
import expressions.Implication;
import expressions.LogicExpression;

public class Axiom3 implements AxiomSchema {
    // A->B->A&B

    @Override
    public boolean equalsAxiom(LogicExpression expression) {
        if (expression instanceof Implication) {
            Implication implication = (Implication) expression;
            LogicExpression a = implication.lhs;
            if (implication.rhs instanceof Implication) {
                Implication tmp = (Implication) implication.rhs;
                LogicExpression b = tmp.lhs;
                if (tmp.rhs instanceof Conjunction) {
                    Conjunction conjunction = (Conjunction) tmp.rhs;
                    return a.equals(conjunction.lhs) && b.equals(conjunction.rhs);
                }
            }
        }
        return false;
    }
}
