import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        //Read input
        String inputString = scanner.nextLine();
        String substring = scanner.nextLine();

        //Initialize a counter for substring
        int occurenceCount = 0;

        //Iterate through the string and search for the substring
        for (int i = 0; i <= inputString.length() - substring.length(); i++) {
            if (inputString.substring(i, i + substring.length()).equals(substring)) {
                occurenceCount++;
                i += substring.length() - 1;

            }
        }
        System.out.println(occurenceCount);
    }
}