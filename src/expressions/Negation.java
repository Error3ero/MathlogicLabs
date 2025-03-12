package expressions;

public class Negation implements LogicExpression {
    public final LogicExpression lhs;

    public Negation(LogicExpression lhs) {
        this.lhs = lhs;
    }

    @Override
    public String getNode() {
        return "(!" + lhs.getNode() + ")";
    }

    @Override
    public String getProof() {
        if (lhs instanceof Variable || lhs instanceof Negation) {
            return "!" + lhs.getProof();
        }
        return "!(" + lhs.getProof() + ")";
    }

    @Override
    public boolean equals(LogicExpression other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Negation) {
            return lhs.equals(((Negation) other).lhs);
        }
        return false;
    }
}
