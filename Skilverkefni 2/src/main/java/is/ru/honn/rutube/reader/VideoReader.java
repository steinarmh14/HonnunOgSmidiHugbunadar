package is.ru.honn.rutube.reader;

import is.ru.honn.rutube.domain.Video;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teacher of the course.
 * @version unknown.
 * Given code by teacher, taken from myschool. Moved getInt function to AbstractReader.
 */

public class VideoReader extends AbstractReader{

    public VideoReader(){}

    /**
     * Walks through a json array of videos and maps it to a object which is a list of videos.
     *
     * @param content Should be a json Array of videos.
     * @return Object which is a list of videos.
     */
    public Object parse(String content){

        JSONArray jVideos = (JSONArray) JSONValue.parse(content);
        List<Video> videos = new ArrayList<>();

        jVideos.stream().forEach(jVideo -> {
            JSONObject jVid = (JSONObject) jVideo;
            int videoId = getInt(jVid, "videoId");

            Video video = new Video(
                    videoId,
                    (String) jVid.get("title"),
                    (String) jVid.get("description"),
                    (String) jVid.get("src"),
                    (String) jVid.get("type"),
                    (List<String>) jVid.get("tags")
            );
            videos.add(video);
        });

        return videos;
    }
}
