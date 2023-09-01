package by.leshkevich.services;

import by.leshkevich.DAO.account.AccountDAO;
import by.leshkevich.DAO.account.AccountPostgresDAOImpl;
import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountService {
   private final AccountDAO ACCOUNT_DAO = new AccountPostgresDAOImpl();

    public List<Account> getListByIdUser(int idUser) {
        List<Account> list;

        try {
            list = ACCOUNT_DAO.getListByIdUser(idUser);
        } catch (DAOException e) {
            list = new ArrayList<>();
        }
        return list;
    }

    public Account getAccountByNumber(String number) {
        Account account = null;
        try {
            account = ACCOUNT_DAO.get(number);
        } catch (DAOException e) {
            account = null;
        }
        return account;
    }

    public Account getAccountByNumberAndIdUser(String number, int idUser) {
        Account account = null;
        List<Account> accountList = null;
        try {
            accountList = ACCOUNT_DAO.getListByIdUser(idUser);
            accountList = accountList.stream().filter(element -> element.getNumber().equals(number)).collect(Collectors.toList());

            if (!accountList.isEmpty()) account = accountList.get(0);

        } catch (DAOException e) {
            account = null;
        }
        return account;
    }

    public boolean updateAccount(String numberAccount, double sumOperation) {
        boolean isUpdate;
        try {
            isUpdate = ACCOUNT_DAO.updateBalance(numberAccount, sumOperation);

        } catch (DAOException e) {
            isUpdate = false;
        }

        return isUpdate;
    }

    public Map<String, Account> getAllAccounts() {
        Map<String, Account> accountMap;
        try {
            accountMap = ACCOUNT_DAO.getAllAccounts();
        } catch (DAOException e) {
            accountMap = new HashMap<>();
        }
        return accountMap;
    }

    public boolean delete(int id) throws DAOException {
       return ACCOUNT_DAO.delete(id);
    }

    public Account save(Account account){
        try {
            return ACCOUNT_DAO.save(account);
        } catch (DAOException e) {
            return null;
        }
    }

}
