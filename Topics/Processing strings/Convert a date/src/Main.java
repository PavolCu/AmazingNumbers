import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);


        String inputDateStr = scanner.nextLine();

        LocalDate date = LocalDate.parse(inputDateStr);
        String outputDateStr = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        System.out.println(outputDateStr);
    }
}