package rep_exposure;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Transaction is an immutable object representing a bank transaction.
 */
// This class has three different kinds of rep exposure -- what are they,
// and how can they be fixed?
public class Transaction {
    public int amount;
    public Calendar date;

    /**
     * ...
     * @param date Date of transaction.  Caller must never mutate date again!
     */
    public Transaction(int amount, Calendar date) {
        this.amount = amount;
        this.date = date;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public Calendar getDate() {
        return date;
    }
    
    
    
    
    
    /** @return a transaction of same amount as t, one month later */
    public static Transaction makeNextPayment(Transaction t) {
        Calendar d = t.getDate(); 
        d.add(Calendar.MONTH, 1);
        return new Transaction (t.getAmount(), d);
    }

    /** @return a list of 12 monthly payments of identical amounts */
    public static List<Transaction> makeYearOfPayments (int amount) {
        List<Transaction> list = new ArrayList<Transaction> (); 
        Calendar date = new GregorianCalendar (); 
        for (int i=0; i < 12; i++) {
            list.add (new Transaction (amount, date));
            date.add (Calendar.MONTH, 1);
        } 
        return list;
    }

}
