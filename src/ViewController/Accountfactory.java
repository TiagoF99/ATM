package ViewController;
import Model.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/*
This class is used to set up a users accounts when the program is started as well as make new accounts for a user
 */
public class Accountfactory extends Writer {
    /*
    This class is always called in order to set up all the accounts when machine is on.
    */
    public void AccountSetup(String username, ArrayList<Account> accounts) {
        //reads through the file and sets up the users accounts that are saved in file.
        try {
            File file = new File("phase2/info/Accounts.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            //st is the current line in the file
            while ((st = br.readLine()) != null) {
                String[] data = st.split(" ");
                //splits username String up to potentially multiple user names on one account
                String[] name = data[0].split(",");
                //check if desired username is in this account
                boolean found = false;
                for (String s : name) {
                    if (s.equals(username)) {
                        found = true;}}
                if (found) {
                    if ("Chequing".equals(data[1])) {
                        Chequing account = new Chequing();
                        account.balance = new Integer(data[2]);
                        account.id = new Integer(data[3]);
                        if (data[4].equals("true")) {
                            account.setPrimary(true);
                        } else if (data[4].equals("false")) {
                            account.setPrimary(false);
                        }
                        account.recentTransaction = data[5];
                        accounts.add(account);
                    } else if ("Savings".equals(data[1])) {
                        Savings account = new Savings();
                        helperSetup(account, data, accounts);
                    } else if ("CreditCard".equals(data[1])) {
                        CreditCard account = new CreditCard();
                        helperSetup(account, data, accounts);
                    } else if ("LineOfCredit".equals(data[1])) {
                        LineOfCredit account = new LineOfCredit();
                        helperSetup(account, data, accounts);
                    } else if ("SuperSavings".equals(data[1])) {
                        SuperSavings account = new SuperSavings();
                        helperSetup(account, data, accounts);
                    }
                }}
        } catch (java.io.IOException e) {System.out.println("Problem reading file.");}
    }

    private void helperSetup(Account account, String[] data, ArrayList<Account> accounts) {
        /*
        Assigns the account instance variables
         */
        account.balance = new Integer(data[2]);
        account.id = new Integer(data[3]);
        account.recentTransaction = data[4];
        accounts.add(account);
    }

    public void makeAccount(String type, ArrayList<Account> accounts, String username) {
        /*
        Makes the account by calling makeAccountHelper by passing in variables into it based on which account it is.
         */
        String file = "phase2/info/Accounts.txt";

        if ("Chequing".equals(type)) {Chequing account = new Chequing();
            makeAccountHelper("Chequing", account, file, accounts, username);
        } else if ("Savings".equals(type)) {Savings account = new Savings();
            makeAccountHelper(type, account, file, accounts, username);
        } else if ("LineOfCredit".equals(type)) {LineOfCredit account = new LineOfCredit();
            makeAccountHelper(type, account, file, accounts, username);
        } else if ("CreditCard".equals(type)) {CreditCard account = new CreditCard();
            makeAccountHelper(type, account, file, accounts, username);
        } else if ("SuperSavings".equals(type)) {SuperSavings account = new SuperSavings();
            makeAccountHelper(type, account, file, accounts, username);
        }
    }

    /*
     Adds the account to the array of accounts we have
     This method takes the account type and writes it to the file in the appropriate format
     */
    private void makeAccountHelper(String type,Account account,String file, ArrayList<Account> accounts, String username)  {

        accounts.add(account);
        account.id = getId();

        if (!type.equals("Chequing")) {
            append(file, username + " " + type + " " + account.balance + " " +account.id +" "+account.recentTransaction+ "\n");
        } else {
            if ((((Chequing) account).getPrimary())) {
                append(file, username + " " + type + " " + account.balance + " " + account.id + " true "
                        + account.recentTransaction + "\n");
            } else {append(file, username + " " + type + " " + account.balance + " " + account.id + " false "
                        + account.recentTransaction + "\n");}
        }
    }
    private int getId() {
        int id = 1;
        //gets most recent id
        try {
            File file = new File("phase2/info/id.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {id = new Integer(st);}
        } catch (java.io.IOException e) {System.out.println("Problem reading file.");}

        //updates it for next new account
        try {
            int num = id + 1;
            FileWriter fr = new FileWriter("phase2/info/id.txt", false);
            fr.write(String.valueOf(num));
            fr.close();
        } catch (java.io.IOException e) {System.out.println("Problem reading file.");}
        return id;
    }
}
