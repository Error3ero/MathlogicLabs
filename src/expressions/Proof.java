package expressions;

public class Proof extends AbstractExpression {
    public String representation;

    public Proof(Context lhs, LogicExpression rhs) {
        super(lhs, rhs);
    }

    public LogicExpression getExpression() {
        return rhs;
    }

    public Context getContext() {
        return (Context) lhs;
    }

    @Override
    protected String getSign() {
        return "|-";
    }

    @Override
    protected String lhsProof() {
        return lhs.getProof();
    }

    @Override
    protected String rhsProof() {
        return rhs.getProof();
    }
}
