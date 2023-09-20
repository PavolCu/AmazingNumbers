package numbers;

import java.math.BigInteger;
import java.util.*;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        // Inicializácia scanneru pre vstup od používateľa
        Scanner scanner = new Scanner(System.in);

        // Vypíše uvítaciu správu a inštrukcie
        System.out.println("Welcome to Amazing Numbers!\n");
        printInstructions();

        // Definuje platné vlastnosti pre čísla
        Set<String> validProperties = new HashSet<>(Arrays.asList("EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SUNNY", "SQUARE"));

        while (true) {
            // Požiada používateľa o vstup
            System.out.print("Enter a request: > ");
            String input = scanner.nextLine().trim();

            // Spracuje prípad, keď chce používateľ ukončiť program
            if (input.equals("0")) {
                System.out.println("\nGoodbye!");
                break; // Ukončí program
            }

            // Spracuje prípad, keď je vstup prázdny, a vypíše inštrukcie znova
            if (input.isEmpty()) {
                printInstructions();
            } else if (input.startsWith("FIND ")) {
                // Spracuje "FIND" požiadavku, ktorá vyhľadáva vlastnosti v rozsahu
                String[] parts = input.split(" ");
                if (parts.length == 4) {
                    // Rozparsuje vstupné parametre
                    BigInteger start = new BigInteger(parts[0]);
                    if (start.compareTo(BigInteger.ZERO) < 0) {
                        System.out.println("\nThe first parameter should be a natural number or zero..\n");
                        continue;
                    }

                    BigInteger count = new BigInteger(parts[1]);
                    if (count.compareTo(BigInteger.ZERO) <= 0) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                        continue;
                    }

                    String property = parts[2].toUpperCase();
                    if (!validProperties.contains(property)) {
                        System.out.println("The property [" + property + "] is wrong.");
                        System.out.println("Available properties: " + validProperties);
                        continue;
                    }

                    BigInteger maxCount = BigInteger.valueOf(8); // Predvolený počet, ak nie je uvedený
                    try {
                        maxCount = new BigInteger(parts[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("\nŠtvrtý parameter by mal byť platné číslo.\n");
                        continue;
                    }

                    // Spracuje rozsah so zadanou vlastnosťou
                    processRangeWithProperty(start, property, validProperties, maxCount);
                } else {
                    System.out.println("\nNeplatný vstup pre FIND požiadavku.\n");
                }
            } else {
                // Spracuje ostatné prípady vstupu (napr. jedno číslo, rozsah čísel, viacero vlastností)
                String[] parts = input.split(" ");
                BigInteger start = new BigInteger(parts[0]);
                if (start.compareTo(BigInteger.ZERO) < 0) {
                    System.out.println("\nThe first parameter should be a natural number or zero.\n");
                    continue;
                }

                if (parts.length == 1) {
                    // Spracuje jedno číslo a jeho vlastnosti
                    processNumber(start, validProperties);
                } else if (parts.length == 2) {
                    try {
                        // Spracuje rozsah čísel s počítaním
                        BigInteger numCount = new BigInteger(parts[1]);
                        if (numCount.compareTo(BigInteger.ZERO) <= 0) {
                            System.out.println("\nThe second parameter should be a natural number.\n");
                            continue;
                        }
                        processRangeWithTwoNumbers(start, numCount, validProperties);
                    } catch (NumberFormatException e) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                    }
                } else if (parts.length == 3) {
                    // Spracuje prípad, keď sú zadané dve čísla a jedna vlastnosť
                    String property = parts[2].toUpperCase();
                    if (!validProperties.contains(property)) {
                        System.out.println("The property [" + property + "] is wrong.");
                        System.out.println("Available properties: " + validProperties);
                        continue;
                    }
                    try {
                        BigInteger count = new BigInteger(parts[1]);
                        if (count.compareTo(BigInteger.ZERO) <= 0) {
                            System.out.println("\nThe second parameter should be a natural number.\n");
                            continue;
                        }
                        // Spracuje rozsah so zadanou vlastnosťou
                        processRangeWithProperty(start, property, validProperties, count);
                    } catch (NumberFormatException e) {
                        System.out.println("\nDThe second parameter should be a natural number.\n");
                    }
                } else if (parts.length >= 3) {
                    // Spracuje viacero vlastností
                    List<String> properties = new ArrayList<>();
                    for (int i = 2; i < parts.length; i++) {
                        properties.add(parts[i].toUpperCase());
                    }

                    // Kontroluje nesprávne vlastnosti
                    List<String> incorrectProperties = new ArrayList<>();
                    for (String prop : properties) {
                        if (!validProperties.contains(prop)) {
                            incorrectProperties.add(prop);
                        }
                    }
                    //Vypíše chybové hlásenie, ak sú nesprávne vlastnosti
                    if (!incorrectProperties.isEmpty()) {
                        if (incorrectProperties.size() == 1) {
                            System.out.println("The property [" + incorrectProperties.get(0) + "] is wrong.");
                        } else {
                            System.out.println("The properties " + incorrectProperties + " are wrong.");
                        }
                        System.out.println("Available properties: " + validProperties);
                        continue;
                    }

                    // Pokračuje v ďalšom spracovaní vstupu
                    if (properties.size() == 2) {
                        //Spracuje prípady s dvoma vlastnosťami
                        String property1 = properties.get(0);
                        String property2 = properties.get(1);

                        if (areMutuallyExclusive(property1, property2)) {
                            Collections.sort(properties); //Upravi poradie vlastnosti pre konzistentné usporiadanie
                            System.out.println("The request contains mutually exclusive properties: " + properties);
                            System.out.println("There are no numbers with these properties.");
                            continue;
                        }

                        if (properties.size() == 1) {
                            //Spracuje rozsah s jednou vlastnosťou
                            processRangeWithProperty(start, properties.get(0), validProperties, BigInteger.valueOf(8));
                        } else if (properties.size() == 2) {
                            //spracuje rozsah s dvoma vlastnosťami
                            BigInteger count = new BigInteger(parts[1]);
                            processRangeWithProperties(start, properties.get(0), properties.get(1), validProperties, count);
                        }
                    } else {
                        System.out.println("\nInvalid input\n");
                    }
                }
            }
        }

        // Uzavrie scanner pred ukončením programu
        scanner.close();
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
        Map<String, Boolean> propertyMap = new LinkedHashMap<>();

        for (String property : validProperties) {
            boolean hasProperty = hasProperty(number, property);
            propertyMap.put(property.toLowerCase(), hasProperty);
        }

        for (Map.Entry<String, Boolean> entry : propertyMap.entrySet()) {
            String propName = entry.getKey();
            boolean propValue = entry.getValue();
            System.out.println("\t" + propName + ": " + propValue);
        }
    }

    public static void processRangeWithProperty(BigInteger start, String property, Set<String> validProperties, BigInteger count) {
        BigInteger number = start;
        BigInteger processed = BigInteger.ZERO;

        while (processed.compareTo(count) < 0) {
            List<String> properties = new ArrayList<>();
            for (String prop : validProperties) {
                if (hasProperty(number, prop)) {
                    properties.add(prop.toLowerCase());
                }
            }

            if (properties.contains(property.toLowerCase())) {
                // Vypíšeme číslo s požadovanou vlastnosťou
                System.out.print(number + " is " + property.toLowerCase());

                // Doplníme ďalšie vlastnosti ku danej vlastnosti
                for (String prop : properties) {
                    if (!prop.equals(property.toLowerCase())) {
                        System.out.print(", " + prop);
                    }
                }
                System.out.println();

                processed = processed.add(BigInteger.ONE);
            }

            number = number.add(BigInteger.ONE);
        }
    }


// ...

    public static void processRangeWithProperties(BigInteger start, String property1, String property2, Set<String> validProperties, BigInteger count) {
        BigInteger number = start;
        BigInteger processed = BigInteger.ZERO;
        BigInteger skipped = BigInteger.ZERO;

        while (processed.compareTo(count) < 0) {
            List<String> properties = new ArrayList<>();
            for (String prop : validProperties) {
                if (hasProperty(number, prop)) {
                    properties.add(prop.toLowerCase());
                }
            }

            boolean hasProperty1 = properties.contains(property1.toLowerCase());
            boolean hasProperty2 = properties.contains(property2.toLowerCase());

            if (hasProperty1 && hasProperty2) {
                String numberStr = String.format("%" + number.toString().length() + "s", number);
                String propertyStr = String.join(", ", properties);

                System.out.println(numberStr + " is " + propertyStr);
                processed = processed.add(BigInteger.ONE);
            }

            // Move to the next number
            number = number.add(BigInteger.ONE);

            // Skip numbers that don't meet both property1 and property2
            while (!hasProperty(number, property1) || !hasProperty(number, property2)) {
                number = number.add(BigInteger.ONE);
                skipped = skipped.add(BigInteger.ONE);
            }

            // Break if it goes too far or if we've reached the count
            if (skipped.compareTo(count) >= 2 || processed.compareTo(count) >= count.intValue()) {
                break;
            }
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

    // Prídame túto metódu pre spracovanie rozsahu s dvoma číslami a dvoma vlastnosťami
    public static void processRangeWithTwoNumbers(BigInteger start, BigInteger numCount, Set<String> validProperties) {
        BigInteger number = start;
        BigInteger processed = BigInteger.ZERO;

        while (processed.compareTo(numCount) < 0) {
            List<String> properties = new ArrayList<>();
            for (String prop : validProperties) {
                if (hasProperty(number, prop)) {
                    properties.add(prop.toLowerCase());
                }
            }

            if (!properties.isEmpty()) {
                System.out.print(number + " is " + String.join(", ", properties));
                System.out.println();

                processed = processed.add(BigInteger.ONE);
            }

            number = number.add(BigInteger.ONE);
        }
    }

}


