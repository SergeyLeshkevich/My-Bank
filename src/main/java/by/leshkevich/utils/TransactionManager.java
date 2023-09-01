package by.leshkevich.utils;

import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.AccountException;
import by.leshkevich.utils.exceptions.AuthorisationException;
import by.leshkevich.utils.exceptions.BalanceException;
import by.leshkevich.model.Account;
import by.leshkevich.model.Transaction;
import by.leshkevich.services.AccountService;
import by.leshkevich.services.BankService;
import by.leshkevich.utils.enums.TypeOperation;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class TransactionManager {

    private final BankService BANK_SERVICE = new BankService();

    public String conductTransaction(Account senderAccount, Account beneficiaryAccount,
                                     double sumOperation, String login, String passwordOperation,
                                     TypeOperation typeOperation) {
        String response;


        Transaction transaction = null;
        synchronized (senderAccount) {

            try {
                BANK_SERVICE.checkAccountForTransaction(senderAccount, beneficiaryAccount,
                        typeOperation, sumOperation, login, passwordOperation);

                transaction = performOperation(senderAccount, beneficiaryAccount, sumOperation, typeOperation);

            } catch (AccountException | BalanceException | AuthorisationException e) {
                response = e.getMessage();
                return response;
            }

            response = "Transaction " + transaction.getStatus();
            return response;

        }
    }

    public void accrueInterest(Account senderAccount) {
        YmlManager ymlManager = new YmlManager();
        double sBalance = senderAccount.getBalance();

        double sumOperation = getFine(sBalance,
                Integer.parseInt(ymlManager.getValue(AppConstant.CONFIGURATION_YAML, AppConstant.ACCRUAL_PERCENTAGE)));

        performOperation(senderAccount, senderAccount, sumOperation, TypeOperation.ACCRUAL_OF_INTEREST);
    }


    private Transaction performOperation(Account senderAccount, Account beneficiaryAccount, double sumOperation,
                                         TypeOperation typeOperation) {
        Transaction transaction;

        System.out.println(senderAccount.getBank().getName() + " sozdaet tranzakciu " +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());

        transaction = BANK_SERVICE.openTransaction(senderAccount.getNumber(), beneficiaryAccount.getNumber(),
                sumOperation, typeOperation);
        System.out.println(senderAccount.getBank().getName() + " peredaet tranzakciu " +
                beneficiaryAccount.getBank().getName() +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());

        System.out.println(beneficiaryAccount.getBank().getName() + " poluchaet tranzakciu ot " +
                senderAccount.getBank().getName() +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());

        transaction = BANK_SERVICE.handleRequestSenderBank(transaction);
        System.out.println(beneficiaryAccount.getBank().getName() + " vozvraschaet tranzakciu " +
                senderAccount.getBank().getName() +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());

        System.out.println(senderAccount.getBank().getName() + " prinimaet tranzakciu ot " +
                beneficiaryAccount.getBank().getName() +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());
        transaction = BANK_SERVICE.handleResponseBeneficiaryBank(transaction);
        System.out.println("tranzakciia zacrita-" + Thread.currentThread().getName());

        return transaction;
    }

    private double getFine(double sum, int percent) {
        return (Math.abs(sum) * percent) / 100;
    }
}
