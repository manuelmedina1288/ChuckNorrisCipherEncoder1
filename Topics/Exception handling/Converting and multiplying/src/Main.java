import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int i = 1;
        String input = "";

        while (i != 0) {

            try {
                input = scanner.nextLine();
                i = Integer.parseInt(input);

                if (i != 0) {
                    System.out.println(i * 10);
                }

            } catch(NumberFormatException e) {
                System.out.printf("Invalid user input: %s%n", input );
            }
        }

    }
}