package by.leshkevich;

import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.services.AccountService;
import by.leshkevich.utils.TransactionManager;
import by.leshkevich.utils.enums.TypeOperation;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is the main entry point to the application. For the sake of simplicity in the demonstration,
 * classes that inherit from the Thread class and implement its run() method were written in one file.
 * The class demonstrates the operation of withdrawal, deposit, and transfer to the account of its own
 * and another bank in a multi-threaded mode of operation
 */
public class Main {

    private static Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        TransactionManager transactionManager = new TransactionManager();

        Account accountS = Account.builder()
                .number("46535465")
                .Bank(Bank.builder().name("Clever-Bank").build()).build();
        Account accountBClever = Account.builder()
                .number("32246546")
                .Bank(Bank.builder().name("Alfa-Bank").build()).build();

        Account accountBOther = Account.builder()
                .number("64674764")
                .Bank(Bank.builder().name("Alfa-Bank").build()).build();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(new Thread1(transactionManager, accountS, accountBClever));
        executorService.execute(new Thread2(transactionManager, accountS, accountBOther));
        executorService.execute(new Thread3(transactionManager, accountS, accountS));
        executorService.execute(new Thread4(transactionManager, accountS, accountS));
        executorService.execute(new ThreadBalanceManager(transactionManager, true));

    }
}

/**
 * Class responsible for transferring money to the account of own bank.
 */
@AllArgsConstructor
class Thread1 extends Thread {
    private TransactionManager transactionManager;
    private Account accountS;
    private Account accountB;

    @Override
    public void run() {
        System.out.println(transactionManager.conductTransaction(accountS,
                accountB,
                100,
                "user5",
                "5",
                TypeOperation.TRANSLATION));

    }
}

/**
 * Class responsible for transferring money to the account of another bank.
 */
@AllArgsConstructor
class Thread2 extends Thread {
    private volatile TransactionManager transactionManager;
    private Account accountS;
    private Account accountB;

    @Override
    public void run() {
        System.out.println(transactionManager.conductTransaction(accountS,
                accountB,
                100,
                "user15",
                "15",
                TypeOperation.TRANSLATION));

    }
}

/**
 * Class responsible for depositing money into the account.
 */
@AllArgsConstructor
class Thread3 extends Thread {
    private volatile TransactionManager transactionManager;
    private Account accountS;
    private Account accountB;

    @Override
    public void run() {
        System.out.println(transactionManager.conductTransaction(accountS,
                accountB,
                150,
                "user10",
                "10",
                TypeOperation.REFILL));

    }
}

/**
 * Class responsible for withdrawing money from the account.
 */
@AllArgsConstructor
class Thread4 extends Thread {
    private volatile TransactionManager transactionManager;
    private Account accountS;
    private Account accountB;

    @Override
    public void run() {
        System.out.println(transactionManager.conductTransaction(accountS,
                accountB,
                150,
                "user10",
                "10",
                TypeOperation.WITHDRAWAL));

    }
}

/**
 * Class responsible for interest calculation on bank deposits.
 */
@AllArgsConstructor
class ThreadBalanceManager extends Thread {
    private final AccountService ACCOUNT_SERVICE = new AccountService();
    private volatile TransactionManager transactionManager;
    private boolean stopBalanceManager;


    @Override
    public void run() {

        boolean isLastDayOfMonth = false;

        while (isLastDayOfMonth || stopBalanceManager) {
            System.out.println("interest calculation check");
            LocalDate today = LocalDate.now();
            int dayOfMonth = today.getDayOfMonth();
            int lastDayOfMonth = today.lengthOfMonth();

            if ((dayOfMonth == lastDayOfMonth) && !isLastDayOfMonth) {
                System.out.println("interest calculation operation started");
                ACCOUNT_SERVICE.getAllAccounts().forEach((k, v) ->
                        transactionManager.accrueInterest(v));
                isLastDayOfMonth = true;
                System.out.println("interest calculation completed");
            } else if (dayOfMonth != lastDayOfMonth) {
                System.out.println("interest calculation rejected");
                isLastDayOfMonth = false;
            }
            try {
                sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}