package is.ru.honn.rutube.domain;
import is.ru.honn.rutube.service.ServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *  @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 *  @version 1.0, 28/09/16.
 *
 * This class contains a constructor for a video as well as get and set functions.
 * The class also contains the addVideo function which adds a video to a list of videos.
 */
public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String displayName;
    private LocalDate birthDate;
    private List<Video> videos;

    /**
     * Constructor for User.
     *
     * @param userId The id of user.
     * @param firstName The first name of user.
     * @param lastName The last name of user.
     * @param email The email of user.
     * @param displayName The display name of user.
     * @param birthDate The birth date of user.
     */
    public User(int userId, String firstName, String lastName, String email, String displayName, LocalDate birthDate) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.displayName = displayName;
        this.birthDate = birthDate;

        videos = new ArrayList<Video>();

    }

    /**
     * Getter function for the id of user.
     *
     * @return The id of user.
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * Getter function for the first name of user.
     *
     * @return The first name of user.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Getter function for the last name of user.
     *
     * @return The last name of user.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Getter function for the email of user.
     *
     * @return The email of user.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Getter function for the display name of user.
     *
     * @return The display name of user.
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Getter function for the birth date of user.
     *
     * @return The birth date of user.
     */
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    /**
     * Getter function for the list of videos.
     *
     * @return The list of videos.
     */
    public List<Video> getVideos() {
        return this.videos;
    }

    /**
     * Setter function for the user id.
     *
     * @param userId The id of user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Setter function for the first name of user.
     *
     * @param firstName The first name of user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter function for the last name of user.
     *
     * @param lastName The last name of user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter function for the email of user.
     *
     * @param email The email of user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter function for the display name of user.
     *
     * @param displayName The display name of user.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Setter function for the birth date of user.
     *
     * @param birthDate The birth date of user.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Setter function for the video list.
     *
     * @param vidList The list of videos.
     */
    public void setVideos(List<Video> vidList)
    {
        for(Video v: vidList)
        {
            videos.add(v);
        }
    }

    /**
     * This function adds video to the list of videos.
     *
     * @param vid The video.
     * @throws ServiceException Throws exception if the video couldn't get added.
     */
    public void addVideo(Video vid) throws ServiceException {
        try{
            videos.add(vid);
        }
        catch (Exception ex)
        {
            throw new ServiceException("Can't add video");
        }
    }
}