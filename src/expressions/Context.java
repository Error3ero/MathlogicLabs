package expressions;

import java.util.ArrayList;
import java.util.List;

public class Context implements LogicExpression {
    public final List<LogicExpression> hypothesis = new ArrayList<LogicExpression>();

    public void addHypothesis(LogicExpression hyp) {
        hypothesis.add(hyp);
    }

    public int size() {
        return hypothesis.size();
    }

    public boolean isEmpty() {
        return hypothesis.isEmpty();
    }

    @Override
    public String getNode() {
        return "";
    }

    @Override
    public String getProof() {
        StringBuilder sb = new StringBuilder();
        if (!hypothesis.isEmpty()) {
            sb.append(hypothesis.get(0).getProof());
        }
        for (int i = 1; i < hypothesis.size(); i++) {
            sb.append(",").append(hypothesis.get(i).getProof());
        }
        return sb.toString();
    }

    private String removeBrackets(String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    @Override
    public boolean equals(LogicExpression other) {
        if (other instanceof Context) {
            Context c = (Context) other;
            if (c.size() == size()) {
                for (int i = 0; i < size(); i++) {
                    if (!c.hypothesis.get(i).equals(hypothesis.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean equalsWithout(LogicExpression logicExpression, int i) {
        if (logicExpression instanceof Context) {
            Context c = (Context) logicExpression;
            if (c.size() == size() - 1) {
                for (int j = 0; j < size() - 1; j++) {
                    int q = j < i ? j : j + 1;
                    if (!c.hypothesis.get(j).equals(hypothesis.get(q))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean setEquals(Context context) {
        if (context.size() != size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            LogicExpression e = hypothesis.get(i);
            int c = 0;
            for (int j = 0; j < size(); j++) {
                if (hypothesis.get(j).equals(e)) {
                    c++;
                }
            }
            for (int j = 0; j < size(); j++) {
                if (context.hypothesis.get(j).equals(e)) {
                    c--;
                }
            }
            if (c != 0) {
                return false;
            }
        }
        return true;
    }

    public int indexOf(LogicExpression logicExpression) {
        for (int i = 0; i < hypothesis.size(); i++) {
            if (hypothesis.get(i).equals(logicExpression)) {
                return i;
            }
        }
        return -1;
    }
}
