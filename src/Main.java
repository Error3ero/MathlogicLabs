import parser.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        task2();
    }

    public static void task1() {
        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);
        System.out.println(parser.parse(scanner.nextLine()).getNode());
    }

    public static void task2() {
        ProofProcessor.processProofs();
    }
}
