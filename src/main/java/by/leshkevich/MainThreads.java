package by.leshkevich;

import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.services.AccountService;
import by.leshkevich.utils.TransactionManager;
import by.leshkevich.utils.enums.TypeOperation;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThreads {
    public static void main(String[] args){
        TransactionManager transactionManager = new TransactionManager();

        Account accountS = Account.builder()
                .number("46535465")
                .Bank(Bank.builder().name("Clever-Bank").build()).build();
        Account accountB = Account.builder()
                .number("21342355")
                .Bank(Bank.builder().name("Alfa-Bank").build()).build();
        ExecutorService executorService= Executors.newFixedThreadPool(3);

        executorService.execute(new Thread1(transactionManager,accountS,accountB));
        executorService.execute(new Thread2(transactionManager,accountS,accountB));
        executorService.execute(new Thread3(transactionManager,accountS,accountB));
        executorService.execute(new Thread4(transactionManager,accountS,accountB));
        executorService.execute(new ThreadBalanceManager(transactionManager,true));

    }
}

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

@AllArgsConstructor
class ThreadBalanceManager extends Thread {
    private final AccountService ACCOUNT_SERVICE = new AccountService();
    private volatile TransactionManager transactionManager;
    private boolean stopBalanceManager;


    @Override
    public void run() {

        boolean isLastDayOfMonth = false;

        while (isLastDayOfMonth||stopBalanceManager) {
            LocalDate today = LocalDate.now();
            int dayOfMonth = 30;
            int lastDayOfMonth = today.lengthOfMonth();

            if ((dayOfMonth == lastDayOfMonth) && !isLastDayOfMonth) {
                ACCOUNT_SERVICE.getAllAccounts().forEach((k, v) ->
                        transactionManager.accrueInterest(v));
                isLastDayOfMonth = true;
            } else if (dayOfMonth != lastDayOfMonth) {
                isLastDayOfMonth = false;
            }

        }
    }
}