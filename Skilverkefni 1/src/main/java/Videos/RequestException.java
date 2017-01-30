package Videos;

/**
 * This class prints out error message
 *
 * @author Steinar Marinó Hilmarsson
 * @version 1.0, 2 sept 2016
 *
 */
public class RequestException extends Exception
{
    /**
     *
     * @param message The error message.
     */
    public RequestException(String message)
    {
        super(message);
    }
}
