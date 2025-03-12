import axioms.*;
import expressions.*;
import parser.Parser;

import java.util.*;

public class ProofProcessor {
    public static void processProofs() {
        processProofs(new Scanner(System.in));
    }

    public static void processProofs(String in) {
        processProofs(new Scanner(in));
    }

    private static void processProofs(Scanner sc) {
        Parser p = new Parser();
        ProofProcessor proofProcessor = new ProofProcessor();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Proof proof = (Proof) p.parse(line);
            proof.representation = line;
            proofProcessor.add(proof);
        }
        sc.close();
        proofProcessor.process();
        proofProcessor.print();
    }

    private final List<Proof> proofs = new ArrayList<>();
    private final List<FormalProof> formalProofs = new ArrayList<>();
    int c = 0;

    private final Map<AxiomSchema, Integer> axioms = new HashMap<>();

    public ProofProcessor() {
        axioms.put(new Axiom1(), 1);
        axioms.put(new Axiom2(), 2);
        axioms.put(new Axiom3(), 3);
        axioms.put(new Axiom4(), 4);
        axioms.put(new Axiom5(), 5);
        axioms.put(new Axiom6(), 6);
        axioms.put(new Axiom7(), 7);
        axioms.put(new Axiom8(), 8);
        axioms.put(new Axiom9(), 9);
        axioms.put(new Axiom10(), 10);
    }

    public void add(Proof proof) {
        proofs.add(proof);
    }

    public void print() {
        System.out.println(getProof());
    }

    public String getProof() {
        StringBuilder sb = new StringBuilder();
        for (FormalProof fp : formalProofs) {
            sb.append(fp.getProof()).append("\n");
        }
        return sb.toString();
    }

    public void process() {
        for (Proof proof : proofs) {
            FormalProof fp = new FormalProof(proof, ++c);
            int a = getAxiom(proof);
            if (a > 0) {
                fp.setConclusion(FormalProof.AX.formatted(a));
            } else {
                a = getHypothesis(proof);
                if (a > 0) {
                    fp.setConclusion(FormalProof.HYP.formatted(a));
                } else {
                    a = getDeduction(proof);
                    if (a > 0 && !formalProofs.get(a - 1).incorrect) {
                        fp.setConclusion(FormalProof.DED.formatted(a));
                    } else {
                        int[] mp = getModusPones(proof);
                        if (mp == null) {
                            if (a > 0) {
                                fp.fromIncorrect = true;
                                fp.setConclusion(FormalProof.DED.formatted(a));
                            } else {
                                fp.setConclusion(FormalProof.INC);
                                fp.incorrect = true;
                            }
                        } else {
                            if (formalProofs.get(mp[0] - 1).incorrect || formalProofs.get(mp[1] - 1).incorrect) {
                                fp.fromIncorrect = true;
                            }
                            fp.setConclusion(FormalProof.MP.formatted(mp[0], mp[1]));
                        }
                    }
                }
            }
            formalProofs.add(fp);
        }
    }

    private int getAxiom(Proof proof) {
        for (AxiomSchema axiomSchema : axioms.keySet()) {
            if (axiomSchema.equalsAxiom(proof.getExpression())) {
                return axioms.get(axiomSchema);
            }
        }
        return 0;
    }

    private int getHypothesis(Proof proof) {
        LogicExpression e = proof.getExpression();
        List<LogicExpression> context = proof.getContext().hypothesis;
        for (int i = context.size() - 1; i >= 0; i--) {
            if (e.equals(context.get(i))) {
                return i + 1;
            }
        }
        return 0;
    }

    private int[] getModusPones(Proof proof) {
        int[] res = null;
        for (int i = 0; i < formalProofs.size(); i++) {
            if (formalProofs.get(i).getExpression() instanceof Implication) {
                Implication impl = (Implication) formalProofs.get(i).getExpression();
                if (impl.rhs.equals(proof.getExpression()) && formalProofs.get(i).getContext().setEquals(proof.getContext())) {
                    for (int j = 0; j < formalProofs.size(); j++) {
                        if (formalProofs.get(j).getExpression().equals(impl.lhs) && formalProofs.get(j).getContext().setEquals(proof.getContext())) {
                            if (formalProofs.get(j).incorrect || formalProofs.get(j).incorrect) {
                                res = new int[]{j + 1, i + 1};
                            } else {
                                return new int[]{j + 1, i + 1};
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    private int getDeduction(Proof proof) {
        Proof p = moveToLeft(proof);
        int res = 0;
        for (int i = 0; i < formalProofs.size(); i++) {
            Proof d = moveToLeft(formalProofs.get(i));
            if (p.getExpression().equals(d.getExpression()) && p.getContext().setEquals(d.getContext())) {
                if (!formalProofs.get(i).incorrect) {
                    return i + 1;
                }
                res = i + 1;
            }
        }
        return res;
    }

    private Proof moveToLeft(Proof proof) {
        Context c = new Context();
        c.hypothesis.addAll(proof.getContext().hypothesis);
        LogicExpression e = proof.getExpression();
        while (e instanceof Implication) {
            Implication i = (Implication) e;
            c.hypothesis.add(i.lhs);
            e = i.rhs;
        }
        return new Proof(c, e);
    }
}
