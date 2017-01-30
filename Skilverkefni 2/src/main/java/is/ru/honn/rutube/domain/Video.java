package is.ru.honn.rutube.domain;

import java.util.List;

/**
 *  @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 *  @version 1.0, 28/09/16.
 *  This class contains a constructor for a video as well as get and set functions.
 */
public class Video {

    private int videoId;
    private String title;
    private String description;
    private String source;
    private String videoType;
    private List<String> tags;

    /**
     * Constructor for video.
     *
     * @param videoId The id of the video.
     * @param title The title of the video.
     * @param description The description of the video.
     * @param source The source of the video.
     * @param videoType The type of the video.
     * @param tags The tag of the video.
     */
    public Video(int videoId, String title, String description, String source, String videoType, List<String> tags) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.source = source;
        this.videoType = videoType;
        this.tags = tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    /**
     * Getter function for the video id.
     *
     * @return The video id.
     */
    public int getVideoId()
    {
        return this.videoId;
    }

    /**
     * Getter function for the title of video.
     *
     * @return The title of video.
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * Getter for the description of video.
     *
     * @return The description of video.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Getter function for the source of video.
     *
     * @return The source of video.
     */
    public String getSource()
    {
        return this.source;
    }

    /**
     * Getter function for the type of video.
     *
     * @return The type of video.
     */
    public String getVideoType()
    {
        return this.videoType;
    }

    /**
     * Getter function for the tag of video.
     *
     * @return The tag of video.
     */
    public List<String> getTags()
    {
        return this.tags;
    }

    /**
     * Setter function for video id.
     *
     * @param videoId The id of video.
     */
    public void setVideoId(int videoId)
    {
        this.videoId = videoId;
    }

    /**
     * Setter function for the title of video.
     *
     * @param title The title of video.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Setter function for the description of video.
     *
     * @param description The description of video.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Setter function for the source of video.
     *
     * @param source The source of video.
     */
    public void setSource(String source)
    {
        this.source = source;
    }

    /**
     * Setter function for the type of video.
     *
     * @param videoType The type of video.
     */
    public void setVideoType(String videoType)
    {
        this.videoType = videoType;
    }

    /**
     * Setter function for the tag of video.
     *
     * @param tags The tag of video.
     */
    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }


}
