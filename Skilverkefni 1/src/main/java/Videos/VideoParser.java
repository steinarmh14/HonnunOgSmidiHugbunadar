package Videos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * This class prints out JSON objects
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 2 sept 2016
 *
 */
public class VideoParser
{
    private JSONParser parser;

    public VideoParser()
    {
        this.parser = new JSONParser();
    }

    /**
     *
     * @param s The text from the file.
     */
    public void parseAndPrint(String s)
    {
        try
        {
            JSONObject jsonObj = (JSONObject) parser.parse(s);
            String catalogName = (String) jsonObj.get("catalog_name");
            JSONArray catalogArr = (JSONArray) jsonObj.get("catalog");

            System.out.println("Catalog: " + catalogName);
            System.out.printf("%-25s%-15s%-30s\n", "Title:", "Type:", "Author");
            System.out.println("-------------------------------------------------------------");

            int counter = 0;
            while(counter < catalogArr.size())
            {
                JSONObject obj = (JSONObject) catalogArr.get(counter);
                String title = (String) obj.get("title");
                String type = (String) obj.get("type");
                String author = (String) obj.get("author_name");
                System.out.printf("%-25s%-15s%-30s\n", title, type, author);
                counter++;

            }
        }
        catch(Exception exception)
        {

            System.out.println(exception.getMessage());
        }
    }
}