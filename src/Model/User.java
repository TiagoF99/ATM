package Model;

import ViewController.Accountfactory;

import java.util.*;
public class User extends Writer {

    //leave password and username public so they can be accessed by worker class
    public String username;
    public String password;
    private Accountfactory factory;

    public User() {
        factory = new Accountfactory();
    }


    public ArrayList<Account> accounts = new ArrayList<>();

    /*
    Creates existing accounts in the file
     */
    public void AccountSetup() {
        factory.AccountSetup(username, accounts);
    }

    /*
    *This method creates the account and calls on the helper for the appropriate account
    * creates a new account and adds it to the file
     */
    public void makeAccount(String type) {
        factory.makeAccount(type, accounts, username);
    }


    public void makePrimary(){
        for (int i = 0; i < accounts.size() - 1; i++) {
            if (accounts.get(i) instanceof Chequing) {
                ((Chequing) accounts.get(i)).setPrimary(false);
            }
        }
        ( (Chequing) accounts.get(accounts.size() - 1)).setPrimary(true);
    }


    /*
    Needs to return a string of summary of transactions
    returns the account type and id and balance
    */
    public String Summary() {
        StringBuilder thing = new StringBuilder();
        for (Account account : accounts) {
            thing.append(account.id).append(" ").append(account.balance).append(" ").append(account.getClass()).append("\n");
        }
        return thing.toString();
    }

    public String credit_cards() {
        StringBuilder thing = new StringBuilder();
        for (Account account : accounts) {
            if (account instanceof CreditCard) {
                thing.append(account.id).append(" ").append(account.balance).append(" ").append(account.getClass()).append("\n");
            }
        }
        return thing.toString();
    }

    public String transferSummary() {
    /*
    Gives the summary after transfer is made this method is called by Controller
     */

        StringBuilder thing = new StringBuilder();
        for (Account account : accounts) {
            if (!(account instanceof CreditCard)) {
                thing.append(account.id).append(" ").append(account.balance).append(" ").append(account.getClass()).append("\n");
            }
        }
        return thing.toString();
    }


    public int netTotal() {
        int total = 0;
        for (Account account : accounts) {
            if (account instanceof Chequing || account instanceof Savings) {
                total += account.balance;}
            else {total -= account.balance;}}
        return total;
    }
}
