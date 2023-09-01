package by.leshkevich.DAO.user;

import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.User;
import by.leshkevich.utils.db.ConnectionManager;
import by.leshkevich.utils.db.SQLRequest;
import by.leshkevich.utils.security.PasswordHashing;


import java.sql.*;

public class UserPostgresDAOImpl implements UserDAO {


    @Override
    public User getUser(String login) throws DAOException {
        User user = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.SELECT_USER_BY_LOGIN)) {

            pst.setString(1, login);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int idUserDB = rs.getInt(SQLRequest.ID_COLUMN);
                String loginDB = rs.getString(SQLRequest.LOGIN_COLUMN);
                String firstnameDB = rs.getString(SQLRequest.FIRSTNAME_COLUMN);
                String lastnameDB = rs.getString(SQLRequest.LASTNAME_COLUMN);

                user = new User(idUserDB, loginDB, firstnameDB, lastnameDB);
            }


        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public String getPassword(String login) throws DAOException {
        String passwordDB = "";
        int idUser = 0;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.SELECT_ID_USER_BY_LOGIN);
             PreparedStatement pst2 = cn.prepareStatement(SQLRequest.SELECT_PASSWORD_BY_ID_USER)) {

            pst1.setString(1, login);

            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                idUser = rs.getInt(SQLRequest.ID_COLUMN);
            }

            if (idUser > 0) {
                pst2.setInt(1, idUser);

                rs = pst2.executeQuery();
                if (rs.next()) {
                    passwordDB = rs.getString(SQLRequest.PASSWORD_COLUMN);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return passwordDB;

    }

    @Override
    public int save(User user, String password) throws DAOException {
        int idUserBD = 0;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.INSERT_USER_TO_DB, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pst2 = cn.prepareStatement(SQLRequest.INSERT_PASSWORD_FOR_USER)) {


            pst1.setString(1, user.getLogin());
            pst1.setString(2, user.getFirstname());
            pst1.setString(3, user.getLastname());
            if (pst1.executeUpdate() > 0) {
                ResultSet rs = pst1.getGeneratedKeys();
                if (rs.next()) {
                    idUserBD = rs.getInt(1);
                }

                pst2.setInt(1, idUserBD);
                pst2.setString(2, PasswordHashing.scryptPasswordHashing(password));
                pst2.setString(2, password);
                idUserBD = 0;
                int res = pst2.executeUpdate();
                if (res > 0) {
                    idUserBD = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return idUserBD;
    }

    @Override
    public boolean removeUser(int idUser) throws DAOException {
        boolean isDel = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst = cn.prepareStatement(SQLRequest.DELETE_USER_BY_ID)) {

            pst.setInt(1, idUser);
            pst.setInt(2, idUser);
            if (pst.executeUpdate() > 0) {
                isDel = true;
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return isDel;
    }

    @Override
    public boolean updateLastname(User user){
        boolean isUpdate = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement pst1 = cn.prepareStatement(SQLRequest.UPDATE_USER_LASTNAME_BY_LOGIN)) {

            pst1.setString(1, user.getLastname());
            pst1.setString(2, user.getLogin());


            int res = pst1.executeUpdate();

            if (res > 0) {
                isUpdate = true;
            }
            return isUpdate;

        } catch (SQLException e) {
            return false;
        }
    }


}
