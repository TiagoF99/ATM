package Model;
import java.util.Calendar;

public class SuperSavings extends Savings{

    public SuperSavings(){
        super();
        month = Calendar.getInstance().get(Calendar.MONTH);
    }

    public String toString(){
        double wrapperBalance = balance;
        return "Current balance: " + wrapperBalance;
    }

    public void updateAccount(User user){

        changeBalance(user, (int)((balance * 0.25) - 10));
    }

}
