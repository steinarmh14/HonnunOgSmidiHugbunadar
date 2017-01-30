package is.ru.honn.rutube.reader;

import org.json.simple.JSONObject;


/**
 *  @author Steinar Marinó Hilmarsson and Egill Gautur Steingrímsson.
 *  @version 1.0, 02/10/16.
 *  This class implements the reader interface.
 */
public abstract class AbstractReader implements Reader{

    private String URI;
    private ReadHandler readHandler;
    private String contentFromURI;
    private boolean isURIset;

    /**
     * Constructor.
     */
    public AbstractReader()
    {
        this.isURIset = false;
    }

    /**
     * Parses everything which is gotten from the URI
     * @param content The content from the URI.
     * @return Either list of Users or Videos.
     */
    public abstract Object parse(String content);

    /**
     * Reads the file from the URI and parses it into a Object list.
     * @return Either list of Users or Videos.
     * @throws ReaderException throws exception if there is no readhandler or if it faild to get the content from URI.
     */
    public Object read() throws ReaderException {

        if(readHandler != null)
        {
            try
            {
                getContentFromURI(this.URI);
            }
            catch (Exception ex)
            {
                throw new ReaderException("Failed to get content from URI");
            }

            Object objList = parse(this.contentFromURI);

            return objList;
        }
        else
        {
            throw new ReaderException("There is no read handler");
        }

    }

    /**
     *  Gets the content from the URI
     * @param URI The URI which we get the content from.
     * @throws ReaderException Throw failed to get content from URI.
     */
    public void getContentFromURI(String URI) throws ReaderException {
        try
        {
            ClientRequest clientRequest = new ClientRequest();
            this.contentFromURI = clientRequest.getRequest(this.URI);
        }
        catch (Exception ex)
        {
            throw new ReaderException("Failed to get content from URI");
        }

    }

    /**
     * Sets the URI
     * @param URI The URI which we get the content from.
     * @throws ReaderException Throws reader exception if there were some complications in setting URI.
     */
    public void setURI(String URI) throws ReaderException
    {
        try
        {
            this.URI = URI;
            this.isURIset = true;
        }
        catch (Exception ex)
        {
            throw new ReaderException("Unable to set URI");
        }
    }

    /**
     * Getter function for the URI
     * @return The URI
     */
    public boolean getIsURISet()
    {
        return this.isURIset;
    }

    public void setReadHandler(ReadHandler readHandler) throws ReaderException {
        try
        {
            this.readHandler = readHandler;

        }
        catch (Exception ex)
        {
            throw new ReaderException("Unable to set readHandler");
        }
    }



    /**
     *
     * @param jParent Json parent containing an integer field.
     * @param name name of the integer field.
     * @return int value of the json int in the jParent object.
     */
    protected static int getInt(JSONObject jParent, String name)
    {
        if(jParent == null)
            return 0;
        Long value = (Long)jParent.get(name);
        if(value == null)
            return 0;
        return value.intValue();
    }

}
