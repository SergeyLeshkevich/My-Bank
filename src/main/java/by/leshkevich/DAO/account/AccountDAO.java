package by.leshkevich.DAO.account;

import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Account;

import java.util.List;
import java.util.Map;

/**
 * @author S.Leshkevich
 * @version 1.0
 * an interface that provides methods for working with Account's DAO for implementation by the class implementing it
 * */
public interface AccountDAO {
    /**
     * get method Account object
     * @param number number
     * @throws DAOException ejected into the DAO
     * @return Account
     */
    Account get(String number) throws DAOException;
    /**
     * Account object save method
     * @param account account
     * @throws DAOException ejected into the DAO
     * @return Account
     */
    Account save(Account account)throws DAOException;
    /**
     * Account object deletion method
     * @param id id
     * @throws DAOException ejected into the DAO
     * @return boolean
     */
    boolean delete(int id) throws DAOException;
    /**
     * method for getting list of Accounts object by user id
     * @param idUser idUser
     * @throws DAOException ejected into the DAO
     * @return Account list
     */
    List<Account> getListByIdUser(int idUser) throws DAOException;
    /**
     * method for updating the balance field of the Account object
     * @param number number
     * @param newBalance newBalance
     * @throws DAOException ejected into the DAO
     * @return boolean
     */
    boolean updateBalance(String number, double newBalance) throws DAOException;
    /**
     * method for getting a list of all Account objects
     * @throws DAOException ejected into the DAO
     * @return Account map
     */
    Map<String, Account> getAllAccounts() throws DAOException;
}
