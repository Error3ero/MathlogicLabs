package expressions;

public class Variable implements LogicExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String getNode() {
        return name;
    }

    @Override
    public String getProof() {
        return name;
    }

    @Override
    public boolean equals(LogicExpression other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Variable) {
            return name.equals(((Variable) other).name);
        }
        return false;
    }
}
