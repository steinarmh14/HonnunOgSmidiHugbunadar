package is.ru.honn.rutube.service;

/**
 * @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 * @version 1.0, 28/09/16.
 * Exception class for services. Throws exception when there is some error in the service classes.
 */

public class ServiceException extends Exception {

    public ServiceException(String msg)
    {
        super(msg);
    }

    public ServiceException(String msg, Throwable throwable)
    {
        super(msg,throwable);
    }

}
