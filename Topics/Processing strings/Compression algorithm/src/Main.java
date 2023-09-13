import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String encoded = encodeString(input);
        System.out.println(encoded);
    }

    public static String encodeString(String input) {
        StringBuilder encodedString = new StringBuilder();
        int count = 1;

        for (int i = 0; i < input.length(); i++) {
            if (i + 1 < input.length() && input.charAt(i) == input.charAt(i + 1)) {
                count++;
            } else {
                encodedString.append(input.charAt(i)).append(count);
                count = 1;
            }
        }

        return encodedString.toString();
    }
}