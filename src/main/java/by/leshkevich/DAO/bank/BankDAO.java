package by.leshkevich.DAO.bank;

import by.leshkevich.model.Bank;
import by.leshkevich.utils.exceptions.DAOException;

public interface BankDAO {
    boolean delete(int id) throws DAOException;
    Bank save(Bank bank) throws DAOException;

    boolean update(Bank bank) throws DAOException;
    Bank get(int id) throws DAOException;
}
