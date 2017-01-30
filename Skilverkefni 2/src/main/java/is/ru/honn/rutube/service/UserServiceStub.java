package is.ru.honn.rutube.service;

import is.ru.honn.rutube.domain.User;
import java.util.List;

/**
 * @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 * @version 1.0, 28/09/16.
 * This class contains a setter function for the user list as well as getter functions where you
 * can either get the whole list of users or a single user with a specific id.
 * The class also contains the addUser function which adds a user to the user list and returns the size of the list.
 */
public class UserServiceStub implements UserService
{
    private List<User> userList;

    /**
     * Setter function for the user list.
     *
     * @param userList The list of users.
     */
    public UserServiceStub(List<User> userList)
    {
        this.userList = userList;
    }

    /**
     * This function adds a user to the user list and returns the size of the list or throws an exception.
     * Exception is thrown if some of the attributes are in use. Those include the id, name, email or display name.
     *
     * @param user The user.
     * @return The size of the user list.
     * @throws ServiceException Throws exception if the user id, name, email or display name is in use also if adding a user wasnt possible.
     */
    public int addUser(User user) throws ServiceException {

        for(User u: userList)
        {
            if(u.getUserId() == user.getUserId())
            {
                throw new ServiceException("This user id is already in use");
            }
            if(u.getFirstName() == user.getFirstName() && u.getLastName() == user.getLastName())
            {
                throw new ServiceException("This name is already in use");
            }
            if(u.getEmail() == user.getEmail())
            {
                throw new ServiceException("This email is already in use");
            }
            if(u.getDisplayName() == user.getDisplayName())
            {
                throw new ServiceException("This display name is already in use");
            }

        }

        try
        {
            userList.add(user);
        }
        catch(Exception ex)
        {
            throw new ServiceException("Can't add user");
        }

        return userList.size();
    }

    /**
     * This function gets the list of users or throws an exception if the list is empty.
     *
     * @return The list of users.
     * @throws ServiceException Throws exception if the user list was empty or if it was enable to return the list of users.
     */
    public List<User> getUsers() throws ServiceException {

        if(userList.isEmpty())
        {
            throw new ServiceException("There are no users");
        }
        else
        {
            try
            {
                return userList;
            }
            catch (Exception ex)
            {
                throw new ServiceException("Unable to return the list of users");
            }
        }
    }

    /**
     * This function gets a user by id or throws an exception if the list of users
     * is empty or there is no user with the requested id.
     *
     * @param id The id of user.
     * @return The user with requested id.
     * @throws ServiceException Throws exception if the user list was empty,
     * if it wasn't able to return the use or no user with this id.
     */
    public User getUserByID(int id) throws ServiceException {

        if(userList.isEmpty())
        {
            throw new ServiceException("There is no user with this id");
        }
        else
        {
            for(User u: userList)
            {
                if(u.getUserId() == id)
                {
                    try
                    {
                        return u;
                    }
                    catch (Exception ex)
                    {
                        throw new ServiceException("There was an error in returning this user");
                    }

                }
            }
        }

        throw new ServiceException("There is no user with this id");

    }
}
