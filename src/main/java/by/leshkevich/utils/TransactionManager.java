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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class is the main conductor for conducting a transaction
 */
@Data
public class TransactionManager {

    private static final Logger logger = LogManager.getLogger(AccountService.class);

    private final BankService BANK_SERVICE = new BankService();

    /**
     * this method is the entry point for conducting a transaction
     */
    public String conductTransaction(Account senderAccount, Account beneficiaryAccount,
                                     double sumOperation, String login, String passwordOperation,
                                     TypeOperation typeOperation) {
        logger.info("начало банковской операции. Входящие параметры:{},{},{},{},{},{}", senderAccount, beneficiaryAccount, sumOperation, login, passwordOperation, typeOperation);
        String response;

        Transaction transaction = null;
        synchronized (senderAccount) {

            try {
                BANK_SERVICE.validationInputParameters(senderAccount, beneficiaryAccount,
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

    /**
     * when this method is called, the process of accruing interest on the specified account is started.
     * Interest rate is specified in conf.yml
     */
    public void accrueInterest(Account senderAccount) {
        logger.info("Начисление процентов: {}", senderAccount);
        YmlManager ymlManager = new YmlManager();
        double sBalance = senderAccount.getBalance();

        double sumOperation = getFine(sBalance,
                Integer.parseInt(ymlManager.getValue(AppConstant.CONFIGURATION_YAML, AppConstant.ACCRUAL_PERCENTAGE)));
        Transaction transaction = performOperation(senderAccount, senderAccount, sumOperation, TypeOperation.ACCRUAL_OF_INTEREST);
        logger.info("Начисление процентов: {}", transaction);
    }

    /**
     * in this method, the distribution of transactions between banks takes place
     */
    private Transaction performOperation(Account senderAccount, Account beneficiaryAccount, double sumOperation,
                                         TypeOperation typeOperation) {
        Transaction transaction;

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

    /**
     * this method is designed to calculate the amount by interest rate.
     * The rate is specified in the file conf.yml
     */
    private double getFine(double sum, int percent) {
        logger.info("Расчет суммы начисления:{} {}", sum, percent);
        double sumWithPercent = (Math.abs(sum) * percent) / 100;
        logger.info("Сумма с процентом {}", sumWithPercent);
        return sumWithPercent;
    }
}
