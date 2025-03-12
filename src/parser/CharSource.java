package parser;

public interface CharSource {
    boolean hasNext();
    char next();
    char checkNext();
    int getPos();
    int size();
    String dump();
    IllegalArgumentException error(String message);
}