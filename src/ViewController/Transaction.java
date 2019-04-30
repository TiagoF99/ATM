package ViewController;
import Model.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;

/*
This is the main front end and controller class for all options that have to do with transactions1
 */
class Transaction extends Writer {
    /*
    helps make transactions, like money transfer from one account to other and withdraw and put in money.
     */
    private int transactionInterfaceHelper(User user) {
        boolean found = false; int index = 0;
        while (!found) {
            String between = JOptionPane.showInputDialog(null, user.Summary() + "Type the account number" +
                    "for which you want to withdraw from (Note: You cannot withdraw from a Credit Card account you own)");
            int make = Integer.parseInt(between);
            for (int i = 0; i < user.accounts.size(); i++) {if (make == user.accounts.get(i).id){found = true; index = i;}}}
        return index;
    }

    private Object[] transactionInterfaceHelper2(String input1) {

        Object[] datas = new Object[2];
        int accountsOther = 0;
        String beginning = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("phase2/info/Accounts.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split(" ");
                String[] names = data[0].split(",");
                int count = 0;
                for (String name : names) {
                    if (name.equals(input1)) {
                        count++;
                    }
                }
                if (count > 0 && data[1].equals("Chequing")) {
                    accountsOther++;
                    beginning = data[0];
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("Something wrong with reading the file");
        }
        datas[0] = accountsOther;
        datas[1] = beginning;
        return datas;
    }

    private void transactionInterfaceHelper4(User user) {
        String input1 = checkUser();
        //checks how many chequing accounts that user has
        Object[] data = transactionInterfaceHelper2(input1);
        transactionInterfaceHelper5(user, data[1].toString(), (int) data[0]);
    }

    private void transactionInterfaceHelper5(User user, String input1, int accountsOther) {
        if (accountsOther == 0) {
            JOptionPane.showMessageDialog(null, "The user you want to send money to does not have a chequing account. This transaction" +
                    "is impossible");
        } else {
            String money = JOptionPane.showInputDialog(null, "Enter the value you want to send this user: " +
                    "(Note: this will be deposited into the users primary chequing account)");
            int amount = new Integer(money);

            Account account = user.accounts.get(transactionInterfaceHelper(user));
            account.changeBalance(user, -amount);

            //code to edit the file so that it changes the balance on the other users primary chequing () both try blocks
            String[] array = transactionInterfaceHelper3(input1);
            int new_balance = amount + Integer.parseInt(array[0]);

            write("phase2/info/Accounts.txt", input1 + " Chequing " + array[0] + " " + array[1] + " true " + array[2],
                    input1 + " Chequing " + new_balance + " " + array[1] + " true " + array[2]);
        }
    }

    private String[] transactionInterfaceHelper3(String input1) {

        StringBuilder oldBalance = new StringBuilder();
        StringBuilder id = new StringBuilder();
        StringBuilder recent = new StringBuilder();
        String[] arr = new String[3];
        try {
            BufferedReader br = new BufferedReader(new FileReader("phase2/info/Accounts.txt"));
            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split(" ");
                if (data[0].equals(input1) && data[1].equals("Chequing") && data[4].equals("true")) {
                    oldBalance.append(data[2]); id.append(data[3]); recent.append(data[5]);
                }
            }
        } catch (java.io.IOException e) {System.out.println("Something wrong with reading the file-");}
        arr[0] = oldBalance.toString(); arr[1] = id.toString(); arr[2] = recent.toString();
        return arr;
    }

    private void transactionTransferBetween(Controller controller, User user) {
        String between = JOptionPane.showInputDialog(null, user.transferSummary() + "Type the account number" +
                "for which you want to transfer money from (1, 2, ...)");
        String between2 = JOptionPane.showInputDialog(null, user.Summary() + "Type the account number" +
                "for which you want to transfer money to (1, 2, ...)");
        String between3 = JOptionPane.showInputDialog(null, "Type an appropriate value to transfer");

        controller.transferBetween(between, between2, between3, user);
    }

    private void transactionTransferBetween2(Controller controller, User user) {


        // For pay credit card balance payoff
        String from = JOptionPane.showInputDialog(null, user.transferSummary() + "\n Type the account number " +
                "for which you want to payoff credit card balance from.");

        int id2 = Integer.parseInt(from);
        boolean found2 = false;
        for (int i = 0; i < user.accounts.size(); i++) {
            if (id2 == user.accounts.get(i).id) {
                found2 = true;
            }
        }
        // If "from" account exists
        if (found2) {
            String to = JOptionPane.showInputDialog(null, user.credit_cards() + "\n Type the account number" +
                    " of credit card for which you want to pay balance off");
            int id = Integer.parseInt(to);

            Account account = user.accounts.get(0);
            boolean found = false;
            for (int i = 0; i < user.accounts.size(); i++) {
                if (id == user.accounts.get(i).id) {
                    account = user.accounts.get(i);
                    found = true;
                }
            }
            int amount = account.balance;
            // If "to" account exists
            if (found) {
                if (amount >= 0) {
                    JOptionPane.showMessageDialog(null, "Your credit card balance is positive");
                } else {
                    String payoff = JOptionPane.showInputDialog(null, "Your credit card balance is " + amount +
                            " do you want to payoff?    (Yes/No)");
                    if (payoff.equals("yes")) {controller.transferBetween2(from, to, amount, user);}
                }
            } else {JOptionPane.showMessageDialog(null, "No such account exist. Ending session.");}
        } else {JOptionPane.showMessageDialog(null, "No such account exist. Ending session.");}
    }

    private void transactionDepositMoney(User user, Machine machine, Employee employee) {
        /*
        this method is called for depositing money in the createTransactionInterface.
         */

        String input = JOptionPane.showInputDialog(null, "Type 1 to deposit CAD and type 2 " +
                "to deposit EURO");

        int choice = new Integer(input);
        int fifty = 0; int twenty = 0; int ten = 0; int five = 0;
        String input1,input2, input3, input4;

        if (choice == 1) {
            input1 = JOptionPane.showInputDialog(null, "Type how many fifty dollar bills you want to deposit:");
            fifty = new Integer(input1);
            input2 = JOptionPane.showInputDialog(null, "Type how many twenty dollar bills you want to deposit:");
            twenty = new Integer(input2);
            input3 = JOptionPane.showInputDialog(null, "Type how many ten dollar bills you want to deposit:");
            ten = new Integer(input3);
            input4 = JOptionPane.showInputDialog(null, "Type how many five dollar bills you want to deposit:");
            five = new Integer(input4);
        } else if (choice == 2) {
            input1 = JOptionPane.showInputDialog(null, "Type an amount of euros you want to deposit:");
            fifty = new Integer(input1);
            int[] data = employee.exchange(fifty);
            five = data[0]; ten = data[1]; twenty = data[2]; fifty = data[3];
        }

        String input5 = JOptionPane.showInputDialog(null, user.Summary() + "\n" +
                "Type a valid account id of the account you want to deposit to");
        int id = Integer.parseInt(input5);

        Account account = user.accounts.get(0);
        boolean found = false;
        for (int i=0; i < user.accounts.size(); i++) {
            if (id == user.accounts.get(i).id) {
                account = user.accounts.get(i);
                found = true;
            }
        }
        if (found) {
            account.deposit(user, machine, five, ten, twenty, fifty);
        } else {
            JOptionPane.showMessageDialog(null, "You typed an invalid id. Try again.");
        }
    }

    void createTransactionInterface(Controller controller, User user, Machine machine, Employee employee, Manager manager) {
        /*
        This method is responsible user to make which type of transaction. Based on the user input it make the transaction.
         */
        String input = JOptionPane.showInputDialog(null, "Type \n" + "     1: To transfer money between accounts \n  " +
                "   2: Withdraw money from an account \n     3: pay bills \n     " +
                "4: deposit money into an account \n     5: To Transfer money to another users account \n    " +
                "6: to undo the most recent ViewController.Transaction on an account \n" + "7: merge Chequing Accounts \n "+
                "8: to pay off credit card balance");
            //transfer money between your accounts
        if ("1".equals(input)) {
            transactionTransferBetween(controller, user);
            // Withdraw money from an account
        } else if ("2".equals(input)) {
            if (user.accounts.size() == 0) {
                JOptionPane.showMessageDialog(null, "you have no accounts");
            } else {
                int make = transactionInterfaceHelper(user);
                String between3 = JOptionPane.showInputDialog(null, "Type an appropriate value to withdraw");
                controller.withdrawControl(machine, between3, make, user);
            }
            //pay bills
        } else if ("3".equals(input)) {
            String between = JOptionPane.showInputDialog(null, user.Summary() + "Type the account number" +
                    "for which you want to transfer money from (1, 2, ...) (Note this cannot be done for CreditCard accounts)");
            int thing = new Integer(between);
            String between2 = JOptionPane.showInputDialog(null, "Type an appropriate value to pay (int)");
            controller.billControl(thing, between2, user);
            //deposit money into an account
        } else if ("4".equals(input)) {transactionDepositMoney(user, machine, employee);
            //To Transfer money to another users account
        } else if ("5".equals(input)) {transactionInterfaceHelper4(user);
            //to undo the most recent ViewController.Transaction on an account
        } else if ("6".equals(input)) {
            String between = JOptionPane.showInputDialog(null, user.Summary() + "Type the account number" +
                    "for which you want to undo the most recent transaction");
            manager.undoTransactionControl(user, between);
        } else if ("7".equals(input)) {controller.merge(user);
            // to pay off credit card balance
        } else if ("8".equals(input)) {transactionTransferBetween2(controller, user);}
    }
}
