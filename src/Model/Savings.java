package Model;
import java.util.Calendar;

public class Savings extends Asset{
    /*
       People get interest on this account every month.
       At the end of each month account balance is updated using the updateAccounnt method.
       */
    public static int month;

    public Savings(){
        super();
        month = Calendar.getInstance().get(Calendar.MONTH);
    }

    public String toString(){
        /*
     Calls the constructor of assets and assigns the month to the current month using the calender.
     */
        double wrapperBalance = balance;
        return "Current balance: " + wrapperBalance;
    }

    public void updateAccount(){
        balance = (int) (balance + (balance * 0.001));
    }

    public static void setMonth(){
       /*
        checks if month is in between 0 to 12, and then assigns the static variable month to newMonth which is common for all the Savings accounts fro that month.
    */

        int newMonth = Calendar.getInstance().get(Calendar.MONTH);
        assert(newMonth > 0 && newMonth <= 12);
        month = newMonth;
    }

}
