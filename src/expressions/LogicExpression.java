package expressions;

public interface LogicExpression {
    String getNode();
    String getProof();
    boolean equals(LogicExpression other);
}
