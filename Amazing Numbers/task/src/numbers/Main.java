package numbers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Amazing Numbers!\n");
        printInstructions();

        List<String> validProperties = Arrays.asList("EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY");
        while (true) {
            System.out.print("Enter a request: > ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("\nGoodbye!");
                break;
            } else if (input.isEmpty()) {
                    printInstructions();
            } else if (input.matches("-\\d+")) {
                System.out.println("\nThe first parameter should be a natural number or zero.\n");
            } else if (input.matches("\\d+ \\d+ \\w+")) { // Handle two numbers followed by a property
                String[] parts = input.split(" ");
                BigInteger start;
                BigInteger count;

                try {
                        start = new BigInteger(parts[0]);
                        count = new BigInteger(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                        continue;
                    }

                boolean isFirstNegative = start.compareTo(BigInteger.ZERO) < 0;
                boolean isSecondNegative = count.compareTo(BigInteger.ZERO) < 0;

                if (isFirstNegative && isSecondNegative) {
                    System.out.println("The first parameter should be a natural number.");
                    System.out.println("The second parameter should be a natural number.");
                    continue; // Požiadať o ďaľší vstup
                } else if (isFirstNegative) {
                    System.out.println("The first parameter should be a natural number.");
                    continue; //Požiadať o ďaľší vstup
                } else if (isSecondNegative) {
                    System.out.println("The second parameter should be a natural number.");
                    continue; // Požiadať o ďaľší vstup
                }


                if (!isNatural(start) || !isNatural(count)) {
                        System.out.println("\nThe first two parameters should be natural numbers.\n");
                        continue; // Požiadať o ďaľší vstup
                    }

                    String property = parts[2].toUpperCase();

                    if (!validProperties.contains(property)) {
                        System.out.println("The property [" + property + "] is wrong.");
                        System.out.println("Available properties: " + validProperties);
                        continue; // ask on anothe input
                    }

                    BigInteger found = BigInteger.ZERO;
                    for (BigInteger i = start; found.compareTo(count) < 0; i = i.add(BigInteger.ONE)) {
                        if (hasProperty(i, property)) {
                            printPropertiesInLine(i);
                            found = found.add(BigInteger.ONE);
                        }
                    }
                } else if (input.matches("\\d+ [-]?\\d+")) { // Handle two numbers
                String[] parts = input.split(" ");
                BigInteger start = new BigInteger(parts[0]);
                BigInteger count = new BigInteger(parts[1]);

                if (count.compareTo(BigInteger.ZERO) < 0) {
                    System.out.println("The second parameter should be a natural number.");
                    continue;
                }

                for (BigInteger i = start; i.compareTo(start.add(count)) < 0; i = i.add(BigInteger.ONE)) {
                    printPropertiesInLine(i);
                }

        } else if (input.matches("\\d+ [-]?\\w+")) { // Handle number followed by a word
                    String[] parts = input.split(" ");
                    BigInteger number = new BigInteger(parts[0]);
                    if (number.compareTo(BigInteger.ZERO) > 0) {
                        processNumber(number);
                    } else {
                        System.out.println("\nThe first parameter should be a natural number or zero.\n");
                    }
                } else if (input.matches("\\d+")) {
                    BigInteger number = new BigInteger(input);
                    if (number.compareTo(BigInteger.ZERO) > 0) {
                        processNumber(number);
                    } else {
                        System.out.println("\nThe first parameter should be a natural number or zero.\n");
                    }
                } else {
                String[] parts = input.split(" ");
                if (parts.length == 3 && !validProperties.contains(parts[2].toUpperCase())) {
                    System.out.println("The property " + parts[2] + " is wrong.");
                    System.out.println("Available properties: " + validProperties);
                } else if (parts.length > 0 && !parts[0].matches("\\d+")) {
                    System.out.println("\nThe first parameter should be a natural number or zero.\n");
                } else if (parts.length > 1 && !parts[1].matches("\\d+")) {
                    System.out.println("\nThe second parameter should be a natural number.\n");
                } else {
                    System.out.println("\nInvalid input.\n");
                }
            }

        }
            scanner.close();
        }

        public static void printPropertiesInLine(BigInteger number) {
            StringBuilder properties = new StringBuilder();
            if (isBuzzNumber(number)) properties.append("buzz, ");
            if (isDuckNumber(number)) properties.append("duck, ");
            if (isPalindromicNumber(number)) properties.append("palindromic, ");
            if (isGapfulNumber(number)) properties.append("gapful, ");
            if (isSpyNumber(number)) properties.append("spy, ");
            if (number.mod(new BigInteger("2")).equals(BigInteger.ZERO)) properties.append("even, ");
            else properties.append("odd, ");

            if (properties.length() > 0) {
                properties.setLength(properties.length() - 2);
            }

            System.out.println("\t" + number + " is " + properties);
        }

        public static void printInstructions() {
            System.out.println("Supported requests:");
            System.out.println("- enter a natural number to know its properties;");
            System.out.println("- enter two natural numbers to obtain the properties of the List:");
            System.out.println(" * the first parameter represents a starting number;");
            System.out.println(" * the second parameter shows how many consecutive numbers are to be printed;");
            System.out.println("- two natural numbers and a property to search for;");
            System.out.println("- separate the parameters with one space;");
            System.out.println("- enter 0 to exit.\n");
        }

        public static void processNumber(BigInteger number) {
            // Check properties
            boolean isEven = number.mod(new BigInteger("2")).equals(BigInteger.ZERO);
            boolean isOdd = !isEven;
            boolean isBuzz = isBuzzNumber(number);
            boolean isDuck = isDuckNumber(number);
            boolean isPalindromic = isPalindromicNumber(number);
            boolean isGapful = isGapfulNumber(number);
            boolean isSpy = isSpyNumber(number);

            // Print properties
            System.out.println("Properties of " + number);
            System.out.println("\tbuzz: " + isBuzz);
            System.out.println("\tduck: " + isDuck);
            System.out.println("\tpalindromic: " + isPalindromic);
            System.out.println("\tgapful: " + isGapful);
            System.out.println("\tspy: " + isSpy);
            System.out.println("\teven: " + isEven);
            System.out.println("\todd: " + isOdd + "\n");
        }

        public static boolean isBuzzNumber(BigInteger number) {
            return (number.mod(new BigInteger("7")).equals(BigInteger.ZERO)) || (number.mod(new BigInteger("10")).equals(new BigInteger("7")));
        }

        public static boolean isDuckNumber(BigInteger number) {
            while (number.compareTo(BigInteger.ZERO) > 0) {
                BigInteger digit = number.mod(new BigInteger("10"));
                if (digit.equals(BigInteger.ZERO)) {
                    return true;
                }
                number = number.divide(new BigInteger("10"));
            }
            return false;
        }

        public static boolean isPalindromicNumber(BigInteger number) {
            BigInteger originalNumber = number;
            BigInteger reverseNumber = BigInteger.ZERO;

            while (number.compareTo(BigInteger.ZERO) > 0) {
                BigInteger digit = number.mod(new BigInteger("10"));
                reverseNumber = reverseNumber.multiply(new BigInteger("10")).add(digit);
                number = number.divide(new BigInteger("10"));
            }
            return originalNumber.equals(reverseNumber);
        }

        public static boolean isGapfulNumber(BigInteger number) {
            if (number.compareTo(new BigInteger("100")) < 0) {
                return false;
            }
            BigInteger firstDigit = new BigInteger(number.toString().substring(0, 1));
            BigInteger lastDigit = number.mod(new BigInteger("10"));
            BigInteger concatenated = new BigInteger(firstDigit.toString() + lastDigit.toString());

            return number.mod(concatenated).equals(BigInteger.ZERO);
        }

        public static boolean isSpyNumber(BigInteger number) {
            String numStr = number.toString();
            int sum = 0;
            int product = 1;

            for (int i = 0; i < numStr.length(); i++) {
                char digitChar = numStr.charAt(i);
                int digit = Character.getNumericValue(digitChar);
                sum += digit;
                product *= digit;
            }

            return sum == product;
        }

        public static boolean hasProperty(BigInteger number, String property) {
            switch (property.toUpperCase()) {
                case "EVEN":
                    return number.mod(new BigInteger("2")).equals(BigInteger.ZERO);
                case "ODD":
                    return !number.mod(new BigInteger("2")).equals(BigInteger.ZERO);
                case "BUZZ":
                    return isBuzzNumber(number);
                case "DUCK":
                    return isDuckNumber(number);
                case "PALINDROMIC":
                    return isPalindromicNumber(number);
                case "GAPFUL":
                    return isGapfulNumber(number);
                case "SPY":
                    return isSpyNumber(number);
                default:
                    return false;
            }
        }
        public static boolean isNatural(BigInteger number) {
            return number.compareTo(BigInteger.ZERO) > 0;
        }
    }
