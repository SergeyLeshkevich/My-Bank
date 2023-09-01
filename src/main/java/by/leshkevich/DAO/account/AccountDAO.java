package by.leshkevich.DAO.account;

import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Account;

import java.util.List;
import java.util.Map;

public interface AccountDAO {
    Account get(String number) throws DAOException;
    Account save(Account account)throws DAOException;
    boolean delete(int id) throws DAOException;
    List<Account> getListByIdUser(int idUser) throws DAOException;
    boolean updateBalance(String number, double newBalance) throws DAOException;
    Map<String, Account> getAllAccounts() throws DAOException;
}
