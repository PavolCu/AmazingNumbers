import java.util.Scanner;

public class Main {

    /* Fix this method */
    public static void printReverseCharByChar(String s) {
        if (s.length() > 0) {
            System.out.print(s.charAt(s.length() - 1));
            printReverseCharByChar(s.substring(0, s.length() - 1));
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        printReverseCharByChar(scanner.nextLine());
    }
}