package ViewController;
import Model.*;

import javax.swing.*;
import java.util.Calendar;

/*
calls the main view helper classes
 */
class Controller extends Writer {
    /*
    Object of controller is created in Program.
    Has Transaction, CreateAccount and AddUser as instance variable this results in cunstroctor of all the classes to be called.

     */
    private Transaction transaction;
    private CreateAccountInterface createAccount;
    private AddUser addUser;

    Controller() {
        transaction = new Transaction();
        createAccount = new CreateAccountInterface();
        addUser = new AddUser();
    }

    /*
    Takes in input of initial option screen and calls appropriate methods and classes
     */
    void main(Controller controller, User user, Manager manager, Machine machine, int value, Employee employee) {
        /*
     Based on the input of the user the controller prints out different messages.
     If the user wants to further make another action then Program calls controller again.
     */
        if (value == 1) {
            String summary = user.Summary();
            JOptionPane.showMessageDialog(null, summary);
        } else if (value == 3) {
            JOptionPane.showMessageDialog(null, user.netTotal());
        } else if (value == 4) {
            String password = JOptionPane.showInputDialog(null, "Type new password: (do not set to 'QUIT') ");
            while (password.equals("QUIT")) {
                password = JOptionPane.showInputDialog(null, "INVALID INPUT. \n " +
                        "Type new password: (do not set to 'QUIT') ");
            }
            manager.setPassword(user, password, user.password);
        } else if (value == 5) {
            transaction.createTransactionInterface(controller, user, machine, employee, manager);
        } else if (value == 6) {
            createAccount.CreateAccountFront(user);
        } else if (value == 8) {
            addUser.main(user);
        }

        //check what month it is and updates Savings account if in new month
        if (Calendar.getInstance().get(Calendar.MONTH) != Savings.month) {
            for (int i = 0; i < user.accounts.size(); i++) {
                if (user.accounts.get(i) instanceof Savings) {
                    ((Savings) user.accounts.get(i)).updateAccount();
                }
            }
            Savings.setMonth();
        }
    }

    void transferBetween(String between, String between2, String between3, User user) {

        /*
        helps transfer between 2 accounts.
     */
        int make = new Integer(between);
        int make2 = new Integer(between2);
        int amount = new Integer(between3);

        if (user.accounts.size() < 2) {
            JOptionPane.showMessageDialog(null, "You do not have enough accounts for this transaction");
        } else {
            Account one = user.accounts.get(0);
            Account two = user.accounts.get(1);
            int count = 0;
            for (int i  = 0; i < user.accounts.size(); i ++) {
                Integer id = user.accounts.get(i).id;
                if (id.equals(make)) {
                    one = user.accounts.get(i);
                    count++;
                } else if (id.equals(make2)) {
                    two = user.accounts.get(i);
                    count++;
                }
            }
            if (!(count == 2)) {
                JOptionPane.showMessageDialog(null, "You typed invalid id's for the account");
            } else {
                if (one instanceof CreditCard) {
                    JOptionPane.showMessageDialog(null, "You cannot transfer money out of a CreditCard account");
                }else if (one instanceof LineOfCredit && -2000 > -amount + one.balance) {
                    JOptionPane.showMessageDialog(null, "youve reached your limit");
                } else {
                    one.transferOut(user, amount);
                    two.transferIn(user, amount);
                }
            }
        }
    }

    void transferBetween2(String from, String to, int amount, User user) {

        // For paying off credit card balance
        int id_from = new Integer(from);
        int id_to = new Integer(to);

        // Find account "from" and "to" using id's
        Account asset = user.accounts.get(0);
        Account credit = user.accounts.get(1);
        for (int i  = 0; i < user.accounts.size(); i ++) {
            Integer id = user.accounts.get(i).id;
            if (id.equals(id_from)) {
                asset = user.accounts.get(i);
            } else if (id.equals(id_to)) {
                credit = user.accounts.get(i);
            }
        }

        if (asset.balance >= -credit.balance) {
            asset.transferOut(user, -amount);
            credit.transferIn(user, -amount);
        } else {
            int diff = asset.balance;
            credit.transferIn(user, diff);
            asset.transferOut(user, diff);
        }
    }


    void withdrawControl(Machine machine, String between3, int make, User user) {
        /*
        Sets the variable account to user.account and then calls withdraw on the account
        Transaction calls the method and controller calls transaction in th constructor
        transactionTransferBetween method is called when user inputs 5 which leads to transaction being made.
         */
        int amount = new Integer(between3);
        Account account = user.accounts.get(make);
        account.withdraw(user, machine, amount);
    }

    void billControl(int thing, String between, User user) {
        /*
        It control the bills in the machine
         */

        Account account = user.accounts.get(0);
        int count = 0;
        for (int i = 0; i < user.accounts.size(); i++) {
            if (user.accounts.get(i).id == thing) {
                account = user.accounts.get(i);
                count++;
            }
        }
        if (count != 1) {
            JOptionPane.showMessageDialog(null, "You typed invalid id's for the account");
        } else {
            if (account instanceof CreditCard) {
                JOptionPane.showMessageDialog(null, "This transaction cannot be done from a credit card account");
            }else if (account instanceof LineOfCredit && -2000 > -Integer.parseInt(between) + account.balance) {
                JOptionPane.showMessageDialog(null, "Youve reached your limit");
            } else {
                int amount = new Integer(between);
                account.changeBalance(user, -amount);

                append("phase2/info/outgoing.txt", "You payed a bill worth: " + amount +
                        " from account with id: " + account.id + "\n");
            }
        }
    }

    void recentTransaction(User user, int accNumber) {
        /*
        Gives the last transaction made
         */

        Account account = user.accounts.get(0);
        int count = 0;
        for (int i = 0; i < user.accounts.size(); i++) {
            Integer ids = user.accounts.get(i).id;
            if (ids.equals(accNumber)) {
                account = user.accounts.get(i);
                count++;
            }
        }
        if (count != 1) {
            JOptionPane.showMessageDialog(null, "You typed invalid id's for the account");
        } else {
            JOptionPane.showMessageDialog(null, "Your most recent Transaction is (format: BalanceChange,+/-change,id): " + account.recentTransaction);
        }
    }

    void merge(User user) {
        /*
        merges 2 or more accounts to the primeary
         */
        int total = 0;

        for (int i = 0; i < user.accounts.size(); i++) {
            if (user.accounts.get(i) instanceof Chequing) {
                if (!((Chequing) user.accounts.get(i)).getPrimary()) {
                    total += user.accounts.get(i).balance;
                    String original = user.username + " Chequing " + user.accounts.get(i).balance
                            + " " + user.accounts.get(i).id + " false " + user.accounts.get(i).recentTransaction;
                    delete("phase2/info/Accounts.txt", original);
                }
            }
        }
        for (int i = 0; i < user.accounts.size(); i++) {
            if (user.accounts.get(i) instanceof Chequing) {
                if (((Chequing) user.accounts.get(i)).getPrimary()) {
                    user.accounts.get(i).changeBalance(user, total);
                }
                else {
                    user.accounts.remove(user.accounts.get(i));
                }
            }
        }
    }

}
