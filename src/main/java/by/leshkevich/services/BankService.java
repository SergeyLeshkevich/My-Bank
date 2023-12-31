package by.leshkevich.services;

import by.leshkevich.DAO.bank.BankDAO;
import by.leshkevich.DAO.bank.BankPostgresDAOImpl;
import by.leshkevich.model.Bank;
import by.leshkevich.utils.constants.AppConstant;
import by.leshkevich.utils.exceptions.AccountException;
import by.leshkevich.utils.exceptions.AuthorisationException;
import by.leshkevich.utils.exceptions.BalanceException;
import by.leshkevich.model.Account;
import by.leshkevich.model.Transaction;
import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.YmlManager;
import by.leshkevich.utils.enums.Status;
import by.leshkevich.utils.enums.TypeOperation;
import by.leshkevich.utils.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class contains the main logic for working with the entity Bank and making a bank transaction
 */
public class BankService {
    private static final Logger logger = LogManager.getLogger(BankService.class);
    private final AccountService ACCOUNT_SERVICE = new AccountService();
    private final TransactionService TRANSACTION_SERVICE = new TransactionService();
    private final UserService USER_SERVICE = new UserService();
    private YmlManager YAML = new YmlManager();
    private final BankDAO BANK_DAO = new BankPostgresDAOImpl();

    /**
     * the method is intended for opening a transaction by the sender's bank during a banking operation
     *
     * @param numberSenderAccount      sender's account number
     * @param numberBeneficiaryAccount beneficiary's account number
     * @return if successful, a transaction object is returned with the operation status "IN PROCESSING".
     * Otherwise, null is returned.
     */
    public Transaction openTransaction(String numberSenderAccount, String numberBeneficiaryAccount,
                                       double sumOperation, TypeOperation typeOperation) {
        logger.info("Opening a transaction senderBank: {},{},{},{}", numberSenderAccount, numberBeneficiaryAccount, sumOperation, typeOperation);
        Account senderAccount = requestAccount(numberSenderAccount);
        Account beneficiaryAccount = requestAccount(numberBeneficiaryAccount);

        String s = YAML.getValue(AppConstant.CONFIGURATION_YAML, AppConstant.PERCENT_TRANSACTION);
        int finePercent = Integer.parseInt(s);

        Transaction transaction = Transaction.builder()
                .amount(sumOperation)
                .senderAccount(senderAccount)
                .senderBank(senderAccount.getBank())
                .beneficiaryBank(beneficiaryAccount.getBank())
                .beneficiaryAccount(beneficiaryAccount)
                .typeOperation(typeOperation.getMessage())
                .status(Status.IN_PROCESSING.getMessage())
                .dataOperation(new DateManager()).build();

        String bBankName = transaction.getBeneficiaryBank().getName();
        if (!transaction.getTypeOperation().equals(TypeOperation.ACCRUAL_OF_INTEREST.getMessage())) {
            if (!bBankName.equals(AppConstant.CLEVER_BANK)) {
                sumOperation = sumOperation + TRANSACTION_SERVICE.getFine(sumOperation, finePercent);
            }

            if (!transaction.getTypeOperation().equals(TypeOperation.REFILL.getMessage())) {
                sumOperation = sumOperation * (-1);
            }
        }
        logger.info("Transfer amount from senderAccount:{},{}", numberSenderAccount, sumOperation);
        boolean status = ACCOUNT_SERVICE.updateAccount(numberSenderAccount, sumOperation);
        logger.info("Translation: {}", status);

        if (!status) return null;
        TRANSACTION_SERVICE.saveTransaction(transaction);
        logger.info("Sending a transaction to beneficiaryBank: {}", transaction);
        return transaction;
    }

    /**
     * this method in which the receiving bank processes the transaction from the sending bank
     *
     * @param transaction transaction from the sender's bank
     * @return after checks, returns the Transaction object of the sender's bank with a changed status
     */
    public Transaction handleRequestSenderBank(Transaction transaction) {
        logger.info("Receive transaction from senderBank: {}", transaction);
        if (checkingIncomingTransaction(transaction)) {
            Account beneficiaryAccount = transaction.getBeneficiaryAccount();
            String numberBeneficiaryAccount = beneficiaryAccount.getNumber();
            double sumOperation = transaction.getAmount();

            boolean status = false;

            if (transaction.getTypeOperation().equals(TypeOperation.TRANSLATION.getMessage())) {
                logger.info("Crediting money to BeneficiaryAccount: {},{}", numberBeneficiaryAccount, Math.abs(sumOperation));
                status = ACCOUNT_SERVICE.updateAccount(numberBeneficiaryAccount, Math.abs(sumOperation));
            }
            if (status ||
                    transaction.getTypeOperation().equals(TypeOperation.REFILL.getMessage())
                    || transaction.getTypeOperation().equals(TypeOperation.WITHDRAWAL.getMessage())
                    || transaction.getTypeOperation().equals(TypeOperation.ACCRUAL_OF_INTEREST.getMessage())) {
                logger.info("Changing the status of a transaction: {}", transaction);
                transaction.setStatus(Status.COMPLETED.getMessage());
            } else {
                transaction.setStatus(Status.REJECTED.getMessage());
            }

        } else {
            transaction.setStatus(Status.REJECTED.getMessage());
        }
        logger.info("Transaction status changed: {}", transaction);
        if (transaction.getTypeOperation().equals(TypeOperation.TRANSLATION.getMessage())
                || transaction.getTypeOperation().equals(TypeOperation.WITHDRAWAL.getMessage())) {
            double amount = transaction.getAmount() * (-1);
            logger.info("Changing the transaction amount depending on the type: {}", transaction);
            transaction.setAmount(amount);
        }

        TRANSACTION_SERVICE.updateStatusAndAmount(transaction);
        logger.info("Revert transaction to senderBank: {}", transaction);
        return transaction;
    }

    /**
     * this method, in which the sending bank processes the transaction object returned from the beneficiary bank.
     * In case of a successful outcome of the banking operation, a transaction receipt is generated
     *
     * @param transaction transaction from the beneficiary's bank
     * @return after checks, returns the Transaction object of the beneficiary's bank with a changed status
     */
    public Transaction handleResponseBeneficiaryBank(Transaction transaction) {
        logger.info("Receive transaction from senderBank: {}", transaction);
        if (transaction.getStatus().equals(Status.REJECTED.getMessage())) {
            logger.info("Rolling back a transaction: {}", transaction);
            boolean status = ACCOUNT_SERVICE.updateAccount(transaction.getSenderAccount().getNumber(), transaction.getAmount());
            logger.info("Translation: {}", status);
        } else {
            if (Boolean.parseBoolean(YAML.getValue(AppConstant.CONFIGURATION_YAML, AppConstant.SAVE_CHECK)))
                TRANSACTION_SERVICE.createCheck(transaction);
        }

        return transaction;
    }

    /**
     * input parameter validation method
     *
     * @throws AccountException       thrown if account not found
     * @throws BalanceException       thrown out if there are not enough funds on the account
     * @throws AuthorisationException thrown out on unsuccessful authorization
     */
    public void validationInputParameters(Account senderAccount, Account beneficiaryAccount,
                                          TypeOperation typeOperation, double sumOperation,
                                          String login, String passwordOperation)
            throws AccountException, BalanceException, AuthorisationException {
        logger.info("Validation of incoming parameters. Incoming parameters:{},{},{},{},{},{}", senderAccount, beneficiaryAccount,
                typeOperation, sumOperation, login, passwordOperation);
        USER_SERVICE.authorisation(login, passwordOperation);

        logger.info("Balance update. Incoming parameters:{}", senderAccount);
        senderAccount.setBalance(
                ACCOUNT_SERVICE.getAccountByNumber(senderAccount.getNumber()).getBalance());

        if (senderAccount == null) throw new AccountException("Sender account not found");
        if (beneficiaryAccount == null) throw new AccountException("Beneficiary account not found");

        double balance = senderAccount.getBalance();
        if (typeOperation == TypeOperation.WITHDRAWAL || typeOperation == TypeOperation.TRANSLATION) {
            if ((balance - Math.abs(sumOperation)) < 0) throw new BalanceException("Insufficient funds");
        }
        logger.info("Balance updated");
    }

    /**
     * verification of the transaction by the beneficiary bank
     *
     * @return returns true on success
     */
    private boolean checkingIncomingTransaction(Transaction incominTransaction) {
        logger.info("Checking if a transaction exists in the database:{}", incominTransaction);
        boolean isTransactionOK = false;
        Transaction transactionFromDAO = null;
        int counter = 0;
        while (transactionFromDAO == null && counter <=
                Integer.parseInt(YAML.getValue(AppConstant.CONFIGURATION_YAML, AppConstant.NUMBER_ATTEMPTS_REQUESTS))) {
            transactionFromDAO = TRANSACTION_SERVICE.getTransaction(incominTransaction.getId());
            counter++;
        }
        if (transactionFromDAO != null) {
            isTransactionOK = true;
        }
        logger.info("Examination: {}", isTransactionOK);
        return isTransactionOK;
    }

    /**
     * request to get a bank account object. several attempts are given to obtain.
     * the number of attempts is specified in the conf.yml file
     *
     * @param numberAccount bank account number
     */
    private Account requestAccount(String numberAccount) {
        logger.info("invoice request: {}", numberAccount);
        Account account = null;
        int counter = 0;
        while (account == null && counter <= Integer.parseInt(YAML.getValue(AppConstant.CONFIGURATION_YAML,
                AppConstant.NUMBER_ATTEMPTS_REQUESTS))) {
            account = ACCOUNT_SERVICE.getAccountByNumber(numberAccount);
            counter++;
        }
        logger.info("счет: {}", account);
        return account;
    }

    /**
     * the method is for deleting the Bank object
     *
     * @return returns true on success
     */
    public boolean delete(int id) {
        logger.info("deleting a bank: {}", id);
        boolean status;
        try {
            status = BANK_DAO.delete(id);
            logger.info("bank deleted: {}", status);
            return status;
        } catch (DAOException e) {
            logger.info("bank deleted: {}", false);
            return false;
        }
    }

    /**
     * the method is intended to be stored in the Bank object
     *
     * @param bank required fields in the transaction object must be all fields except id
     * @return returns a Transaction object with an id assigned to id
     */
    public Bank save(Bank bank) {
        logger.info("saving the bank: {}", bank);
        Bank saveBank;
        try {
            saveBank = BANK_DAO.save(bank);
            logger.info("bank: {}", saveBank);
            return saveBank;
        } catch (DAOException e) {
            logger.info("bank: {}", "empty");
            return null;
        }
    }

    /**
     * the method is for updating the name field in the transaction object
     *
     * @param bank required fields in the Transaction object must be id and name
     * @return returns true on success
     */
    public boolean update(Bank bank) {
        logger.info("bank change: {}", bank);
        boolean status;
        try {
            status = BANK_DAO.update(bank);
            logger.info("bank changed: {}", status);

            return status;
        } catch (DAOException e) {
            logger.info("bank changed: {}", false);
            return false;
        }
    }

    /**
     * the method is designed to get the Transaction object from the dao layer
     *
     * @return returns a Bank object
     */
    public Bank get(int id) {
        logger.info("getting a bank: {}", id);
        Bank saveBank;
        try {
            saveBank = BANK_DAO.get(id);
            logger.info("bank: {}", id);
            return saveBank;
        } catch (DAOException e) {
            logger.info("bank: {}", "empty");
            return null;
        }
    }
}
