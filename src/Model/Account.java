package Model;
import javax.swing.*;

public abstract class Account extends Writer {
    /*
         User has multiple accounts, composition of classes used
         account class is parent class of all the account type classes
         Account has
         */
    public int balance;//just make everything public at this moment.
    public int id;
    public String recentTransaction = "none";

    /*
    This constructor is for the old accounts, which means these accounts already exist in the Model.Account.txt so when machine
    restarts, the constructor should be told the information, here balance.
    */
    Account()
    {
        this.balance = 0;
    }
    /*
        assigns the balance to 0 when any account is created.
         */
    public void withdraw(User user, Machine machine, int amount) {
        if (machine.allocate(-amount) && balance >= amount) {
            changeBalance(user, -amount);
        }
        else {JOptionPane.showMessageDialog(null, "Not enough funds.");}
    }

    public void changeBalance(User user, int balanceChange) {
        switch (this.getClass().toString()) {
            case "class Model.SuperSavings":
                changeBalanceInFile(balance, balance + balanceChange, id, "SuperSavings");
                balance += balanceChange;
                recentTransaction(balanceChange, "SuperSavings");
                break;
            case "class Model.LineOfCredit":
                changeBalanceInFile(balance, balance + balanceChange, id, "LineOfCredit");
                balance += balanceChange;
                recentTransaction(balanceChange, "LineOfCredit");
                break;
            case "class Model.Savings":
                changeBalanceInFile(balance, balance + balanceChange, id, "Savings");
                balance += balanceChange;
                recentTransaction(balanceChange, "Savings");
                break;
            case "class Model.Chequing":
                changeBalanceInFile(user, balance, balance + balanceChange, id);
                balance += balanceChange;
                recentTransaction(user, balanceChange);
                break;
            case "class Model.CreditCard":
                changeBalanceInFile(balance, balance + balanceChange, id, "CreditCard");
                balance += balanceChange;
                recentTransaction(balanceChange, "CreditCard");
                break;
        }
    }

    public void recentTransaction(User user, int balanceChange) {}

    public void changeBalanceInFile(User user, int old, int newBalance, int id) {}

    private void recentTransaction(int change, String type) {

        String old = recentTransaction;
        if (change >= 0) {
            this.recentTransaction = this.id + "," + "+" + "," + change;
        } else {
            change = Math.abs(change);
            this.recentTransaction = this.id + "," + "-" + "," + change;
        }
        String Users = getUsers(this.id);
        write("phase2/info/Accounts.txt", Users + " " + type + " " + balance + " "+id+" "+old,
                Users + " " + type + " " + balance + " "+id+" "+recentTransaction);
    }

    private void changeBalanceInFile(int old, int newBalance, int id, String type) {
        String Users = getUsers(this.id);
        write("phase2/info/Accounts.txt", Users + " " + type + " " + old + " " + id +" "+recentTransaction,
                Users + " " + type + " " + newBalance + " " + id+" "+recentTransaction);
    }

    public void transferIn(User user, int transferInNum)
    {
        changeBalance(user, transferInNum);
    }

    public void transferOut(User user, int transferInNum) {}

    public String toString() {return "Your balance is " + balance;}

    public void deposit(User user, Machine machine, int num_five, int num_ten, int num_twenty, int num_fifty){
        machine.receive_money(num_five, num_ten, num_twenty, num_fifty);
        changeBalance(user, num_fifty * 50  + num_twenty * 20 + num_ten * 10 + num_five * 5);
    }

}
