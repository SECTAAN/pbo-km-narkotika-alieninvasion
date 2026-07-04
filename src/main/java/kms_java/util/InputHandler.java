package kms_java.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {

    public static int validasiInt(String prompt, Scanner sc) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = sc.nextInt();
                sc.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Input tidak valid! Harap masukkan angka bulat.");
                sc.nextLine();
            }
        }
    }

    public static double validasiDouble(String prompt, Scanner sc) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = sc.nextDouble();
                sc.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Input tidak valid! Harap masukkan angka (bisa desimal).");
                sc.nextLine();
            }
        }
    }

    public static String validasiString(String prompt, Scanner sc) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public static int validasiPilihan(String prompt, int min, int max, Scanner sc) {
        while (true) {
            int input = validasiInt(prompt, sc);
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.println("[ERROR] Pilihan di luar rentang (" + min + " - " + max + ")!");
            }
        }
    }

    public static int validasiIntGUI(String input) throws NumberFormatException {
        if (input == null || input.trim().isEmpty()) {
            throw new NumberFormatException("Input angka tidak boleh kosong!");
        }
        return Integer.parseInt(input.trim());
    }

    public static double validasiDoubleGUI(String input) throws NumberFormatException {
        if (input == null || input.trim().isEmpty()) {
            throw new NumberFormatException("Input angka desimal tidak boleh kosong!");
        }
        return Double.parseDouble(input.replace(",", ".").trim());
    }
}