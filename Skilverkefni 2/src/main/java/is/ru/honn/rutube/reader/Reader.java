package is.ru.honn.rutube.reader;


/**
 *  @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 *  @version 1.0, 02/10/16.
 * The interface of the Reader.
 */
public interface Reader
{
    public Object read() throws ReaderException;
    public Object parse(String content);
    public void setURI(String URI) throws ReaderException;
    public void setReadHandler(ReadHandler readHandler) throws ReaderException;
    public boolean getIsURISet();

}