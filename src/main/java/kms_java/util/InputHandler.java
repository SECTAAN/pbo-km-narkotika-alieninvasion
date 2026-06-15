package kms_java.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {
    private Scanner scanner = new Scanner(System.in);

    public int ambilInputAngka() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Input tidak valid! Harap masukkan angka.");
                scanner.nextLine();
            }
        }
    }

    public String ambilInputTeks() {
        return scanner.nextLine();
    }
}