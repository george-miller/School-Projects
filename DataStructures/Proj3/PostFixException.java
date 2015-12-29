

/**
 * @author George Miller
 * @version Nov 4, 2014
 * 
 * This exception class is thrown when something goes wrong with 
 * syntax or evaluation of postfix expressions
 *
 */
public class PostFixException extends Exception{
	
	/**
	 * The default constructor used to throw an exception with no message
	 */
	public PostFixException(){
		super();
	}
	
	/**
	 * The constructor used to throw an exception with a message
	 * @param message
	 * A description of the error that occurred
	 */
	public PostFixException(String message){
		super(message);
	}

}
