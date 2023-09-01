package by.leshkevich.DAO.bank;

import by.leshkevich.model.Bank;
import by.leshkevich.utils.db.ConnectionManager;
import by.leshkevich.utils.db.SQLRequest;
import by.leshkevich.utils.exceptions.DAOException;

import java.sql.*;

public class BankPostgresDAOImpl implements BankDAO {
    @Override
    public boolean delete(int id) throws DAOException {
        boolean isDel = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.DELETE_BANK_BY_ID)) {

            pst.setInt(1, id);
            if (pst.executeUpdate() > 0) {
                isDel = true;
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return isDel;
    }

    @Override
    public Bank save(Bank bank) throws DAOException {
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.INSERT_BANK_TO_DB, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, bank.getName());
            if (pst.executeUpdate() > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    bank.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return bank;
    }

    @Override
    public boolean update(Bank bank) throws DAOException {
        boolean isUpdate = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.UPDATE_BANK_NAME_BY_NUMBER)) {

            pst1.setString(1, bank.getName());
            pst1.setInt(2, bank.getId());

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
    public Bank get(int id) throws DAOException {
        Bank bank = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.SELECT_BANK_BY_ID)) {
            pst1.setInt(1, id);

            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                int idDB = rs.getInt(SQLRequest.ID_COLUMN);
                String nameBank = rs.getString(SQLRequest.NAME_COLUMN);

                    bank = new Bank(idDB, nameBank);
            }
            return bank;

        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


}
