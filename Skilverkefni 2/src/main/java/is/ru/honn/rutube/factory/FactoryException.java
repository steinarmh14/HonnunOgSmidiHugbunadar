package is.ru.honn.rutube.factory;

/**
 *  @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 *  @version 1.0, 02/10/16.
 * Exception class for factories. Throws exception when there is some error in the Factory class.
 */
public class FactoryException extends Exception
{
    public FactoryException()
    {
        super();
    }

    public FactoryException(String message)
    {
        super(message);
    }

    public FactoryException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}

