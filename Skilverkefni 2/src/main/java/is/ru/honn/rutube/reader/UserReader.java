package is.ru.honn.rutube.reader;

import is.ru.honn.rutube.domain.User;
import is.ru.honn.rutube.domain.Video;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Teacher of the course.
 * @version unknown.
 * Given code by teacher, taken from myschool. Moved getInt function to AbstractReader
 */
public class UserReader extends AbstractReader{

    private VideoReader videoReader;

    public UserReader(VideoReader videoReader){
        this.videoReader = videoReader;
    }


    /**
     *
     * Walks through the json String and maps it to lists of users, which all contain 1 or more videos.
     *
     * @param content Json string with list of users.
     * @return Object which is a list of users.
     */
    public Object parse(String content) {

        //root object
        JSONObject jsonObject = (JSONObject) JSONValue.parse(content);

        // Get apiResults, this is an array so get the first (and only) item
        JSONArray apiResults = (JSONArray) jsonObject.get("apiResults");
        JSONObject jTmp = (JSONObject) apiResults.get(0);

        JSONArray jUsers = (JSONArray) jTmp.get("users");
        List<User> users = new ArrayList<>();

        jUsers.stream().forEach(jUser1 -> {
            JSONObject jUser = (JSONObject) jUser1;
            int userId = getInt(jUser, "userId");
            User user = new User(
                    userId,
                    (String) jUser.get("firstName"),
                    (String) jUser.get("lastName"),
                    (String) jUser.get("email"),
                    (String) jUser.get("displayName"),
                    (LocalDate) jUser.get("birthdate"));

            JSONArray jVideos = (JSONArray) jUser.get("videos");
            Object jvids = videoReader.parse(jVideos.toString());
            List<Video> videos = (List<Video>) jvids;
            user.setVideos(videos);

            users.add(user);
        });

        return users;
    }


}
