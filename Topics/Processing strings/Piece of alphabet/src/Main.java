import java.util.Scanner;
import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine().toLowerCase();

        // Sort the characters of the string in ascending order
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        String sortedString = new String(chars);

        // Check if the sorted string is a substring of the ordered English alphabet
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        boolean isAlphabetSubstring = alphabet.contains(sortedString);

        // Output the result
        System.out.println(isAlphabetSubstring);
    }
}
