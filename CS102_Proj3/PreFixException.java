
/**
 * @author George Miller
 * @version Nov 4, 2014
 * 
 * This exception class is thrown when something goes wrong with 
 * syntax or evaluation of prefix expressions
 *
 */
public class PreFixException  extends Exception{
	
	/**
	 * The default constructor used to throw an exception with no message
	 */
	public PreFixException(){
		super();
	}
	/**
	 * The constructor used to throw an exception with a message
	 * @param message
	 * A description of the error that occurred
	 */
	public PreFixException(String message){
		super(message);
	}

}
