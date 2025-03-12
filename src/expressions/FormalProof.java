package expressions;

public class FormalProof extends Proof {
    public static final String DED = "Ded. %d";
    public static final String HYP = "Hyp. %d";
    public static final String AX = "Ax. sch. %d";
    public static final String MP = "M.P. %d, %d";
    public static final String INC = "Incorrect";
    public static final String FROM_INC = "; from Incorrect";

    private final int n;
    public boolean fromIncorrect;
    public boolean incorrect;
    private String conclusion;

    public FormalProof(Context lhs, LogicExpression rhs, String representation, int n) {
        super(lhs, rhs);
        this.representation = representation;
        this.n = n;
    }

    public FormalProof(Proof proof, int n) {
        this(proof.getContext(), proof.getExpression(), proof.representation, n);

    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    @Override
    public String getProof() {
        return "[" + n + "] " + representation + " [" + conclusion + (fromIncorrect ? FROM_INC : "") + "]";
    }
}
