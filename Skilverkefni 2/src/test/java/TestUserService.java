import is.ru.honn.rutube.domain.User;
import is.ru.honn.rutube.service.ServiceException;
import is.ru.honn.rutube.service.UserServiceStub;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *  @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 *  @version 1.0, 28/09/16.
 */
public class TestUserService
{
    private UserServiceStub userServiceStubTest;

    @Before
    public void startUp()
    {
        List<User> userList = new ArrayList<User>();
        User user = new User(1, "Steinar", "Hilmarsson", "steinarmarinohilmarsson@gmail.com", "steini", LocalDate.of(1955,5,5));
        User user2 = new User(2, "Jon", "Jonsson", "jon@gmail.com", "jonni", LocalDate.of(1962,4,4));
        User user3 = new User(3, "Gunni", "Gunnason", "gunni@gmail.com", "gunnar", LocalDate.of(1970,3,3));
        User user4 = new User(4, "Bondi", "Bondason", "bondi@gmail.com", "bonni", LocalDate.of(1990,2,2));
        userList.add(user);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userServiceStubTest = new UserServiceStub(userList);

    }

    @Test
    public void addValidUserTest()
    {
        User user = new User(5, "Runni", "Runnason", "runni@gmail.com", "runni", LocalDate.of(1950,10,10));
        try
        {
            userServiceStubTest.addUser(user);
        }
        catch (ServiceException ex)
        { }

        List<User> temp = null;
        try {
            temp = userServiceStubTest.getUsers();
        } catch (ServiceException ex)
        { }

        assertEquals(5,temp.get(4).getUserId());
        assertEquals("Runni",temp.get(4).getFirstName());
        assertEquals("Runnason",temp.get(4).getLastName());
        assertEquals("runni@gmail.com",temp.get(4).getEmail());
        assertEquals("runni",temp.get(4).getDisplayName());
        assertEquals(LocalDate.of(1950,10,10) ,temp.get(4).getBirthDate());

    }

    @Test
    public void addUserWithExistingId()
    {
        User user = new User(4, "Bondi", "Bondason", "bondi@gmail.com", "bonni", LocalDate.of(1950,1,1));
        try
        {
            userServiceStubTest.addUser(user);
        }
        catch (ServiceException ex)
        {
            assertEquals("This user id is already in use", ex.getMessage());
        }
    }

    @Test
    public void addUserWithExistingName()
    {
        User user = new User(5, "Bondi", "Bondason", "bondi@gmail.com", "bonni", LocalDate.of(1950,1,1));
        try
        {
            userServiceStubTest.addUser(user);
        }
        catch (ServiceException ex)
        {
            assertEquals("This name is already in use", ex.getMessage());
        }
    }

    @Test
    public void addUserWithExistingEmail()
    {
        User user = new User(5, "Marri", "Bondason", "bondi@gmail.com", "bonni", LocalDate.of(1950,1,1));
        try
        {
            userServiceStubTest.addUser(user);
        }
        catch (ServiceException ex)
        {
            assertEquals("This email is already in use", ex.getMessage());
        }
    }

    @Test
    public void addUserWithExistingDisplayName()
    {
        User user = new User(5, "Marri", "Bondason", "marribondi@gmail.com", "bonni", LocalDate.of(1950,1,1));
        try
        {
            userServiceStubTest.addUser(user);
        }
        catch (ServiceException ex)
        {
            assertEquals("This display name is already in use", ex.getMessage());
        }
    }


    @Test
    public void getUserThatExist()
    {
        User user = null;
        try
        {
            user = userServiceStubTest.getUserByID(1);
        }
        catch (ServiceException ex)
        {

        }

        assertEquals(1,user.getUserId());
        assertEquals("Steinar",user.getFirstName());
        assertEquals("Hilmarsson",user.getLastName());
        assertEquals("steinarmarinohilmarsson@gmail.com",user.getEmail());
        assertEquals("steini",user.getDisplayName());
        assertEquals(LocalDate.of(1955,5,5), user.getBirthDate());


    }

    //Checka þetta betur!
    @Test
    public void getUserThatDoesNotExist()
    {
        User user = null;

        try
        {
            user = userServiceStubTest.getUserByID(99);
        }
        catch (ServiceException ex)
        {
            assertEquals("There is no user with this id", ex.getMessage());
        }
    }
}
