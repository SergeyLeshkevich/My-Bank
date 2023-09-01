package by.leshkevich.services;

import by.leshkevich.DAO.user.UserDAO;
import by.leshkevich.DAO.user.UserPostgresDAOImpl;
import by.leshkevich.utils.exceptions.AuthorisationException;
import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.User;
import by.leshkevich.utils.security.PasswordHashing;

public class UserService {

    private final UserDAO USER_DAO = new UserPostgresDAOImpl();

    public boolean checkUniqueLogin(String login) {
        boolean checkLogin;
        User user = null;

        try {
            user = USER_DAO.getUser(login);
            checkLogin = user == null;

        } catch (DAOException e) {
            checkLogin = false;
        }

        return checkLogin;
    }

    public boolean registration(User user, String password) {
        boolean isReg;

        try {
            isReg = USER_DAO.save(user, password) > 0;
        } catch (DAOException e) {
            isReg = false;
        }
        return isReg;
    }

    public boolean authorisation(String login, String password) throws AuthorisationException {
        boolean auth = false;
        if (getUser(login) == null) throw new AuthorisationException("User is not found");

        try {

            String passwordBD = USER_DAO.getPassword(login);
            if (!passwordBD.isEmpty()) {
                auth = PasswordHashing.verificationPasswordHashing(password, passwordBD);
                if (!auth) throw new AuthorisationException("Incorrect password");
            }
        } catch (DAOException e) {
            auth = false;
        }
        return true;
    }

    public User getUser(String login) {
        User user;
        try {
            user = USER_DAO.getUser(login);
        } catch (DAOException e) {
            user = null;
        }
        return user;
    }

    public int save(User user, String password) {
        try {
            return USER_DAO.save(user, password);
        } catch (DAOException e) {
            return 0;
        }
    }

    public boolean delete(int id) {
        try {
            return USER_DAO.removeUser(id);
        } catch (DAOException e) {
            return false;
        }
    }


    public boolean updateLastname(User user) {
        try {
            return USER_DAO.updateLastname(user);
        } catch (DAOException e) {
            return false;
        }
    }
}
