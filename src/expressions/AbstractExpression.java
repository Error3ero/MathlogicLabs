package expressions;

public abstract class AbstractExpression implements LogicExpression {
    public final LogicExpression lhs;
    public final LogicExpression rhs;

    public AbstractExpression(LogicExpression lhs, LogicExpression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    protected abstract String getSign();
    protected abstract String lhsProof();
    protected abstract String rhsProof();

    @Override
    public String getNode() {
        return "(" + getSign() + "," + lhs.getNode() + "," + rhs.getNode() + ")";
    }

    @Override
    public String getProof() {
        return lhsProof() + getSign() + rhsProof();
    }

    @Override
    public boolean equals(LogicExpression other) {
        if (this == other) {
            return true;
        }
        if (this.getClass() == other.getClass()) {
            return this.lhs.equals(((AbstractExpression) other).lhs) && this.rhs.equals(((AbstractExpression) other).rhs);
        }
        return false;
    }
}
