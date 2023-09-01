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

public class BankService {
    private final AccountService ACCOUNT_SERVICE = new AccountService();
    private final TransactionService TRANSACTION_SERVICE = new TransactionService();
    private final UserService USER_SERVICE = new UserService();
    private YmlManager YAML = new YmlManager();
    private final BankDAO BANK_DAO = new BankPostgresDAOImpl();

    public Transaction openTransaction(String numberSenderAccount, String numberBeneficiaryAccount,
                                       double sumOperation, TypeOperation typeOperation) {
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


        boolean transfer = moneyTransfer(numberSenderAccount, sumOperation);

        if (!transfer) return null;
        TRANSACTION_SERVICE.saveTransaction(transaction);

        return transaction;
    }

    public Transaction handleRequestSenderBank(Transaction transaction) {

        if (checkingIncomingTransaction(transaction)) {
            Account beneficiaryAccount = transaction.getBeneficiaryAccount();
            String numberBeneficiaryAccount = beneficiaryAccount.getNumber();
            double sumOperation = transaction.getAmount();

            boolean status = false;

            if (transaction.getTypeOperation().equals(TypeOperation.TRANSLATION.getMessage())) {
                status = ACCOUNT_SERVICE.updateAccount(numberBeneficiaryAccount, Math.abs(sumOperation));
            }
            if (status ||
                    transaction.getTypeOperation().equals(TypeOperation.REFILL.getMessage())
                    || transaction.getTypeOperation().equals(TypeOperation.WITHDRAWAL.getMessage())
                    || transaction.getTypeOperation().equals(TypeOperation.ACCRUAL_OF_INTEREST.getMessage())) {
                transaction.setStatus(Status.COMPLETED.getMessage());
            } else {
                transaction.setStatus(Status.REJECTED.getMessage());
            }

        } else {
            transaction.setStatus(Status.REJECTED.getMessage());
        }
        transaction.setDataOperation(new DateManager());
        TRANSACTION_SERVICE.updateStatus(transaction);
        return transaction;
    }

    public Transaction handleResponseBeneficiaryBank(Transaction transaction) {
        if (transaction.getStatus().equals(Status.REJECTED.getMessage())) {
            moneyTransfer(transaction.getSenderAccount().getNumber(), transaction.getAmount());
        } else {
            if (Boolean.parseBoolean(YAML.getValue(AppConstant.CONFIGURATION_YAML, AppConstant.SAVE_CHECK)))
                TRANSACTION_SERVICE.createCheck(transaction);
        }

        return transaction;
    }

    private boolean moneyTransfer(String numberSenderAccount, Double sumOperation) {
        return ACCOUNT_SERVICE.updateAccount(numberSenderAccount, sumOperation);
    }

    public void checkAccountForTransaction(Account senderAccount, Account beneficiaryAccount,
                                           TypeOperation typeOperation, double sumOperation,
                                           String login, String passwordOperation)
            throws AccountException, BalanceException, AuthorisationException {

        USER_SERVICE.authorisation(login, passwordOperation);

        if (senderAccount == null) throw new AccountException("Sender account not found");
        if (beneficiaryAccount == null) throw new AccountException("Beneficiary account not found");

        double balance = senderAccount.getBalance();
        if (typeOperation == TypeOperation.WITHDRAWAL || typeOperation == TypeOperation.TRANSLATION) {
            if ((balance - Math.abs(sumOperation)) < 0) throw new BalanceException("Insufficient funds");
        }
    }

    private boolean checkingIncomingTransaction(Transaction incominTransaction) {
        boolean isTransactionOK = false;
        Transaction transactionFromDAO = requestTransactionFromDAO(incominTransaction);
        if (transactionFromDAO != null) {
            isTransactionOK = true;
        }
        return isTransactionOK;
    }


    private Transaction requestTransactionFromDAO(Transaction transaction) {
        Transaction transactionFromDAO = null;
        int counter = 0;
        while (transactionFromDAO == null && counter <=
                Integer.parseInt(YAML.getValue(AppConstant.CONFIGURATION_YAML,AppConstant.NUMBER_ATTEMPTS_REQUESTS))) {
            transactionFromDAO = TRANSACTION_SERVICE.getTransaction(transaction.getId());
            counter++;
        }
        return transactionFromDAO;
    }

    public boolean delete(int id) {
        try {
            return BANK_DAO.delete(id);
        } catch (DAOException e) {
            return false;
        }
    }

    public Bank save(Bank bank) {
        try {
            return BANK_DAO.save(bank);
        } catch (DAOException e) {
            return null;
        }
    }

    public boolean update(Bank bank) {
        try {
            return BANK_DAO.update(bank);
        } catch (DAOException e) {
            return false;
        }
    }

    public Bank get(int id) {
        try {
            return BANK_DAO.get(id);
        } catch (DAOException e) {
            return null;
        }
    }

    public synchronized String conductTransaction(String numberSenderAccount, String numberBeneficiaryAccount,
                                                  double sumOperation, String login, String passwordOperation,
                                                  TypeOperation typeOperation) {


        String response;
        Account senderAccount = ACCOUNT_SERVICE.getAccountByNumber(numberSenderAccount);
        Account beneficiaryAccount = ACCOUNT_SERVICE.getAccountByNumber(numberBeneficiaryAccount);
        Transaction transaction = null;
        try {
            checkAccountForTransaction(senderAccount, beneficiaryAccount,
                    typeOperation, sumOperation, login, passwordOperation);

            transaction = performOperation(senderAccount, beneficiaryAccount, sumOperation, typeOperation);

        } catch (AccountException | BalanceException | AuthorisationException e) {
            response = e.getMessage();
            return response;
        }

        response = "Transaction " + transaction.getStatus();
        return response;

    }

    public void accrueInterest(String numberSenderAccount) {
        YmlManager ymlManager = new YmlManager();
        Account senderAccount = requestAccount(numberSenderAccount);

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

        transaction = openTransaction(senderAccount.getNumber(), beneficiaryAccount.getNumber(),
                sumOperation, typeOperation);
        System.out.println(senderAccount.getBank().getName() + " peredaet tranzakciu " +
                beneficiaryAccount.getBank().getName() +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());

        System.out.println(beneficiaryAccount.getBank().getName() + " poluchaet tranzakciu ot " +
                senderAccount.getBank().getName() +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());

        transaction = handleRequestSenderBank(transaction);
        System.out.println(beneficiaryAccount.getBank().getName() + " vozvraschaet tranzakciu " +
                senderAccount.getBank().getName() +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());

        System.out.println(senderAccount.getBank().getName() + " prinimaet tranzakciu ot " +
                beneficiaryAccount.getBank().getName() +
                "-" + senderAccount.getNumber()
                + "-" + Thread.currentThread().getName());
        transaction = handleResponseBeneficiaryBank(transaction);
        System.out.println("tranzakciia zacrita-" + Thread.currentThread().getName());

        return transaction;
    }

    private double getFine(double sum, int percent) {
        return (Math.abs(sum) * percent) / 100;
    }

    private Account requestAccount(String numberAccount) {
        Account account = null;
        int counter = 0;
        while (account == null && counter <= Integer.parseInt(YAML.getValue(AppConstant.CONFIGURATION_YAML,
                AppConstant.NUMBER_ATTEMPTS_REQUESTS))) {
            account = ACCOUNT_SERVICE.getAccountByNumber(numberAccount);
            counter++;
        }
        return account;
    }


}
