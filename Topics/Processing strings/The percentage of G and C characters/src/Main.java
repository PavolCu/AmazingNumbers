import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);

        // Read the input sequence
        String sequence = sc.next().toLowerCase(); // Convert to lowercase to make it case insensitive

        // Calculate number of G and C characters
        int countGC = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (c == 'g' || c == 'c') {
                countGC++;
            }
        }

        // Calculate the percentage of G and C characters
        double result = (double) countGC / sequence.length() * 100;

        // Print the result
        System.out.println(result);
    }
}