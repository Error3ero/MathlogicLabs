package expressions;

public class Implication extends AbstractExpression {
    public Implication(LogicExpression lhs, LogicExpression rhs) {
        super(lhs, rhs);
    }

    @Override
    protected String getSign() {
        return "->";
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
        return rhs.getProof();
    }
}
