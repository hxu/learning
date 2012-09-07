import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * RulesOf6005 represents some of the rules of 6.005 as described by the 
 * general information on Stellar. 
 * 
 * The course elements are described by the hasFeature function. 
 * 
 * The grade details are described by the computeGrade function.
 * 
 * The extension policy (slack days) are described by the extendDeadline function.
 */
public class RulesOf6005 {
	
	
	/**
	 * Tests if the string is one of the items in the Course Elements section. 
	 *  
	 * @param name - the element to be tested
	 * @return true if <name> appears in bold in Course Elements section. Ignores case (capitalization). 
	 * Example: "Lectures" and "lectures" will both return true.
	 */
	public static boolean hasFeature(String name){
		List<String> features = new ArrayList<String>();
		features.add("quizzes");
		features.add("code review");
		if (features.contains(name.toLowerCase())) {
		    return true;
		} else {
		    return false;
		}
		
		        
	}
	
	
	/**
	 * Takes in the quiz, pset, project, and participation grades as values out of a 
	 * hundred and returns the grade based on the course information also as a value out 
	 * of a hundred, rounded to the nearest integer. 
	 * 
	 * Behavior is unspecified if the values are out of range.
	 * 
	 * @param quiz - 20% weight
	 * @param pset - 40% weight
	 * @param project - 30% weight
	 * @param participation - 10% weight
	 * @return the resulting grade out of a hundred
	 */
	public static int computeGrade(int quiz, int pset, int project, int participation){
	    float q = quiz;
	    float ps = pset;
	    float pr = project;
	    float part = participation;
	    Float grade = new Float((0.2f * q) + (0.4f * ps) + (0.3f * pr) + (0.1f * part));
	    return Math.round(grade);
	}
	
	
	/**
	 * Based on the slack day policy, returns a date of when the assignment would be due, making sure not to
	 * exceed the budget. In the case of the request being more than what's allowed, the latest possible
	 * due date is returned. 
	 * 
	 * Hint: Take a look at http://download.oracle.com/javase/6/docs/api/java/util/GregorianCalendar.html
	 * 
	 * Behavior is unspecified if request is negative or duedate is null. 
	 * 
	 * @param request - the requested number of slack days to use
	 * @param budget - the total number of available slack days
	 * @param duedate - the original due date of the assignment
	 * @return a new instance of a Calendar with the date and time set to when the assignment will be due
	 */
	public static Calendar extendDeadline(int request, int budget, Calendar duedate){
	    int d = Math.min(budget, 3);
	    d = Math.min(d, request);
	    duedate.add(Calendar.DAY_OF_MONTH, d);
	    return duedate;
	}
	
	
	/**
	 * Main method of the class. Runs the functions hasFeature, computeGrade, and 
	 * extendDeadline. 
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println("Has feature QUIZZES: " + hasFeature("QUIZZES"));
		System.out.println("My grade is: " + computeGrade(60, 40, 50, 37));
		Calendar duedate = new GregorianCalendar();
		duedate.set(2011, 8, 9, 23, 59, 59);
		System.out.println("Original due date: " + duedate.getTime());
		System.out.println("New due date:" + extendDeadline(4, 2, duedate).getTime());
	}
}
