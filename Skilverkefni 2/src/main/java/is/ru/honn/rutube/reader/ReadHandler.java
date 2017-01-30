package is.ru.honn.rutube.reader;

import is.ru.honn.rutube.factory.FactoryException;

/**
 *  @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 *  @version 1.0, 02/10/16.
 *
 *  The interface of the ReadHandler.
 */

public interface ReadHandler
{
    void read(int count, Object object) throws ReaderException, FactoryException;
}