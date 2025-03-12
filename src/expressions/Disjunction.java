package expressions;

public class Disjunction extends AbstractExpression{
    public Disjunction(LogicExpression lhs, LogicExpression rhs) {
        super(lhs, rhs);
    }

    @Override
    protected String getSign() {
        return "|";
    }

    @Override
    protected String lhsProof() {
        if (lhs instanceof Implication) {
            return "(" + lhs.getProof() + ")";
        }
        return lhs.getProof();
    }

    @Override
    protected String rhsProof() {
        if (rhs instanceof Implication) {
            return "(" + rhs.getProof() + ")";
        }
        return rhs.getProof();
    }
}
