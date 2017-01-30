package is.ru.honn.rutube.service;

import is.ru.honn.rutube.domain.Video;

import java.util.List;

/**
 * @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 * @version 1.0, 28/09/16.
 *
 * The interface of VideoService
 */
public interface VideoService
{
    Video getVideo(int videoId) throws ServiceException;
    List<Video> getVideosbyUser(int userId) throws ServiceException;
    int addVideo(Video video, int userId) throws ServiceException;
}