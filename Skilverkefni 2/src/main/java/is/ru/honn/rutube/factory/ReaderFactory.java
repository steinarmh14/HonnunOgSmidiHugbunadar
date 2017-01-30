package is.ru.honn.rutube.factory;

import is.ru.honn.rutube.reader.Reader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *  @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 *  @version 1.0, 02/10/16
 *  This is a factory class which gets the Reader in the reader.xml and returns it.
 */
public class ReaderFactory
{
    private Reader reader;

    /**
     * Constructor.
     */
    public ReaderFactory()
    { }

    /**
     *  Gets the Reader type from the value which it is called with. Should be either userReader or videoReader
     *  else it throws factory exception.
     *
     * @param readerBean Is the Reader type. it is either userReader or videoReader.
     * @return The Reader type.
     * @throws FactoryException Throws factory exception if there was no bean with the name given.
     */
    public Reader getReaderType(String readerBean) throws FactoryException {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:reader.xml");

        try
        {
            Object r = ctx.getBean(readerBean);
            reader = (Reader)r;
            return reader;
        }
        catch(Exception ex)
        {
            throw new FactoryException("There was no bean found with that name");
        }
    }
}