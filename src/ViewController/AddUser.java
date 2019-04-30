package ViewController;
import Model.*;
import javax.swing.*;

/*
This class is used for when you want to add another user to have access to an account you own.
 */
class AddUser extends Writer {
    /*
    This class is responsible for adding another user to an account a already existing user own.

     */
    void main(User user) {

        String name = checkUser();

        boolean found = false;
        int make;
        int index = 0;
        while(!found) {

            String id = JOptionPane.showInputDialog(null, user.Summary() + "type the account id for" +
                    "which you want to add this new user to.");
            make = Integer.parseInt(id);

            for (int i = 0; i < user.accounts.size(); i++) {
                if (user.accounts.get(i).id == make) {
                    found = true;
                    index = i;
                    break;
                }
            }
        }
        String[] data = helper(user, index, name);
        write("phase2/info/Accounts.txt", data[0], data[1]);
    }

    private String[] helper(User user, int index, String name) {

        String[] data = new String[2];

        Account account = user.accounts.get(index);
        String Users = getUsers(account.id);
        String new_username = Users + "," + name;
        String original;
        String new_string;

        if (account instanceof Chequing) {
            if (((Chequing) account).getPrimary()) {
                original = Users + " Chequing " + account.balance + " " + account.id + " true " +
                        account.recentTransaction;
                new_string = new_username + " Chequing " + account.balance + " " + account.id + " true " +
                        account.recentTransaction;
            } else {
                original = Users + " Chequing " + account.balance + " " + account.id + " false " +
                        account.recentTransaction;
                new_string = new_username + " Chequing " + account.balance + " " + account.id + " false " +
                        account.recentTransaction;
            }
        } else if (account.getClass().toString().equals("class SuperSavings")) {
            original = Users + " SuperSavings " + account.balance + " " + account.id + " " +
                    account.recentTransaction;
            new_string = new_username + " SuperSavings " + account.balance + " " + account.id + " " +
                    account.recentTransaction;
        }else if (account instanceof Savings) {
            original = Users + " Savings " + account.balance + " " + account.id + " " +
                    account.recentTransaction;
            new_string = new_username + " Savings " + account.balance + " " + account.id + " " +
                    account.recentTransaction;
        } else if (account instanceof CreditCard) {
            original = Users + " CreditCard " + account.balance + " " + account.id + " " +
                    account.recentTransaction;
            new_string = new_username + " CreditCard " + account.balance + " " + account.id + " " +
                    account.recentTransaction;
        } else {
            original = Users + " LineOfCredit " + account.balance + " " + account.id + " " +
                    account.recentTransaction;
            new_string = new_username + " LineOfCredit " + account.balance + " " + account.id + " " +
                    account.recentTransaction;}
        data[0] = original;
        data[1] = new_string;
        return data;
    }
}
