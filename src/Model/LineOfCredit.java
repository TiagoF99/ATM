package Model;

public class LineOfCredit extends Account {
    /*
        LineOfCredit helps the user withdraw money and transfer to other account.
        */
    public LineOfCredit()
    {
        super();
    }

    public void transferOut(User user, int transferInNum)
    {
        /*
    when user tries to withdraws money this method is called.
    */
        // transfer out to a non-user account.
        changeBalance(user, -transferInNum);
        append("phase2/info/outgoing.txt", "Transferred money from a line of credit account");
    }

}
