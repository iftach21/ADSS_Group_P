import java.util.Scanner;

abstract class AInterface {



    public static String checkName (String input)
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int counter = 0;
            for (int i = 0; i < input.length(); i++) {
                if (Character.isDigit(input.charAt(i))) {
                    System.out.println("A name has to be letters only");
                    break;
                } else {
                    counter++;
                }
            }
            if (counter == input.length()) {
                return input;
            } else {
                System.out.println("name:");
                input = scanner.next();
            }
        }
    }
    public static String checkNumber (String input)
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int counter = 0;
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    System.out.println("has to be numbers only");
                    break;
                } else {
                    counter++;
                }
            }
            if (counter == input.length()) {
                return input;
            } else {
                System.out.println("number:");
                input = scanner.next();
            }
        }
    }

    public static String checkNumberWithDot (String input)
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int counter = 0;
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i)) && !(input.charAt(i) == '.')) {
                    System.out.println("has to be numbers only");
                    break;
                } else {
                    counter++;
                }
            }
            if (counter == input.length()) {
                return input;
            } else {
                System.out.println("number:");
                input = scanner.next();
            }
        }
    }

}
