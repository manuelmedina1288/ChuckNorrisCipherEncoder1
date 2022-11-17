package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        chuckNorrisCipher();

    }

    public static void chuckNorrisCipher() {
        String operation = "";
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Please input operation (encode/decode/exit):");
            operation = scanner.nextLine();

            switch (operation) {
                case "encode":
                    System.out.println("Input string:");
                    String stringToBeEncoded = scanner.nextLine();

                    System.out.printf("Encoded String:%n%s%n%n", encoder(stringToBeEncoded));
                    break;
                case "decode":

                    System.out.println("Input encoded string:");
                    String encodedString = scanner.nextLine();

                    if (isValid(encodedString)) {
                        String decodedString = decoder(encodedString);

                        if (decodedString.length() % 7 == 0) {
                            String[] blocks = separateBinaryCode(decodedString);

                            String result = "";

                            for (int i = 0; i < blocks.length; i++) {
                                result += String.format("%c", fromBinaryToChar(blocks[i]));
                            }

                            System.out.printf("Decoded String:%n%s%n%n", result);
                        } else {
                            System.out.println("Encoded string is not valid.\n");
                        }

                    } else {
                        System.out.println("Encoded string is not valid.\n");
                    }

                    break;

                case "exit":
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.printf("There is no '%s' operation%n%n", operation);
                    break;
            }

        } while(!"exit".equals(operation));
    }

    public static String encoder(String stringToBeEncoded) {
        String wholeBinarySequence = "";


        for (int i = 0; i < stringToBeEncoded.length(); i++) {

            int integerChar = stringToBeEncoded.charAt(i);

            // converts the char at the specified position to a binary sequence and add it to the whole sequence.
            wholeBinarySequence += String.format("%07d", Integer.parseInt(Integer.toBinaryString(integerChar)));

        }

        String chuckNorriesCode = "";

        for (int i = 0; i < wholeBinarySequence.length(); i++) {
            // Compares if the next number is 1 or 0
            if (wholeBinarySequence.charAt(i) == '1') {

                chuckNorriesCode += "0 ";

                // counter the number of consecutive ones
                for (int j = i; j < wholeBinarySequence.length(); j++) {
                    if (wholeBinarySequence.charAt(j) == '1') {
                        chuckNorriesCode += "0";
                        i++;
                    } else {
                        chuckNorriesCode += " ";
                        i--;
                        break;
                    }
                }

            } else {

                chuckNorriesCode += "00 ";

                // counter the number of consecutive ceros
                for (int j = i; j < wholeBinarySequence.length(); j++) {

                    if (wholeBinarySequence.charAt(j) == '0') {

                        chuckNorriesCode += "0";
                        i++;

                    } else {

                        chuckNorriesCode += " ";
                        i--;
                        break;

                    }
                }
            }
        }

        return chuckNorriesCode.trim();

    }

    public static String decoder(String chuckNorrisCode) {

        String[] initialCode = chuckNorrisCode.split(" ");
        String result = "";

        int i = 0;

        while (i < initialCode.length) {
            if ("0".equals(initialCode[i])) {
                for (int j = 0; j < initialCode[i + 1].length(); j++) {
                    result += "1";
                }
            } else {
                for (int j = 0; j < initialCode[i + 1].length(); j++) {
                    result += "0";
                }
            }
            i += 2;
        }

        return result;
    }

    public static String[] separateBinaryCode(String binaryCode) {

        int blockToSeparate = binaryCode.length() / 7;

        String[] blocks = new String[blockToSeparate];

        int init = 0;
        int end = 7;

        for (int i = 0; i < blockToSeparate; i++) {
            blocks[i] = binaryCode.substring(init, end);
            init +=7;
            end +=7;
        }

        return blocks;
    }

    public static char fromBinaryToChar(String block) {
        return (char) Integer.parseInt(block, 2);
    }

    public static boolean isValid(String encodedString) {

        for (int i = 0; i < encodedString.length(); i++) {
            if (encodedString.charAt(i) != '0' && encodedString.charAt(i) != ' ') {
                return false;
            }
        }

        String[] blocks = encodedString.split(" ");

        for (int i = 0; i < blocks.length; i += 2) {
            if (!(blocks[i].equals("00") || blocks[i].equals("0"))) {
                return false;
            }
        }

        if (blocks.length % 2 == 1) {
            return false;
        }

        return true;
    }
}