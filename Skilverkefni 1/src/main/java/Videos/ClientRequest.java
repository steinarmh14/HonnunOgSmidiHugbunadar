package Videos;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class reads all content from a JSON file and converts all JSON objects to string
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 2 sept 2016
 *
 */

public class ClientRequest
{
    private JSONParser parser;

    //Constructor
    public ClientRequest()
    {
        this.parser = new JSONParser();
    }

    /**
     *
     * @param fileName The name of the file.
     * @return The text from the file.
     * @throws RequestException If there were some errors.
     */
    //This function gets text from a JSON file.
    public String getFileContent(String fileName) throws RequestException
    {
        try
        {
            Object obj = parser.parse(new FileReader(fileName)); //Get everything in file
            JSONObject jsonObj = (JSONObject) obj; //Convert everything from the file to a JSON object
            String retStr = jsonObj.toString(); //Convert JSON object to string which is then returned

            return retStr;
        }
        catch(IOException exception) //Catches exception if there were some
        {                            //problems to read the file
            throw new RequestException("Could not read from file!");
        }
        catch(ParseException exception) //Catches exception if there were some
        {                               //problems with parsing the file
            throw new RequestException("Could not parse from file");
        }
        catch(Exception exception) //Catches exception if there were some other errors
        {
            throw new RequestException("Unknown error!");
        }
    }
}
