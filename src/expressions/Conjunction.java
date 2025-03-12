package expressions;

public class Conjunction extends AbstractExpression{
    public Conjunction(LogicExpression lhs, LogicExpression rhs) {
        super(lhs, rhs);
    }

    @Override
    protected String getSign() {
        return "&";
    }

    @Override
    protected String lhsProof() {
        if ((lhs instanceof Disjunction) || (lhs instanceof Implication)) {
            return "(" + lhs.getProof() + ")";
        }
        return lhs.getProof();
    }

    @Override
    protected String rhsProof() {
        if ((rhs instanceof Disjunction) || (rhs instanceof Implication)) {
            return "(" + rhs.getProof() + ")";
        }
        return rhs.getProof();
    }
}
