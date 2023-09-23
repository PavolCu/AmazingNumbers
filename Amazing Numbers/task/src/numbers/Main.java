package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.print("""
                Welcome to Amazing Numbers!
                                
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);

        while (true) {
            System.out.print("\nEnter a request: > ");
            String[] array = new java.util.Scanner(System.in).nextLine().split(" ");

            long first = Long.parseLong(array[0]);
            if (0 == first) break;

            long second = 1;
            if (1 < array.length) second = Long.parseLong(array[1]);
            if (0 == second) continue;

            System.out.println();

            if (0 > first || 0 > second) {
                System.out.printf("The %s parameter should be a natural number or zero.\n", 0 > first ? "first" : "second");
                continue;
            }

            List<String> propertiesToCheck = new ArrayList<>();
            for (int i = 2; i < array.length; i++) {
                propertiesToCheck.add(array[i].toLowerCase());
            }

            if (arePropertiesWrong(propertiesToCheck)) continue;

            // Find the nearest number that meets the properties
            long currentNumber = first;
            while (!meetsProperties(currentNumber, propertiesToCheck)) {
                currentNumber++;
            }

            for (long number = currentNumber, outputCount = 0; outputCount < second; number++) {
                if (1 == array.length) {
                    printList(number);
                    outputCount++;
                } else {
                    outputCount += printRow(number, propertiesToCheck);
                }
            }
        }
        System.out.print("Goodbye!");
    }

    private static boolean arePropertiesWrong(List<String> propertiesToCheck) {
        List<String> properties = Arrays.asList("buzz", "duck", "palindromic", "gapful", "spy", "square", "sunny", "even", "odd", "jumping");

        List<String> wrong = new ArrayList<>();

        for (String property : propertiesToCheck) {
            if (!("".equals(property) || properties.contains(property))) {
                wrong.add(property);
            }
        }

        if (wrong.size() != 0) {
            boolean isMultiple = wrong.size() > 1;
            System.out.printf("The propert%s %s %s wrong.\n", isMultiple ? "ies" : "y", wrong.toString().toUpperCase(), isMultiple ? "are" : "is");
            System.out.printf("Available properties: %s\n", properties.toString().toUpperCase());
            return true;
        }

        if (propertiesToCheck.contains("even") && propertiesToCheck.contains("odd") ||
                propertiesToCheck.contains("square") && propertiesToCheck.contains("sunny") ||
                propertiesToCheck.contains("duck") && propertiesToCheck.contains("spy")) {
            System.out.printf("The request contains mutually exclusive properties: %s\n", propertiesToCheck.toString().toUpperCase());
            System.out.println("There are no numbers with these properties.");
            return true;
        }
        return false;
    }

    private static void printList(long number) {
        System.out.println("Properties of " + number);
        System.out.println("        buzz: " + isBuzz(number));
        System.out.println("        duck: " + isDuck(number));
        System.out.println(" palindromic: " + isPalindromic(number));
        System.out.println("      gapful: " + isGapful(number));
        System.out.println("         spy: " + isSpy(number));
        System.out.println("      square: " + isSquare(number));
        System.out.println("       sunny: " + isSunny(number));
        System.out.println("        even: " + isEven(number));
        System.out.println("         odd: " + isOdd(number));
        System.out.println("     jumping: " + isJumping(number));
    }

    private static int printRow(Long number, List<String> propertiesToCheck) {
        StringBuilder output = new StringBuilder();

        if (isBuzz(number)) output.append(", buzz");
        if (isDuck(number)) output.append(", duck");
        if (isPalindromic(number)) output.append(", palindromic");
        if (isGapful(number)) output.append(", gapful");
        if (isSpy(number)) output.append(", spy");
        if (isSquare(number)) output.append(", square");
        if (isSunny(number)) output.append(", sunny");
        if (isEven(number)) output.append(", even");
        if (isOdd(number)) output.append(", odd");
        if (isJumping(number)) output.append(", jumping");

        String strOutput = output.substring(2);

        if (propertiesToCheck.stream().allMatch(strOutput::contains)) {
            System.out.printf("%d is %s\n", number, strOutput);
            return 1;
        }
        return 0;
    }

    private static boolean isBuzz(long number) {
        return number % 7 == 0 || Long.toString(number).endsWith("7");
    }

    private static boolean isDuck(long number) {
        return Long.toString(number).substring(1).contains("0");
    }

    private static boolean isPalindromic(long number) {
        String strNumber = Long.toString(number);
        boolean isPalindromic = true;

        for (int i = 0; i < strNumber.length(); i++) {
            isPalindromic = isPalindromic && strNumber.charAt(i) == strNumber.charAt(strNumber.length() - 1 - i);
        }
        return isPalindromic;
    }

    private static boolean isGapful(long number) {
        String strNumber = Long.toString(number);

        if (strNumber.length() > 2) {
            return number % Long.parseLong(strNumber.charAt(0) + strNumber.substring(strNumber.length() - 1)) == 0;
        }
        return false;
    }

    private static boolean isSpy(long number) {
        long sum = 0, product = 1;

        for (char c : Long.toString(number).toCharArray()) {
            sum += c - '0';
            product *= c - '0';
        }
        return sum == product;
    }

    private static boolean isSquare(long number) {
        long rootNumber = (long) Math.sqrt(number);
        return number == Math.pow(rootNumber, 2);
    }

    private static boolean isSunny(long number) {
        long sunnyRoot = (long) Math.sqrt(++number);
        return number == Math.pow(sunnyRoot, 2);
    }

    private static boolean isEven(long number) {
        return number % 2 == 0;
    }

    private static boolean isOdd(long number) {
        return number % 2 == 1;
    }

    private static boolean isJumping(long number) {
        String strNumber = Long.toString(number);
        int length = strNumber.length();

        // Single-digit numbers are considered Jumping numbers
        if (length == 1) {
            return true;
        }

        for (int i = 1; i < length; i++) {
            int digit1 = strNumber.charAt(i - 1) - '0';
            int digit2 = strNumber.charAt(i) - '0';

            // Check if the adjacent digits differ by 1
            if (Math.abs(digit1 - digit2) != 1) {
                return false; // Not a Jumping number
            }
        }

        return true; // It's a Jumping number
    }

    private static boolean meetsProperties(long number, List<String> propertiesToCheck) {
        // Check if the number meets the specified properties
        if (propertiesToCheck.isEmpty()) {
            return true; // No specific properties requested, so any number is valid
        }

        String strOutput = getPropertiesString(number).toLowerCase();

        return propertiesToCheck.stream().allMatch(strOutput::contains);
    }

    private static String getPropertiesString(long number) {
        StringBuilder output = new StringBuilder();

        if (isBuzz(number)) output.append(", buzz");
        if (isDuck(number)) output.append(", duck");
        if (isPalindromic(number)) output.append(", palindromic");
        if (isGapful(number)) output.append(", gapful");
        if (isSpy(number)) output.append(", spy");
        if (isSquare(number)) output.append(", square");
        if (isSunny(number)) output.append(", sunny");
        if (isEven(number)) output.append(", even");
        if (isOdd(number)) output.append(", odd");
        if (isJumping(number)) output.append(", jumping");

        String strOutput = output.substring(2);

        return strOutput;
    }


}

