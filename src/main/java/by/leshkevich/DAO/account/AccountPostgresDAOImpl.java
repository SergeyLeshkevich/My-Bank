package by.leshkevich.DAO.account;

import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.Account;
import by.leshkevich.model.Bank;
import by.leshkevich.model.User;
import by.leshkevich.utils.DateManager;
import by.leshkevich.utils.db.ConnectionManager;
import by.leshkevich.utils.db.SQLRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AccountPostgresDAOImpl implements AccountDAO {
    @Override
    public Account get(String number) throws DAOException {
        Account account = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.SELECT_ACCOUNT_BY_NUMBER);
             PreparedStatement pst2 = cn.prepareStatement(SQLRequest.SELECT_USER_BY_ID);
             PreparedStatement pst3 = cn.prepareStatement(SQLRequest.SELECT_BANK_BY_ID)) {
            pst1.setString(1, number);

            List<Account> list = accountListFabric(pst1, pst2, pst3);
            if (!list.isEmpty()) {
                account = list.get(0);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return account;
    }


    @Override
    public List<Account> getListByIdUser(int idUser) throws DAOException {
        List<Account> accountList = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.SELECT_ACCOUNTS_LIST_BY_ID_USER);
             PreparedStatement pst2 = cn.prepareStatement(SQLRequest.SELECT_USER_BY_ID);
             PreparedStatement pst3 = cn.prepareStatement(SQLRequest.SELECT_BANK_BY_ID)) {
            pst1.setInt(1, idUser);

            accountList = accountListFabric(pst1, pst2, pst3);

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return accountList;
    }


    @Override
    public Account save(Account account) throws DAOException {
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.INSERT_ACCOUNT_TO_DB, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, account.getNumber());
            pst.setInt(2, account.getUser().getId());
            pst.setInt(3, account.getBank().getId());
            pst.setDouble(4, account.getBalance());
            pst.setTimestamp(5, account.getOpenData().getDateForDB());
            if (pst.executeUpdate() > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    account.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return account;
    }

    @Override
    public boolean delete(int accountId) throws DAOException {
        boolean isDel = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.DELETE_ACCOUNT_BY_ID)) {

            pst.setInt(1, accountId);
            if (pst.executeUpdate() > 0) {
                isDel = true;
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return isDel;
    }

    @Override
    public boolean updateBalance(String number, double newBalance) throws DAOException {
        boolean isUpdate = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.UPDATE_ACCOUNT_BALANCE_BY_NUMBER)) {

            pst1.setDouble(1, newBalance);
            pst1.setString(2, number);
            int res = pst1.executeUpdate();

            if (res > 0) {
                isUpdate = true;
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return isUpdate;
    }

    @Override
    public Map<String,Account> getAllAccounts() throws DAOException {
        Map<String,Account> accountMap;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.SELECT_ALL_ACCOUNTS_LIST);
             PreparedStatement pst2 = cn.prepareStatement(SQLRequest.SELECT_USER_BY_ID);
             PreparedStatement pst3 = cn.prepareStatement(SQLRequest.SELECT_BANK_BY_ID)) {

            accountMap = accountListFabric(pst1, pst2, pst3).stream().collect(Collectors.toMap(Account::getNumber, a->a));

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return accountMap;
    }



    private List<Account> accountListFabric(PreparedStatement pst1, PreparedStatement pst2, PreparedStatement pst3) throws SQLException {
        List<Account> accountList = new ArrayList<>();
        User user = null;
        Account account = null;
        Bank bank = null;

        ResultSet rs = pst1.executeQuery();
        while (rs.next()) {
            int idDB = rs.getInt(SQLRequest.ID_COLUMN);
            String numberDB = rs.getString(SQLRequest.NUMBER_COLUMN);
            int idUserDB = rs.getInt(SQLRequest.ID_USER_COLUMN);
            int idBankDB = rs.getInt(SQLRequest.ID_BANK_COLUMN);
            double balanceDB = rs.getDouble(SQLRequest.BALANCE_COLUMN);
            Timestamp dateBD = rs.getTimestamp(SQLRequest.DATE_COLUMN);

            pst2.setInt(1, idUserDB);
            ResultSet rs2 = pst2.executeQuery();
            if (rs2.next()) {
                String loginBD = rs2.getString(SQLRequest.LOGIN_COLUMN);
                String lastNameBD = rs2.getString(SQLRequest.LASTNAME_COLUMN);
                String firstNameBD = rs2.getString(SQLRequest.FIRSTNAME_COLUMN);
                user = new User(idUserDB, loginBD, lastNameBD, firstNameBD);
            }

            pst3.setInt(1, idBankDB);
            ResultSet rs3 = pst3.executeQuery();
            if (rs3.next()) {
                String bankNameBD = rs3.getString(SQLRequest.NAME_COLUMN);
                bank = new Bank(idBankDB, bankNameBD);
            }
            account = new Account(idDB, numberDB, user, bank, balanceDB, new DateManager(dateBD));
            accountList.add(account);
        }
        return accountList;
    }
}
