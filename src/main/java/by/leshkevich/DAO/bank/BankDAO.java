package by.leshkevich.DAO.bank;

import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.utils.exceptions.DAOException;

import java.util.List;
import java.util.Map;

/**
 * @author S.Leshkevich
 * @version 1.0
 * an interface that provides methods for working with Bank's DAO for implementation by the class implementing it
 */
public interface BankDAO {
    /**
     * Bank object deletion method
     *
     * @param id id
     * @throws DAOException Exception
     */
    boolean delete(int id) throws DAOException;

    /**
     * Bank object save method
     *
     * @param bank bank
     * @throws DAOException Exception
     */
    Bank save(Bank bank) throws DAOException;

    /**
     * method for updating the name field of the Bank object
     *@param bank bank
     * @throws DAOException Exception
     */
    boolean update(Bank bank) throws DAOException;

    /**
     * get method Bank object
     * @param id id
     * @throws DAOException Exception
     */
    Bank get(int id) throws DAOException;

}
