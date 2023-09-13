import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);


        String input = scanner.nextLine();

        String result = removeMiddleCharacters(input);
        System.out.println(result);
    }

    public static String removeMiddleCharacters(String str) {
        int length = str.length();
        if (length % 2 == 0) {
            // If the length is even, remove the middle 2 characters
            int midIndex1 = length / 2 - 1;
            int midIndex2 = length / 2;
            return str.substring(0, midIndex1) + str.substring(midIndex2 + 1);
        } else {
            // If the length is odd, remove the middle character
            int midIndex = length / 2;
            return str.substring(0, midIndex) + str.substring(midIndex + 1);
        }
    }
}

