package by.leshkevich;

import by.leshkevich.utils.ScannerManager;
import by.leshkevich.utils.TransactionManager;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.AuthorisationException;
import by.leshkevich.model.Account;
import by.leshkevich.model.User;
import by.leshkevich.services.AccountService;
import by.leshkevich.services.UserService;
import by.leshkevich.utils.ViewForConsole;
import by.leshkevich.utils.enums.TypeOperation;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        boolean flag_exit = true;
        String menu1;
        boolean menu2 = true;
        UserService userService = new UserService();
        Scanner console=new Scanner(System.in);
        AccountService accountService = new AccountService();
        List<Account> accountList = null;
        TransactionManager transactionManager=new TransactionManager();

        while (flag_exit) {
            ViewForConsole.showMenu1View();
            menu1 = ScannerManager.nextLine(console);
            switch (menu1) {
                //авторизация
                case "1": {
                    boolean auth_flag = true;
                    while (auth_flag) {
                        System.out.println("Vvedite login:");
                        String login = ScannerManager.nextLine(console);

                        System.out.println("Vvedite parol:");
                        String password = ScannerManager.nextLine(console);

                        boolean authorisation = false;
                        try {
                            authorisation = userService.authorisation(login, password);
                        } catch (AuthorisationException e) {
                            System.out.println(e.getMessage());
                        }

                        if (authorisation) {
                            User user = userService.getUser(login);

                            while (menu2) {
                                accountList = accountService.getListByIdUser(user.getId());
                                accountList.forEach(ViewForConsole::showAccountView);

                                System.out.println("Vvedite nomer scheta dlia prodolgenia");
                                String numberAccount = ScannerManager.nextLine(console);

                                Account account = accountService.getAccountByNumberAndIdUser(numberAccount, user.getId());
                                while (account == null) {
                                    System.out.println("schet ne naiden");
                                    System.out.println("Vvedite nomer scheta dlia prodolgenia");
                                    numberAccount = ScannerManager.nextLine(console);
                                    account = accountService.getAccountByNumberAndIdUser(numberAccount, user.getId());
                                }
                                ViewForConsole.showAccountView(account);

                                ViewForConsole.showMenu2View();

                                double sumOperation;
                                String beneficiaryNumberAccount;
                                String passwordOperation;

                                switch (ScannerManager.nextLine(console)) {


                                    //Sniatie
                                    case "1": {
                                        sumOperation = ViewForConsole.enterAmount(console);
                                        passwordOperation = ViewForConsole.enterPassword(console);

                                        System.out.println(transactionManager.conductTransaction(numberAccount, numberAccount,
                                                sumOperation, login, passwordOperation, TypeOperation.REFILL));
                                    }
                                    break;
//popolnenie
                                    case "2": {
                                        sumOperation = ViewForConsole.enterAmount(console);
                                        passwordOperation = ViewForConsole.enterPassword(console);

                                        System.out.println(transactionManager.conductTransaction(numberAccount, numberAccount,
                                                sumOperation, login, passwordOperation, TypeOperation.WITHDRAWAL));
                                    }
                                    break;
//Perevod na kartu Clever-Bank
                                    case "3": {
                                        System.out.println("Vvedite nomer scheta na kotorii perevesty:");
                                        beneficiaryNumberAccount = ScannerManager.nextLine(console);
                                        Account accountBeneficiary = accountService.getAccountByNumber(beneficiaryNumberAccount);

                                        if (accountBeneficiary.getBank().getName().equals(AppConstant.CLEVER_BANK)) {
                                            sumOperation = ViewForConsole.enterAmount(console);
                                            passwordOperation = ViewForConsole.enterPassword(console);

                                            System.out.println(transactionManager.conductTransaction(numberAccount, numberAccount,
                                                    sumOperation, login, passwordOperation, TypeOperation.REFILL));
                                        } else {
                                            System.out.println("Schet ne prinadlegit " + AppConstant.CLEVER_BANK);
                                        }
                                    }
                                    break;
//Perevod na kartu drugovo banka
                                    case "4": {
                                        System.out.println("Vvedite nomer scheta na kotorii perevesty:");
                                        beneficiaryNumberAccount = ScannerManager.nextLine(console);
                                        Account accountBeneficiary = accountService.getAccountByNumber(beneficiaryNumberAccount);

                                        if (!accountBeneficiary.getBank().getName().equals(AppConstant.CLEVER_BANK)) {

                                            sumOperation = ViewForConsole.enterAmount(console);
                                            passwordOperation = ViewForConsole.enterPassword(console);

                                            System.out.println(transactionManager.conductTransaction(numberAccount, numberAccount,
                                                    sumOperation, login, passwordOperation, TypeOperation.REFILL));
                                        } else {
                                            System.out.println("Schet prinadlegit " + AppConstant.CLEVER_BANK);
                                        }
                                    }
                                    break;

                                    case "5": {

                                    }

                                    default: {
                                        System.out.println("Neverni parameter");
                                    }
                                }
                            }

                        } else {
                            System.out.println("Client ne naiden!");
                            System.out.println("Vvedite 1 dlia vihoda v glavnoe menu ili pustoe soobschenie dlia povtornoi popitki");
                            if (ScannerManager.nextLine(console).equals("1")) {
                                auth_flag = false;
                            }
                        }


                    }
                }
                break;

                //регистрация
                case "2": {

                    System.out.println("Vvedite imia:");
                    String firstname = ScannerManager.nextLine(console);

                    System.out.println("Vvedite familiu:");
                    String lastname = ScannerManager.nextLine(console);

                    System.out.println("Vvedite login:");
                    String login = ScannerManager.nextLine(console);

                    while (!userService.checkUniqueLogin(login)) {
                        System.out.println("Etot login zaniat. Vvedite drugoi:");
                        login = ScannerManager.nextLine(console);
                    }

                    System.out.println("Vvedite parol:");
                    String password = ScannerManager.nextLine(console);

                    User user = User.builder()
                            .firstname(firstname)
                            .lastname(lastname)
                            .login(login).build();

                    if (userService.registration(user, password)) {
                        System.out.println("Registracia proshla uspeshno");
                    } else {
                        System.out.println("Neuspeshnaia registracia. povtorite popitku");
                    }

                }
                break;

                //назад
                case "3": {
                }
                break;

                case "4": {
                    console.close();
                    flag_exit = false;
                }
                break;

                default: {
                    System.out.println("Neverni parameter");
                }

            }
        }
    }
}
