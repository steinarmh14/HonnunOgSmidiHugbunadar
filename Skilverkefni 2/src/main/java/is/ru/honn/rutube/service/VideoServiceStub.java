package is.ru.honn.rutube.service;

import is.ru.honn.rutube.domain.User;
import is.ru.honn.rutube.domain.Video;
import java.util.List;

/**
 * @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 * @version 1.0, 28/09/16.
 * This class contains a setter function for the user list as well as getter functions where you can
 * either get a video by requested video id or get all videos from a certain user by requested user id.
 * The class also contains the addVideo function which adds a video to the video list.
 */
public class VideoServiceStub implements VideoService
{

    private List<User> userList;

    /**
     * Setter function for the list of users.
     *
     * @param list The list of users.
     */
    public VideoServiceStub(List<User> list)
    {
        userList = list;
    }

    /**
     * This functions gets a video by requested video id or throws an exception if either the list of
     * users is empty (if there are no users then there are no videos) or if the requested id does not
     * match with any of the videos.
     *
     * @param videoId The id of video.
     * @return The video with requested id.
     * @throws ServiceException Throws exception if there are no users or if there was no video with that particular id.
     */
    public Video getVideo(int videoId) throws ServiceException
    {
        if(userList.isEmpty())
        {
            throw new ServiceException("There are no users and therefore there are no videos");
        }
        else
        {
            for(User u: userList)
            {
                List<Video> videoList = u.getVideos();
                for(Video v: videoList)
                {
                    if(v.getVideoId() == videoId)
                    {
                        return v;
                    }
                }
            }
        }

        throw new ServiceException("There is no video with this id");
    }

    /**
     * This functions gets videos of a single user by requested user id or throws an exception if the requested id
     * does not match with any of the users.
     *
     * @param userId The id of user.
     * @return The list of videos of requested user.
     * @throws ServiceException Throws exception if there was no user with this particular id.
     */
    public List<Video> getVideosbyUser(int userId) throws ServiceException {
        for(User u: userList)
        {
            if(u.getUserId() == userId)
            {
                return u.getVideos();
            }
        }

        throw new ServiceException("There is no user with this id");
    }

    /**
     * This function adds a video to the video list and returns the size of the list or throws an exception.
     * Exception is thrown if the user list is either empty (If there are no users then you cannot add a video)
     * or if the requested id does not match any user.
     * Exception is also thrown if some attributes are in use. Those include the id, source or title.
     *
     * @param video The video.
     * @param userId The id of user.
     * @return The size of the video list.
     * @throws ServiceException throws exception if id, source or title was in use.
     * It also throws exception if there are no user in the list or if there is no user with this id.
     */
    public int addVideo(Video video, int userId) throws ServiceException {


        if(userList.isEmpty())
        {
            throw new ServiceException("There are no users therefore you cant add this video to user");
        }
        for(User u: userList)
        {
            if(userId == u.getUserId())
            {
                List<Video> vidList = u.getVideos();
                for(Video v: vidList)
                {
                    if(v.getVideoId() == video.getVideoId())
                    {
                        throw new ServiceException("This id is already in use");
                    }
                    if(v.getSource() == video.getSource())
                    {
                        throw new ServiceException("This source is already in use");
                    }
                    if(v.getTitle() == video.getTitle())
                    {
                        throw new ServiceException("This title is already in use");
                    }
                }

                u.addVideo(video);
                return u.getVideos().size();
            }
        }

        throw new ServiceException("There was no user found with that id");
    }

}
