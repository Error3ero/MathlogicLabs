package parser;

import expressions.*;

import java.util.regex.Pattern;

public class Parser extends BaseParser {
    public LogicExpression parse(String expression) {
        setSource(new StringSource(expression.replaceAll("->", "→").replaceAll("\\|-", "⊢")));
        return getExpression();
    }

    public Parser() {
        super(new StringSource("\0"));
    }

    private LogicExpression getExpression() {
        final LogicExpression result = parseProof();
        if (eof()) {
            return result;
        }
        throw error("End of expression expected: " + getDump());
    }

    private LogicExpression parseProof() {
        skipWhitespace();
        LogicExpression context = parseContext();
        if (take('⊢')) {
            final LogicExpression expression = parseImplication();
            if (!(context instanceof Context)) {
                Context ctx = new Context();
                ctx.addHypothesis(context);
                return new Proof(ctx, expression);
            }
            return new Proof((Context) context, expression);
        }
        return context;
    }

    private LogicExpression parseContext() {
        skipWhitespace();
        final Context context = new Context();
        LogicExpression hypothesis = parseImplication();
        skipWhitespace();
        while (take(',')) {
            context.addHypothesis(hypothesis);
            hypothesis = parseImplication();
        }
        if (context.isEmpty()){
            if (hypothesis instanceof Variable && hypothesis.getNode().isEmpty()) {
                return context;
            }
            return hypothesis;
        }
        context.addHypothesis(hypothesis);
        return context;
    }

    private LogicExpression parseImplication() {
        skipWhitespace();
        final LogicExpression first = parseDisjunction();
        skipWhitespace();
        if (take('→')) {
            final LogicExpression second = parseImplication(); // NOTE: or Implication?
            skipWhitespace();
            return new Implication(first, second);
        }
        return first;
    }

    private LogicExpression parseDisjunction() {
        skipWhitespace();
        LogicExpression first = parseConjunction();
        skipWhitespace();
        if (take('|')) {
            LogicExpression second = parseConjunction();
            skipWhitespace();
            while (take('|')) {
                first = new Disjunction(first, second);
                skipWhitespace();
                second = parseConjunction();
            }
            return new Disjunction(first, second);
        }
        return first;
    }

    private LogicExpression parseConjunction() {
        skipWhitespace();
        LogicExpression first = parseNegation();
        skipWhitespace();
        if (take('&')) {
            LogicExpression second = parseNegation();
            skipWhitespace();
            while (take('&')) {
                first = new Conjunction(first, second);
                skipWhitespace();
                second = parseNegation();
            }
            return new Conjunction(first, second);
        }
        return first;
    }

    private LogicExpression parseNegation() {
        skipWhitespace();
        int c = 0;
        while (take('!')) {
            c++;
        }
        LogicExpression result = parseUnary();
        for (int i = 1; i <= c; i++) {
            result = new Negation(result);
        }
        return result;
    }

    private LogicExpression parseUnary() {
        skipWhitespace();
        LogicExpression result;
        if (take('(')) {
            result = parseImplication();
            if (!take(')')) {
                throw error("Closing parenthesis expected");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            while (test(Pattern.compile("[A-Z0-9" + (char) 39 + "]"))) {
                sb.append(take());
            }
            result = new Variable(sb.toString());
        }
        return result;
    }

    private void skipWhitespace() {
        while (take(' ') || take('\r') || take('\n') || take('\t')) {
            // skip
        }
    }
}