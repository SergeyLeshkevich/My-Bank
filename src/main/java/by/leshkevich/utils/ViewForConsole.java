package by.leshkevich.utils;

import by.leshkevich.model.Account;

import java.util.Scanner;

public class ViewForConsole {
    public static void showAccountView(Account account){
        System.out.println("Nomer scheta: " + account.getNumber());
        System.out.println("Bank: " + account.getBank().getName());
        System.out.println("Balans: " + account.getBalance());
        System.out.println("Data otcritia: " + account.getOpenDate().dateFormat());
        System.out.println("__________________________");
    }
    public static double enterAmount(Scanner console){
        double sumOperation;
        System.out.println("Vvedite summu:");
        sumOperation = ScannerManager.nextDouble(console);

        while (sumOperation <= 0) {
            System.out.println("Prover'te pravil'nost' vvoda");
            System.out.println("Povtorite popitku");
            sumOperation = ScannerManager.nextDouble(console);
        }
        return sumOperation;
    }
    public static String enterPassword(Scanner console){
        System.out.println("Vvedite svoi parol':");
        String passwordOperation = ScannerManager.nextLine(console);
        return passwordOperation;
    }
    public static void showMenu1View(){
        System.out.println("Viberite punkt menu:");
        System.out.println("1.Avtorizacia");
        System.out.println("2.Registracia");
        System.out.println("3.Nazad");
        System.out.println("4.Vihod");
    }

    public static void showMenu2View(){
        System.out.println("Viberite operaciu:");
        System.out.println("1.Sniatie:");
        System.out.println("2.Popolnenie:");
        System.out.println("3.Perevod na kartu Clever-Bank:");
        System.out.println("4.Perevod na kartu drugovo banka:");
        System.out.println("5.Nazad");
    }
}
