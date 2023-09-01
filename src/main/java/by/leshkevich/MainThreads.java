package by.leshkevich;

import by.leshkevich.services.AccountService;
import by.leshkevich.services.BankService;
import by.leshkevich.utils.enums.TypeOperation;

import java.time.LocalDate;

public class MainThreads {
    public static void main(String[] args) throws InterruptedException {
        BankService BANK_SERVICE = new BankService();

        Thread.sleep(1000);
        Thread1 thread1 = new Thread1(BANK_SERVICE);
        thread1.setPriority(1);
        Thread2 thread2 = new Thread2(BANK_SERVICE);
        thread2.setPriority(2);
        Thread3 thread3 = new Thread3(BANK_SERVICE);
        thread3.setPriority(3);
        Thread4 thread4 = new Thread4(BANK_SERVICE);
        thread4.setPriority(4);
        ThreadBalanceManager balanceManager = new ThreadBalanceManager(BANK_SERVICE, true);
        balanceManager.setPriority(5);

thread1.start();
thread2.start();
thread3.start();
thread4.start();
balanceManager.start();

    }
}

class Thread1 extends Thread {
    private volatile BankService bankService;

    public Thread1(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public void run() {
        System.out.println(bankService.conductTransaction("46535465",
                "63494666",
                100,
                "user5",
                "5",
                TypeOperation.TRANSLATION));

    }
}

class Thread2 extends Thread {
    private volatile BankService bankService;

    public Thread2(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public void run() {
        System.out.println(bankService.conductTransaction("46535465",
                "46432546",
                100,
                "user15",
                "15",
                TypeOperation.TRANSLATION));

    }
}

class Thread3 extends Thread {
    private volatile BankService bankService;

    public Thread3(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public void run() {
        System.out.println(bankService.conductTransaction("46535465",
                "46535465",
                150,
                "user10",
                "10",
                TypeOperation.REFILL));

    }
}

class Thread4 extends Thread {
    private volatile BankService bankService;

    public Thread4(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public void run() {
        System.out.println(bankService.conductTransaction("46535465",
                "46535465",
                150,
                "user10",
                "10",
                TypeOperation.WITHDRAWAL));

    }
}

class ThreadBalanceManager extends Thread {
    private final AccountService ACCOUNT_SERVICE = new AccountService();
    private volatile BankService bankService;
    private boolean stopBalanceManager;


    public ThreadBalanceManager(BankService bankService, boolean stopBalanceManager) {
        this.bankService = bankService;
        this.stopBalanceManager = stopBalanceManager;
    }

    @Override
    public void run() {

        boolean isLastDayOfMonth = false;

        while (isLastDayOfMonth) {
            LocalDate today = LocalDate.now();
            int dayOfMonth = today.getDayOfMonth();
            int lastDayOfMonth = today.lengthOfMonth();

            if ((dayOfMonth == lastDayOfMonth) && !isLastDayOfMonth) {
                ACCOUNT_SERVICE.getAllAccounts().forEach((k, v) ->
                        bankService.accrueInterest(k));
                isLastDayOfMonth = true;
            } else if (dayOfMonth != lastDayOfMonth) {
                isLastDayOfMonth = false;
            }

        }
    }
}