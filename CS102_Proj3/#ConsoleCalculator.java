import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author George Miller
 * @version Nov 6, 2014
 *
 *	This class is the main method used to test and utilize the ExpressionTools class.
 *	This class uses command line arguments to control input and output.  
 *  The first argument is the name of the input file that contains line of expressions to be calculated
 *  The second and third arguments are the names of the two output files that will become the solutions
 *  of the expressions using postfix and prefix notation respectively
 */
public class ConsoleCalculator {
	public static void main(String [] args){
		
		File input = null;
		File outputPostfix = null;
		File outputPrefix = null;
		// try to initialize the files with the arguments given
		try{
			input = new File(args[0]);
			outputPostfix = new File(args[1]);
			outputPrefix = new File(args[2]);
		}
		catch(NullPointerException e){
			System.err.println("Some or all pathnames inputted were null (3 required at command line");
			System.exit(1);
		}
		// Check if all the files can do the appropriate operations
		if (!(input.canRead() && outputPostfix.canWrite() && outputPrefix.canWrite())){
			System.err.println("Cannot read/write to one or more of the files inputted");
			System.exit(1);
		}
		// Try to initialize the scanner and print writers
		Scanner in = null;
		PrintWriter postfix = null;
		PrintWriter prefix = null;
		try{
			in = new Scanner(input);
			postfix = new PrintWriter(outputPostfix);
			prefix = new PrintWriter(outputPrefix);
		}
		catch (FileNotFoundException e){
			System.err.println("Cannot find one or more of the files");
			System.exit(1);
		}
		
		// for every line in the input file, try to convert and calculate the result
		// If an exception is encountered, print INVALID to the output file
		while (in.hasNextLine()){
			String line = in.nextLine();
			String postfixEx = ExpressionTools.infixToPostfix(line);
			String prefixEx = ExpressionTools.infixToPrefix(line);
			
			int resultPost = 0;
			try{
				resultPost = ExpressionTools.postfixCalculte(postfixEx);
			}
			catch (PostFixException e){postfix.println("INVALID");}
			
			int resultPre = 0;
			try{
				resultPre = ExpressionTools.prefixCalculate(prefixEx);
			}
			catch (PreFixException e){prefix.println("INVALID");}
			
			postfix.println(resultPost);
			prefix.println(resultPre);
		}
		
		
		in.close();
		postfix.close();
		prefix.close();
		
	}
}
