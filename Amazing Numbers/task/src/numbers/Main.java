package numbers;

import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Amazing Numbers!\n");
        printInstructions();
        Set<String> validProperties = new HashSet<>(Arrays.asList("EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SUNNY", "SQUARE"));

        while (true) {
            System.out.print("Enter a request: > ");
            String input = scanner.nextLine().trim();
            if (input.equals("0")) {
                System.out.println("\nGoodbye!");
                break; // Terminate the program
            }

            if (input.isEmpty()) {
                printInstructions();
            } else if (input.startsWith("FIND ")) {
                String[] parts = input.split(" ");
                if (parts.length == 4) {
                    BigInteger start = new BigInteger(parts[0]);
                    if (start.compareTo(BigInteger.ZERO) < 0) {
                        System.out.println("\nThe first parameter should be a natural number or zero.\n");
                        continue;
                    }

                    BigInteger count = new BigInteger(parts[1]);
                    if (count.compareTo(BigInteger.ZERO) <= 0) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                        continue;
                    }

                    String property = parts[2].toUpperCase();
                    if (validProperties.contains(property)) {
                        BigInteger maxCount = BigInteger.valueOf(8); // Default count if not provided
                        try {
                            maxCount = new BigInteger(parts[3]);
                        } catch (NumberFormatException e) {
                            System.out.println("\nThe fourth parameter should be a valid number.\n");
                            continue;
                        }

                        processRangeWithProperty(start, property, validProperties, maxCount);
                    } else {
                        System.out.println("The property [" + property + "] is wrong.");
                        System.out.println("Available properties: " + validProperties);
                    }
                } else {
                    System.out.println("\nInvalid input for FIND request.\n");
                }
            } else {
                String[] parts = input.split(" ");
                BigInteger start = new BigInteger(parts[0]);
                if (start.compareTo(BigInteger.ZERO) < 0) {
                    System.out.println("\nThe first parameter should be a natural number or zero.\n");
                    continue;
                }

                if (parts.length == 1) {
                    processNumber(start, validProperties);
                } else if (parts.length == 2) {
                    try {
                        BigInteger count = new BigInteger(parts[1]);
                        if (count.compareTo(BigInteger.ZERO) <= 0) {
                            System.out.println("\nThe second parameter should be a natural number.\n");
                            continue;
                        }
                        processRange(start, count, validProperties);
                    } catch (NumberFormatException e) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                    }
                } else if (parts.length == 3) {
                    String property = parts[2].toUpperCase();
                    if (validProperties.contains(property)) {
                        try {
                            BigInteger count = new BigInteger(parts[1]);
                            if (count.compareTo(BigInteger.ZERO) <= 0) {
                                System.out.println("\nThe second parameter should be a natural number.\n");
                                continue;
                            }
                            // Objective 7: For two numbers and one property
                            processRangeWithProperty(start, property, validProperties, count);
                        } catch (NumberFormatException e) {
                            System.out.println("\nThe second parameter should be a natural number.\n");
                        }
                    } else {
                        System.out.println("The property [" + property + "] is wrong.");
                        System.out.println("Available properties: " + validProperties);
                    }
                } else if (parts.length >= 3) {
                    List<String> properties = new ArrayList<>();
                    for (int i = 2; i < parts.length; i++) {
                        properties.add(parts[i].toUpperCase());
                    }

                    List<String> incorrectProperties = new ArrayList<>();
                    for (String prop : properties) {
                        if (!validProperties.contains(prop)) {
                            incorrectProperties.add(prop);
                        }
                    }

                    if (incorrectProperties.size() == 1) {
                        System.out.println("The property [" + incorrectProperties.get(0) + "] is wrong.");
                        System.out.println("Available properties: " + validProperties);
                        continue;
                    } else if (incorrectProperties.size() > 1) {
                        System.out.println("The properties " + incorrectProperties + " are wrong.");
                        System.out.println("Available properties: " + validProperties);
                        continue;
                    }

                    if (properties.size() == 2) {
                        String property1 = properties.get(0);
                        String property2 = properties.get(1);

                        if (areMutuallyExclusive(property1, property2)) {
                            Collections.sort(properties); // Sort the properties to ensure consistent order
                            System.out.println("The request contains mutually exclusive properties: " + properties);
                            System.out.println("There are no numbers with these properties");
                            continue;
                        }

                        if (properties.size() == 1) {
                            processRangeWithProperty(start, properties.get(0), validProperties, BigInteger.valueOf(8));
                        } else if (properties.size() == 2) {
                            BigInteger count = new BigInteger(parts[1]);
                            processRangeWithProperties(start, properties.get(0), properties.get(1), validProperties, count);
                        }
                    } else {
                        System.out.println("\nInvalid input.\n");
                    }
                }
            }
        }

        scanner.close(); // Close the scanner before exiting
    }




    public static void printInstructions() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println(" * the first parameter represents a starting number;");
        System.out.println(" * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and a property to search for;");
        System.out.println("- two natural numbers and two properties to search for;");
        System.out.println("- separate the parameters with one space; ");
        System.out.println("- enter 0 to exit.\n");
    }

    public static void processNumber(BigInteger number, Set<String> validProperties) {
        System.out.println("Properties of " + number);
        boolean hasProperties = false;

        for (String property : validProperties) {
            boolean hasProperty = hasProperty(number, property);
            System.out.println("\t" + property.toLowerCase() + ": " + hasProperty);
            if (hasProperty) {
                hasProperties = true;
            }
        }

        if (!hasProperties) {
            System.out.println("\tnone");
        }
    }


    public static void processRange(BigInteger start, BigInteger end, Set<String> validProperties) {
        if (start.compareTo(BigInteger.ZERO) < 0 || end.compareTo(BigInteger.ZERO) < 0) {
            System.out.println("\nThe parameters should be natural numbers or zero.\n");
            return;
        }

        BigInteger current = start;
        boolean isFirstLine = true; // Přidáno pro první řádek

        while (current.compareTo(end) <= 0) {
            List<String> properties = new ArrayList<>();
            for (String property : validProperties) {
                if (hasProperty(current, property)) {
                    properties.add(property.toLowerCase());
                }
            }

            if (!properties.isEmpty()) {
                if (isFirstLine) {
                    // První řádek obsahující titulek
                    System.out.println("Properties of " + current);
                    isFirstLine = false;
                } else {
                    System.out.print(current + " is ");
                    System.out.println(String.join(", ", properties));
                }
            }

            current = current.add(BigInteger.ONE);
        }
    }




    public static void processRangeWithProperty(BigInteger start, String property, Set<String> validProperties, BigInteger count) {
        BigInteger number = start;
        BigInteger processed = BigInteger.ZERO;

        while (processed.compareTo(count) < 0) {
            if (hasProperty(number, property)) {
                printPropertiesInLine(number, validProperties);
                processed = processed.add(BigInteger.ONE);
            }
            number = number.add(BigInteger.ONE);
        }
    }

    public static void processRangeWithProperties(BigInteger start, String property1, String property2, Set<String> validProperties, BigInteger count) {
        BigInteger number = start;
        BigInteger processed = BigInteger.ZERO;

        while (processed.compareTo(count) < 0) {
            Set<String> properties = new HashSet<>();
            for (String property : validProperties) {
                if (hasProperty(number, property)) {
                    properties.add(property.toLowerCase());
                }
            }

            if (properties.contains(property1.toLowerCase()) && properties.contains(property2.toLowerCase())) {
                System.out.print(String.format("%" + number.toString().length() + "s is ", number) + String.join(", ", properties));
                System.out.println();
                processed = processed.add(BigInteger.ONE);
            }

            number = number.add(BigInteger.ONE);
        }
    }

    public static void printPropertiesInLine(BigInteger number, Set<String> validProperties) {
        List<String> properties = new ArrayList<>();
        for (String property : validProperties) {
            if (hasProperty(number, property)) {
                properties.add(property.toLowerCase());
            }
        }

        if (!properties.isEmpty()) {
            System.out.print(number + " is ");
            System.out.println(String.join(", ", properties));
        }
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
            case "SUNNY":
                return isSunnyNumber(number);
            case "SQUARE":
                return isSquareNumber(number);
            default:
                return false;
        }
    }

    // ... Other property-checking methods ...

    public static boolean isBuzzNumber(BigInteger number) {
        return number.mod(new BigInteger("7")).equals(BigInteger.ZERO) || number.mod(new BigInteger("10")).equals(new BigInteger("7"));
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
        for (; number.compareTo(BigInteger.ZERO) > 0; number = number.divide(new BigInteger("10"))) {
            BigInteger digit = number.mod(new BigInteger("10"));
            reverseNumber = reverseNumber.multiply(new BigInteger("10")).add(digit);
        }
        return originalNumber.equals(reverseNumber);
    }

    public static boolean isGapfulNumber(BigInteger number) {
        if (number.compareTo(new BigInteger("100")) < 0) {
            return false;
        } else {
            BigInteger firstDigit = new BigInteger(number.toString().substring(0, 1));
            BigInteger lastDigit = number.mod(new BigInteger("10"));
            String concatenated = firstDigit.toString() + lastDigit.toString();
            BigInteger concatenatedNumber = new BigInteger(concatenated);
            return number.mod(concatenatedNumber).equals(BigInteger.ZERO);
        }
    }

    public static boolean isSpyNumber(BigInteger number) {
        String numStr = number.toString();
        int sum = 0;
        int product = 1;

        for (int i = 0; i < numStr.length(); ++i) {
            char digitChar = numStr.charAt(i);
            int digit = Character.getNumericValue(digitChar);
            sum += digit;
            product *= digit;
        }

        return sum == product;
    }

    public static boolean isSunnyNumber(BigInteger number) {
        BigInteger nextNumber = number.add(BigInteger.ONE);
        double sqrt = Math.sqrt(nextNumber.doubleValue());
        return sqrt == (int) sqrt;
    }

    public static boolean isSquareNumber(BigInteger number) {
        double sqrt = Math.sqrt(number.doubleValue());
        return sqrt == (int) sqrt;
    }


    public static boolean areMutuallyExclusive(String property1, String property2) {
        List<String> pair1 = Arrays.asList("EVEN", "ODD");
        List<String> pair2 = Arrays.asList("DUCK", "SPY");
        List<String> pair3 = Arrays.asList("SUNNY", "SQUARE");

        if (pair1.contains(property1) && pair1.contains(property2)) return true;
        if (pair2.contains(property1) && pair2.contains(property2)) return true;
        if (pair3.contains(property1) && pair3.contains(property2)) return true;

        return false;
    }

    public static void findNumbersWithProperty(String property, BigInteger start, BigInteger count) {
        BigInteger number = start;
        BigInteger foundCount = BigInteger.ZERO;

        while (foundCount.compareTo(count) < 0) {
            if (hasProperty(number, property)) {
                System.out.println(number + " has property " + property);
                foundCount = foundCount.add(BigInteger.ONE);
            }
            number = number.add(BigInteger.ONE);
        }
    }

}