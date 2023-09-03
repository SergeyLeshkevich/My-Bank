package by.leshkevich.services;

import by.leshkevich.DAO.user.UserDAO;
import by.leshkevich.DAO.user.UserPostgresDAOImpl;
import by.leshkevich.utils.exceptions.AuthorisationException;
import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.User;
import by.leshkevich.utils.security.PasswordHashing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author S.Leshkevich
 * @version 1.0
 * this class contains the main logic for working with the entity User
 */
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserDAO USER_DAO = new UserPostgresDAOImpl();

    /**
     * this method checks the uniqueness of the login
     * @return returns true on success
     */
    public boolean checkUniqueLogin(String login) {
        logger.info("Проверка уникальности логина:{}", login);
        boolean checkLogin;
        User user = null;

        try {
            user = USER_DAO.getUser(login);
            checkLogin = user == null;

        } catch (DAOException e) {
            logger.info("Уникальный логина:{}", false);
            checkLogin = false;
        }
        logger.info("Уникальный логина:{}", checkLogin);
        return checkLogin;
    }

    /**
     * method that implements the registration of a new user
     * @return returns true on success
     */
    public boolean registration(User user, String password) {
        logger.info("Регистрация пользователя:{}{}", user, password);

        boolean isReg;

        try {
            isReg = USER_DAO.save(user, password) > 0;
        } catch (DAOException e) {
            logger.info("Регистрация пользователя:{}", false);
            isReg = false;
        }
        logger.info("Регистрация пользователя:{}", isReg);
        return isReg;
    }

    /**
     * method that implements user authorization
     * @throws throws an exception AuthorisationException if the user is not found or the password is incorrect
     * @return returns true on success
     */
    public boolean authorisation(String login, String password) throws AuthorisationException {
        logger.info("Авторизация. Входящие параметры:{},{}", login, password);

        boolean auth = false;
        if (getUser(login) == null) throw new AuthorisationException("User is not found");

        try {
            String passwordBD = USER_DAO.getPassword(login);
            if (!passwordBD.isEmpty()) {
                auth = PasswordHashing.verificationPasswordHashing(password, passwordBD);
                if (!auth) throw new AuthorisationException("Incorrect password");
            }
        } catch (DAOException e) {
            logger.info("Авторизация:{}", false);
            auth = false;
        }
        logger.info("Авторизация:{}", !auth);
        return true;
    }

    /**
     * method for getting a user from the DAO layer
     *
     * @param login
     * @return returns true on success
     */
    public User getUser(String login) {
        logger.info("Получение пользователя:{}", login);
        User user;
        try {
            user = USER_DAO.getUser(login);
        } catch (DAOException e) {
            user = null;
        }
        logger.info("Пользователь:{}", user);
        return user;
    }

    /**
     * user delete method
     *
     * @param id
     * @return returns true on success
     */
    public boolean delete(int id) {
        logger.info("Удаление пользователя:{}", id);
        boolean status;
        try {
            status = USER_DAO.removeUser(id);
            logger.info("Удаление пользователя:{}", status);
            return status;
        } catch (DAOException e) {
            logger.info("Удаление пользователя:{}", false);
            return false;
        }
    }

    /**
     * method for updating the lastname field in the User object
     *
     * @param user required fields in the User object must be login and lastname
     * @return returns true on success
     */
    public boolean updateLastname(User user) {
        logger.info("Изменение пользователя:{}", user);
        boolean status;
        try {
            status = USER_DAO.updateLastname(user);
            logger.info("Изменение пользователя:{}", status);
            return status;
        } catch (DAOException e) {
            logger.info("Изменение пользователя:{}", false);

            return false;
        }
    }
}
