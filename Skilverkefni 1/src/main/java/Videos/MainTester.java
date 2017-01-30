package Videos;

/**
 * This class test if it is possible to get a content of a JSONfile and prints it out
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 2 sept 2016
 *
 */
public class MainTester
{
    public static void main(String [] args) throws RequestException
    {

        VideoParser videoParser = new VideoParser();
        ClientRequest clientRequest = new ClientRequest();

        System.out.println("Result from the file: \n");

       try
        {
            String s = clientRequest.getFileContent("videos.json"); //Get text from the file
            videoParser.parseAndPrint(s); //Print it out
        }
        catch(RequestException exception)//Catches exception if reading file was impossible
        {
            throw new RequestException("Reading file error!!");
        }
    }
}
