
import java.util.Scanner;




/**
 * @author George Miller
 * @version Nov 3, 2014
 * 
 * This class contains many methods used to modify expressions of single digits and operands
 *
 */
public class ExpressionTools {
	
	
	/**
	 * This method converts the input infix expression into a postfix expression
	 * @param in
	 * The input infix expression
	 * @return
	 * The postfix expression
	 */
	public static String infixToPostfix(String in) {
		// Initialize the output string and the operand stack
		Scanner input = new Scanner(in);
		String output = "";
		Stack<String> stack = new Stack<String>();
		// iterate through the string from left to right, ignoring when the character we are at is a space
		while (input.hasNext()){
			
			// if the character is a digit, add it to the postfix expression
			if (input.hasNextInt()){
				output += input.next() + " ";
				continue;
			}
			String next = input.next();

			// Push '(' onto the operand stack
			if (next.equals("(")){
				stack.push(next);
			}
			// if the character is an operator, and the top of the stack is of higher precedence, 
			// add the top of the stack to the output.  Add the character to the stack
			else if (next.equals("*") || next.equals("/") || next.equals("+") || next.equals("-")){
				
				if (stack.getTop() != null){
					while (stack.getTop() != null && operationPrecidence(stack.getTop().getItem(), next)){
						output += stack.pop() + " ";
					}
				}
				stack.push(next);
			}
			// if the character is a ')' add things off the stack to the output until we reach an '('
			else if (next.equals(")")){
				while(stack.getTop() != null){
					if (stack.peek() != "("){
						output += stack.pop() + " ";
					}
					else{
						stack.pop();
						break;
					}
				}
			}
			
		}
		// add the remaining operators to the output
		while (stack.getTop() != null){
			output += stack.pop() + " ";
		}
		input.close();
		// return the output
		return output;
	}
	
	/**
	 * This method converts an infix expression into a prefix expression
	 * @param in
	 * The infix expression
	 * @return
	 * The prefix expression that's equivalent to the infix expression passed as a parameter
	 */
	public static String infixToPrefix(String in){
		// First we must reverse the string inputted
		String inReversed = "";
		for (int i = in.length()-1; i >= 0; i--){
			inReversed += in.charAt(i);
		}
		Stack<String> stack = new Stack<String>();
		Scanner input = new Scanner(inReversed);
		String output = "";
		
		// Iterate through the entire input
		while (input.hasNext()){
			
			// If it's an operand, append it to the output
			if (input.hasNextInt()){
				output += input.next() + " ";
				continue;
			}
			
			// If it's a close parenthesis, push it onto the stack
			String next = input.next();
			if (next.equals(")")){
				stack.push(next);
			}
			
			// If it's an operator, and the stack isn't empty, pop off the stack until a operator of the 
			// same or higher precedence is encountered, then push it onto the stack
			else if (next.equals("*") || next.equals("/") || next.equals("-") || next.equals("+")){
				if (stack.getTop() != null){
					while (stack.getTop() != null && operationPrecidence(stack.getTop().getItem(), next)){
						output += stack.pop() + " ";
					}
				}
				stack.push(next);
			}
			// If it's an open parenthesis, pop off the stack until we find the matching close parenthesis
			else if (next.equals("(")){
				while(stack.getTop() != null){
					if (!stack.peek().equals(")")){
						output += stack.pop() + " ";
					}
					else{
						stack.pop();
						break;
					}
				}
			}
			
		}
		// clear the rest of the operator stack
		while (stack.getTop() != null){
			output += stack.pop() + " ";
		}
		input.close();
		
		// reverse the output and return it
		String outputReversed = "";
		
		for (int i = output.length()-1; i >= 0; i--){
			outputReversed += output.charAt(i);
		}
		return outputReversed;
	}
	
	
	/**
	 * This method calculates and returns the value of the given postFix string
	 * @param in
	 * The postFix expression
	 * @return
	 * The result of the expression
	 * @throws PostFixException 
	 * Throws this exception if something is wrong with 
	 * the expression given
	 */
	public static int postfixCalculte(String in) throws PostFixException{
		// Initialize the stack
		Stack<Integer> stack = new Stack<Integer>();
		Scanner input = new Scanner(in);
		
		// iterate through the input left to right, ignoring if the character is a space
		while (input.hasNext()){
			
				
			// if the character is a digit, add it onto the stack
			if (input.hasNextInt()){
				stack.push(input.nextInt());
			}
			// if the character is an operator, compute the result of the first two items on the stack
			else {
				String operator = input.next();
				
				// If the stack is empty, the syntax is invalid
				if (stack.getTop() == null){
					input.close();
					throw new PostFixException("The stack is empty - not enough operands - possible invalid syntax");
				}
				
				Integer operand2 = stack.pop();
				
				// If the stack is empty, the syntax is invalid
				if (stack.getTop() == null){
					input.close();
					throw new PostFixException("The stack is empty - not enough operands - possible invalid syntax");
				}
				// Compute and push back onto stack
				Integer operand1 = stack.pop();
				Integer result = 0;
				if (operator.equals("*"))
					result = operand1 * operand2;
				if (operator.equals("/"))
					result = operand1/operand2;
				if (operator.equals("+"))
					result = operand1+operand2;
				if (operator.equals("-"))
					result = operand1-operand2;
				// push the result onto the stack
				stack.push(result);
			}
			
		}
		// if the stack is empty, there was probably a syntax error
		if (stack.getTop() == null){
			input.close();
			throw new PostFixException("The stack is empty - not enough operands - possible invalid syntax");
		}
		input.close();
		// return the result
		return stack.getTop().getItem();
	}
	
	/**
	 * This method is the helper method for the recursive prefix calculate method
	 * @param in
	 * The prefix expression in string form (with spaces separating the operands and operators)
	 * @return
	 * The result of the prefix expression
	 * @throws PreFixException
	 * This exception is thrown if an error occurs while calculating the result
	 */
	public static int prefixCalculate(String in) throws PreFixException{
		// create a Scanner then start the recursive method
		Scanner input = new Scanner(in);
		
		return prefixCalculateRec(input);
		
	}
	
	/**
	 * This is a recursive implementation of a method that calculates the result of a prefix expression
	 * @param input
	 * The prefix expression in a Scanner object
	 * @return
	 * The result of the expression
	 * @throws PreFixException
	 * This exception is thrown if an error occurs while calculating the result
	 */
	public static int prefixCalculateRec(Scanner input) throws PreFixException{
		// If the method was called when the scanner is empty, it's a syntax error
		if (!input.hasNext()){
			throw new PreFixException("The recursive method was called with the Scanner being empty - possibly a syntax error in the input");
		}
		// If the next token is a number, return it
		if (input.hasNextInt()){
			return input.nextInt();
		}
		// If the next token is an operator, do the operation on the next 2 tokens in the Scanner
		String next = input.next();
		if (next.equals("*")){
			return prefixCalculateRec(input) * prefixCalculateRec(input);
		}
		else if (next.equals("/")){
			return prefixCalculateRec(input) / prefixCalculateRec(input);
		}
		else if (next.equals("+")){
			return prefixCalculateRec(input) + prefixCalculateRec(input);
		}
		else if (next.equals("-")){
			return prefixCalculateRec(input) - prefixCalculateRec(input);
		}
		// If the next token isn't a digit or an operator, it's a syntax error
		else
			throw new PreFixException("Unexpected String encountered when calculating prefix expression - syntax error");
	}
	
	
	/**
	 * This method determines if the operand x comes before y(true)
	 * or after y(false) in the order of operations.  x and y must be one of
	 * these single string characters : *, /, +, -
	 * @param x
	 * The operand to be compared
	 * @param y
	 * The other operand to be compared
	 * @return
	 * true if x is before y, false if x is after y
	 */
	private static boolean operationPrecidence(String x, String y){
		// If x is '(', this method isn't valid and thus should be false
		if (x.equals("(") || x.equals(")")) return false;
		// make sure x and y are correct inputs
		if ((x.equals("*") || x.equals("/") || x.equals("+") || x.equals("-")) && 
				(y.equals("*") || y.equals("/") || y.equals("+") || y.equals("-") )){
			
			// the only time this method should return true is when x is either * or /
			// and y is either + or -
			if (x.equals("*") || x.equals("/")){
				if (y.equals("+") || y.equals("-")){
					return true;
				}
				else return false;
			}
			else return false;
		}
		else throw new IllegalArgumentException("Invalid inputs for the operationPredidence method");
		
	
	}
	
	
}
