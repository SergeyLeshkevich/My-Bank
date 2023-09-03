package by.leshkevich.DAO.user;

import by.leshkevich.model.Transaction;
import by.leshkevich.utils.exceptions.DAOException;
import by.leshkevich.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author S.Leshkevich
 * @version 1.0
 * an interface that provides methods for working with User's DAO  for implementation by the class implementing it
 * */
public interface UserDAO {
    /**
     * get method User object
     */
    User getUser(String login) throws DAOException;

    /**
     * get method User object
     */
    String getPassword(String login) throws DAOException;

    /**
     * User object save method
     */
    int save(User user, String password)throws DAOException;

    /**
     * User object deletion method
     */
    boolean removeUser(int idUser) throws DAOException;

    /**
     * method for updating the status field of the User object
     */
    boolean updateLastname(User user) throws DAOException;

}
