package by.leshkevich.services;

import by.leshkevich.DAO.account.AccountDAO;
import by.leshkevich.DAO.account.AccountPostgresDAOImpl;
import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class contains the main logic for working with the entity Account
 */
public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);
    private final AccountDAO ACCOUNT_DAO = new AccountPostgresDAOImpl();

    /**
     * the method is designed to receive all accounts found by the given parameters
     *
     * @param idUser id User
     * @return returns an arraylist with Accounts objects. If nothing is found, an empty list is returned
     */
    public List<Account> getListByIdUser(int idUser) {
        logger.info("Getting a list of accounts: {}", idUser);
        List<Account> list;

        try {
            list = ACCOUNT_DAO.getListByIdUser(idUser);
            logger.info("list of accounts: {}", list);
            return list;
        } catch (DAOException e) {
            logger.info("list of accounts: {}", "empty");
            return new ArrayList<>();
        }
    }

    /**
     * the method is designed to get the Account object from the dao layer
     *
     * @return returns Account object
     */
    public Account getAccountByNumber(String number) {
        logger.info("Getting an invoice: {}", number);
        Account account;
        try {
            account = ACCOUNT_DAO.get(number);
            logger.info("Account: {}", account);
            return account;
        } catch (DAOException e) {
            logger.info("Account: {}", "empty");
            return null;
        }
    }

    /**
     * the method is for updating the balance field in the Account object
     *
     * @return returns true on success
     */

    public boolean updateAccount(String numberAccount, double sumOperation) {
        logger.info("Account change: {}{}", numberAccount, sumOperation);
        boolean status;
        try {
            status = ACCOUNT_DAO.updateBalance(numberAccount, sumOperation);
            logger.info("Account change: {}", status);
            return status;

        } catch (DAOException e) {
            logger.info("Account change: {}", false);

            return false;
        }
    }

    /**
     * the method is designed to receive all accounts
     *
     * @return returns an HashMap with Account objects. If nothing is found, an empty HashMap is returned
     */
    public Map<String, Account> getAllAccounts() {
        logger.info("Getting a list of all accounts");

        Map<String, Account> accountMap;
        try {
            accountMap = ACCOUNT_DAO.getAllAccounts();
        } catch (DAOException e) {
            logger.info("Getting a list of all accounts: {}", "empty");
            accountMap = new HashMap<>();
        }
        logger.info("Getting a list of all accounts: {}", accountMap);

        return accountMap;
    }

    /**
     * the method is for deleting the Account object
     *
     * @return returns true on success
     */
    public boolean delete(int id) {
        logger.info("account deletion: {}", id);
        boolean status;
        try {
            status = ACCOUNT_DAO.delete(id);
            logger.info("account deletion: {}", status);
            return status;
        } catch (DAOException e) {
            logger.info("account deletion: {}", false);
            return false;
        }
    }

    /**
     * the method is intended to be stored in the Account object
     *
     * @param account required fields in the Account object must be all fields except id
     * @return returns a Account object with an id assigned to id
     */
    public Account save(Account account) {
        logger.info("Saving an account: {}", account);
        Account accountSave;
        try {
            accountSave = ACCOUNT_DAO.save(account);
            logger.info("Saving an account: {}", accountSave);
            return accountSave;

        } catch (DAOException e) {
            logger.info("Saving an account: {}", false);
            return null;
        }
    }

}
