package parser;

import java.util.regex.Pattern;

public class BaseParser {
    private static final char END = '\0';
    private CharSource source;
    private char ch = 0xffff;

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    protected void setSource(CharSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean test(final byte type) {
        return Character.getType(ch) == type;
    }

    protected boolean test(Pattern pattern) {
        return pattern.matcher(Character.toString(ch)).matches();
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean eof() {
        return take(END);
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected String getDump() {
        return source.dump();
    }
}