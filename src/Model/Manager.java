package Model;
import javax.swing.*;

public class Manager extends Worker {
/*
     Manager has all the methods of a worker
     Manager sets the password and username of the user.
     Manager acn undo the transactions
     */

    public Manager() {}

    public void createLogin(User user, String username, String password) {
        /*
     sets the username and password of the user
     */
        user.password = password;
        user.username = username;
        //rest of code adds to login file the username and password of person
        append("phase2/info/login.txt", username + " " + password + "\n");
    }

    /*
    undo the most recent transaction on a specific account
     */
    public void undoTransactionControl(User user, String between) {
        /*
     undo the transaction made
     */
        if (user.accounts.size() == 0) {
            JOptionPane.showMessageDialog(null, "You have no accounts.");
        } else {
            Account account = user.accounts.get(0);
            for (int i = 0; i < user.accounts.size(); i++) {
                int id = new Integer(between);
                Integer ids = user.accounts.get(i).id;
                if (ids.equals(id)) {
                    account = user.accounts.get(i);
                }
            }
            String[] data = account.recentTransaction.split(",");
            if (data[1].equals("+")) {
                account.changeBalance(user, -(new Integer(data[2])));
            } else if (data[1].equals("-")) {
                account.changeBalance(user, new Integer(data[2]));
            }
        }
    }


}
