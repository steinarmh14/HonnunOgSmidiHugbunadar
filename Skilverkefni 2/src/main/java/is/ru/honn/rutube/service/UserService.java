package is.ru.honn.rutube.service;

import is.ru.honn.rutube.domain.User;

import java.util.List;

/**
 * @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 * @version 1.0, 28/09/16.
 * The interface of UserService
 */
public interface UserService
{
    int addUser(User user) throws ServiceException;
    List<User> getUsers() throws ServiceException;
    User getUserByID(int id) throws ServiceException;

}