package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Main class contains the main method that reads user input and prints the properties of numbers.
 * The program supports the following requests:
 * - enter a natural number to know its properties;
 * - enter two natural numbers to obtain the properties of the list:
 *   * the first parameter represents a starting number;
 *   * the second parameter shows how many consecutive numbers are to be printed;
 * - two natural numbers and properties to search for;
 * - a property preceded by minus must not be present in numbers;
 * - separate the parameters with one space;
 * - enter 0 to exit.
 */

public class Main {
    public enum Property {
        BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, EVEN, ODD, JUMPING, HAPPY, SAD
    }

    /**
     * The main method reads user input and prints the properties of numbers.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.print("""
            Welcome to Amazing Numbers!
                            
            Supported requests:
            - enter a natural number to know its properties;
            - enter two natural numbers to obtain the properties of the list:
              * the first parameter represents a starting number;
              * the second parameter shows how many consecutive numbers are to be printed;
            - two natural numbers and properties to search for;
            - a property preceded by minus must not be present in numbers;
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

            List<Property> propertiesToCheck = new ArrayList<>();
            List<Property> exclusionProperties = new ArrayList<>();

            for (int i = 2; i < array.length; i++) {
                String property = array[i].toLowerCase();

                try {
                    if (property.startsWith("-")) {
                        exclusionProperties.add(Property.valueOf(property.substring(1).toUpperCase()));
                    } else {
                        propertiesToCheck.add(Property.valueOf(property.toUpperCase()));
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("The property [" + property + "] is wrong.");
                    System.out.println("Available properties: " + Arrays.toString(Property.values()));

                }
            }

            if (arePropertiesWrong(propertiesToCheck, exclusionProperties)) continue;

            // Find the nearest number that meets the properties
            long currentNumber = first;
            while (!meetsProperties(currentNumber, propertiesToCheck, exclusionProperties)) {
                currentNumber++;
            }

            for (long number = currentNumber, outputCount = 0; outputCount < second; number++) {
                if (1 == array.length) {
                    printList(number);
                    outputCount++;
                } else {
                    outputCount += printRow(number, propertiesToCheck, exclusionProperties);
                }
            }
        }
        System.out.print("Goodbye!");
    }

    /**
     * Checks if the properties are wrong.
     * @param propertiesToCheck the properties to check
     * @param exclusionProperties the exclusion properties
     * @return true if the properties are wrong, false otherwise
     */
    private static boolean arePropertiesWrong(List<Property> propertiesToCheck, List<Property> exclusionProperties) {
        List<Property> properties = Arrays.asList(Property.values());

        List<Property> wrong = new ArrayList<>();

        for (Property property : propertiesToCheck) {
            if (!properties.contains(property)) {
                wrong.add(property);
            }
        }

        if (!wrong.isEmpty()) {
            boolean isMultiple = wrong.size() > 1;
            System.out.printf("The propert%s %s %s wrong.\n", isMultiple ? "ies" : "y", wrong.toString().toUpperCase(), isMultiple ? "are" : "is");
            System.out.printf("Available properties: %s\n", Arrays.toString(Property.values()));
            return true;
        }

        if ((propertiesToCheck.contains(Property.EVEN) && propertiesToCheck.contains(Property.ODD)) ||
                (propertiesToCheck.contains(Property.SQUARE) && propertiesToCheck.contains(Property.SUNNY)) ||
                (propertiesToCheck.contains(Property.DUCK) && propertiesToCheck.contains(Property.SPY)) ||
                (propertiesToCheck.contains(Property.HAPPY) && propertiesToCheck.contains(Property.SAD))) {
            System.out.printf("The request contains mutually exclusive properties: %s, \n", propertiesToCheck);
            System.out.println("There are no numbers with these properties.");
            return true;
        }

        // Check for mutually exclusive exclusion properties
        for (Property exclusionProperty : exclusionProperties) {
            if (propertiesToCheck.contains(exclusionProperty)) {
                System.out.printf("The request contains mutually exclusive properties: [%s, -%s]\n", exclusionProperty, exclusionProperty);
                System.out.println("There are no numbers with these properties.");
                return true;
            }
        }

        // Check for mutually exclusive properties: -ODD and -EVEN
        if (exclusionProperties.contains(Property.ODD) && exclusionProperties.contains(Property.EVEN)) {
            System.out.println("The request contains mutually exclusive properties: [-ODD, -EVEN]");
            System.out.println("There are no numbers with these properties.");
            return true;
        }

        return false;
    }

    /**
     * Prints the properties of a number.
     * @param number the number
     */
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
        System.out.println("       happy: " + isHappy(number));
        System.out.println("         sad: " + isSad(number));
    }

    /**
     * Prints the properties of a number.
     * @param number the number
     * @param propertiesToCheck the properties to check
     * @param exclusionProperties the exclusion properties
     * @return 1 if the number meets the properties, 0 otherwise
     */
    private static int printRow(Long number, List<Property> propertiesToCheck, List<Property> exclusionProperties) {
        String propertiesString = getPropertiesString(number).toLowerCase();

        for (Property exclusionProperty : exclusionProperties) {
            if (propertiesString.contains(exclusionProperty.toString().toLowerCase())) {
                return 0; // Do not print this number
            }
        }

        if (propertiesToCheck.stream().allMatch(prop -> propertiesString.contains(prop.toString().toLowerCase()))) {
            System.out.printf("%15d is %s\n", number, propertiesString);
            return 1;
        }
        return 0;
    }

    /**
     * Returns true if the number is a Buzz number.
     * @param number the number
     * @return true if the number is a Buzz number, false otherwise
     */
    private static boolean isBuzz(long number) {
        return number % 7 == 0 || Long.toString(number).endsWith("7");
    }

    /**
     * Returns true if the number is a Duck number.
     * @param number the number
     * @return true if the number is a Duck number, false otherwise
     */
    private static boolean isDuck(long number) {
        return Long.toString(number).substring(1).contains("0");
    }

    /**
     * Returns true if the number is a Palindromic number.
     * @param number the number
     * @return true if the number is a Palindromic number, false otherwise
     */
    private static boolean isPalindromic(long number) {
        String strNumber = Long.toString(number);
        boolean isPalindromic = true;

        for (int i = 0; i < strNumber.length(); i++) {
            isPalindromic = isPalindromic && strNumber.charAt(i) == strNumber.charAt(strNumber.length() - 1 - i);
        }
        return isPalindromic;
    }

    /**
     * Returns true if the number is a Gapful number.
     * @param number the number
     * @return true if the number is a Gapful number, false otherwise
     */
    private static boolean isGapful(long number) {
        String strNumber = Long.toString(number);

        if (strNumber.length() > 2) {
            return number % Long.parseLong(strNumber.charAt(0) + strNumber.substring(strNumber.length() - 1)) == 0;
        }
        return false;
    }

    /**
     * Returns true if the number is a Spy number.
     * @param number the number
     * @return true if the number is a Spy number, false otherwise
     */
    private static boolean isSpy(long number) {
        long sum = 0, product = 1;

        for (char c : Long.toString(number).toCharArray()) {
            sum += c - '0';
            product *= c - '0';
        }
        return sum == product;
    }

    /**
     * Returns true if the number is a Square number.
     * @param number the number
     * @return true if the number is a Square number, false otherwise
     */
    private static boolean isSquare(long number) {
        long rootNumber = (long) Math.sqrt(number);
        return number == Math.pow(rootNumber, 2);
    }

    /**
     * Returns true if the number is a Sunny number.
     * @param number the number
     * @return true if the number is a Sunny number, false otherwise
     */
    private static boolean isSunny(long number) {
        long sunnyRoot = (long) Math.sqrt(++number);
        return number == Math.pow(sunnyRoot, 2);
    }

    /**
     * Returns true if the number is an Even number.
     * @param number the number
     * @return true if the number is an Even number, false otherwise
     */
    private static boolean isEven(long number) {
        return number % 2 == 0;
    }

    /**
     * Returns true if the number is an Odd number.
     * @param number the number
     * @return true if the number is an Odd number, false otherwise
     */
    private static boolean isOdd(long number) {
        return number % 2 == 1;
    }

    /**
     * Returns true if the number is a Jumping number.
     * @param number the number
     * @return true if the number is a Jumping number, false otherwise
     */
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

    /**
     * Returns true if the number is a Happy number.
     * @param number the number
     * @return true if the number is a Happy number, false otherwise
     */
    private static boolean isNumber(long number, int target) {
        int count = 0;
        while (number != target && count < 20) {
            long sum = 0;
            while (number > 0) {
                long digit = number % 10;
                sum += digit * digit;
                number /= 10;
            }
            number = sum;
            count++;
        }
        return number == target;
    }

    /**
     * Returns true if the number is a Happy number.
     * @param number the number
     * @return true if the number is a Happy number, false otherwise
     */
    private static boolean isHappy(long number) {
        return isNumber(number, 1);
    }

    /**
     * Returns true if the number is a Sad number.
     * @param number the number
     * @return true if the number is a Sad number, false otherwise
     */
    private static boolean isSad(long number) {
        return isNumber(number, 4);
    }

    /**
     * Returns true if the number meets the properties.
     * @param number the number
     * @param propertiesToCheck the properties to check
     * @param exclusionProperties the exclusion properties
     * @return true if the number meets the properties, false otherwise
     */
    private static boolean meetsProperties(long number, List<Property> propertiesToCheck, List<Property> exclusionProperties) {
        String strOutput = getPropertiesString(number).toLowerCase();

        for (Property exclusionProperty : exclusionProperties) {
            if (strOutput.contains(exclusionProperty.toString().toLowerCase())) {
                return false; // This number has an exclusion property
            }
        }

        return propertiesToCheck.stream().allMatch(prop -> strOutput.contains(prop.toString().toLowerCase()));
    }


    /**
     * Returns the properties of a number as a string.
     * @param number the number
     * @return the properties of a number as a string
     */
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
        if (isHappy(number)) output.append(" , happy");
        if (isSad(number)) output.append(" , sad");

        return output.substring(2);
    }
}

