package ViewController;
import Model.*;
import javax.swing.*;

/*
This class is the front end for Account creation.
 */
class CreateAccountInterface {

    void CreateAccountFront(User user) {

        String type = JOptionPane.showInputDialog(null, "Type 1 for: A credit card account \n " +
                "2 for: A line of credit account \n" + "3 for: A chequing Model.Account \n" +
                "4 for: A savings account \n" + "5 for: a SuperSavings account" );
        int make = Integer.parseInt(type);

        while(make >= 6 || make <= 0) {

            type = JOptionPane.showInputDialog(null, "You typed an invalid number \n"+"Type 1 for: A credit card account \n " +
                    "2 for: A line of credit account \n" + "3 for: A chequing Model.Account \n" +
                    "4 for: A savings account \n" + "5 for: a SuperSavings account" );
            make = new Integer(type);

        }
        createAccount(user, make);
    }

    private void createHelper3(User user) {
        int count = 0;
        for (int i = 0; i < user.accounts.size(); i++) {
            if (user.accounts.get(i) instanceof Chequing) {
                count++;
            }
        }

        if (count == 0) {
            JOptionPane.showMessageDialog(null, "This account will be set as your primary account");
            user.makeAccount("Chequing");
            user.makePrimary();
        } else {
            String primary = JOptionPane.showInputDialog(null, "It seems you already have a" +
                    "chequing account. Would you like to make this new account the primary one? (yes or no)");
            user.makeAccount("Chequing");
            if (primary.equals("yes")) {
                user.makePrimary();
            }
        }
    }

    private void createAccount(User user, int make) {
        if (make == 1) {
            user.makeAccount("CreditCard");
        } else if (make == 2) {
            user.makeAccount("LineOfCredit");
        } else if (make == 3) {
            createHelper3(user);
        } else if (make == 4) {
            user.makeAccount("Savings");
        } else {
            user.makeAccount("SuperSavings");
        }
    }
}
