import is.ru.honn.rutube.domain.User;
import is.ru.honn.rutube.domain.Video;
import is.ru.honn.rutube.service.VideoServiceStub;
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
public class TestVideoService
{
    private VideoServiceStub videoServiceStubTest;

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
        videoServiceStubTest = new VideoServiceStub(userList);

    }

    @Test
    public void addVideoTest()
    {

        List<String> tags = new ArrayList<String>();
        tags.add("in purus");
        tags.add("vivamus vel");

        Video vid = new Video(1, "UfcFinals", "Ufc fighting", "www.ebay.com" , "mp3", tags);

        int counter = 0;
        try
        {
            counter = videoServiceStubTest.addVideo(vid,1);
        }
        catch (Exception ex)
        {

        }

        assertEquals(1,counter);

        List<String> tags2 = new ArrayList<String>();
        tags2.add("libero ut");
        tags2.add("tellus semper");

        Video vid2 = new Video(2, "Football", "Premier League", "www.facebook.com" , "mp4", tags);

        try
        {
            counter = videoServiceStubTest.addVideo(vid2,1);
        }
        catch (Exception ex)
        {

        }

        assertEquals(2, counter);

    }

    @Test
    public void addVideoFailNonExistingUserId()
    {
        List<String> tags = new ArrayList<String>();
        tags.add("in purus");
        tags.add("vivamus vel");

        Video vid = new Video(1, "UfcFinals", "Ufc fighting", "www.ebay.com" , "mp3", tags);

        try
        {
            videoServiceStubTest.addVideo(vid,99);
        }
        catch (Exception ex)
        {
            assertEquals("There was no user found with that id", ex.getMessage());
        }
    }

    @Test
    public void addVideoFailSourceAlreadyExist()
    {
        List<String> tags = new ArrayList<String>();
        tags.add("in purus");
        tags.add("vivamus vel");

        Video vid = new Video(1, "UfcFinals", "Ufc fighting", "www.ebay.com" , "mp3", tags);

        try
        {
            videoServiceStubTest.addVideo(vid,1);
        }
        catch (Exception ex)
        {

        }

        List<String> tags2 = new ArrayList<String>();
        tags2.add("in purus");
        tags2.add("vivamus vel");

        Video v = new Video(30, "UfcFinals", "Ufc fighting", "www.ebay.com" , "mp3", tags);

        try
        {
            videoServiceStubTest.addVideo(v,1);
        }
        catch (Exception ex)
        {
            assertEquals("Cant add video with this source", ex.getMessage());
        }
    }

    @Test
    public void addVideoFailTitleAlreadyExist()
    {
        List<String> tags = new ArrayList<String>();
        tags.add("in purus");
        tags.add("vivamus vel");

        Video vid = new Video(1, "UfcFinals", "Ufc fighting", "www.ebay.com" , "mp3", tags);

        try
        {
            videoServiceStubTest.addVideo(vid,1);
        }
        catch (Exception ex)
        {

        }

        List<String> tags2 = new ArrayList<String>();
        tags2.add("in purus");
        tags2.add("vivamus vel");

        Video v = new Video(30, "UfcFinals", "Ufc fighting", "www.facebook.com/steinarmh" , "mp3", tags);

        try
        {
            videoServiceStubTest.addVideo(v,1);
        }
        catch (Exception ex)
        {
            assertEquals("Cant add video with this title", ex.getMessage());
        }
    }

    @Test
    public void gettingVideoThatDoesNotExist()
    {
        try
        {
            videoServiceStubTest.getVideo(99);
        }
        catch (Exception ex)
        {
            assertEquals("There is no video with this id", ex.getMessage());
        }
    }

    @Test
    public void gettingVideoThatExist()
    {

        List<String> tag = new ArrayList<String>();
        tag.add("iaculis congue");
        tag.add("id");

        Video video = new Video(20, "Handball", "Handball competition", "www.handball.com" , "avi", tag);
        try
        {
            videoServiceStubTest.addVideo(video,1);
        }
        catch (Exception ex)
        {

        }


        Video vid = null;

        try
        {
            vid = videoServiceStubTest.getVideo(20);
        }
        catch (Exception ex)
        {

        }

        List<String> tagsTemp = vid.getTags();

        assertEquals(20,vid.getVideoId());
        assertEquals("Handball",vid.getTitle());
        assertEquals("Handball competition", vid.getDescription());
        assertEquals("www.handball.com", vid.getSource());
        assertEquals("avi", vid.getVideoType());
        assertEquals("iaculis congue", tagsTemp.get(0));
        assertEquals("id", tagsTemp.get(1));

    }
}
