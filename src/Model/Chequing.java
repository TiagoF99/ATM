package Model;
import javax.swing.*;

public class Chequing extends Asset {
    /*
    It is a type of account that don't have any interest. Among all the account one can be set as primary default account.
         */

    private boolean primary = false;

    public Chequing() {}

    /*
    * Sets the primary variable for encapsulation
    * sets the variable and then updates it in the file
     */
    public void setPrimary(boolean value){
    /*
    sets the account to primary
     */
        String old;
        if (primary) {old = "true";}
        else {old = "false";}

        this.primary = value;

        String update;
        if (value) {update = "true";}
        else {update = "false";}

        String Users = getUsers(this.id);
        write("phase2/info/Accounts.txt", Users + " Chequing " + balance + " "+id+ " "+old+" " +
                recentTransaction, Users + " Chequing " + balance + " "+id+ " "+update+" " + recentTransaction );
    }

    public boolean getPrimary() {
         /*
    sets primary account
     */
        return this.primary;
    }

    @Override
    public void recentTransaction(User user, int change) {
     /*

     */
        String old = recentTransaction;
        if (change >= 0) {this.recentTransaction = this.id + "," + "+" + "," + change;}
        else {change = Math.abs(change);this.recentTransaction = this.id + "," + "-" + "," + change;}

        String Users = getUsers(this.id);
        if (primary) {
            write("phase2/info/Accounts.txt", Users + " Chequing " + balance + " "+id+ " true " + old,
                    Users + " Chequing " + balance + " "+id+ " true " + this.recentTransaction);
        } else {
            write("phase2/info/Accounts.txt", Users + " Chequing " + balance + " "+id+ " false " + old,
                    Users + " Chequing " + balance + " "+id+ " false " + this.recentTransaction);}
    }

    @Override
    public void changeBalanceInFile(User user, int old, int newBalance,int id) {
        /*

         */
        String Users = getUsers(this.id);
        if (primary) {
            write("phase2/info/Accounts.txt", Users + " Chequing " + old + " " + id + " true " + recentTransaction,
                    Users + " Chequing " + newBalance + " " + id + " true " + recentTransaction);
        } else {
            write("phase2/info/Accounts.txt", Users + " Chequing " + old + " "+id+ " false " + recentTransaction,
                    Users + " Chequing " + newBalance + " "+id+ " false " + recentTransaction);}
    }

    @Override
    public void withdraw(User user, Machine machine, int amount) {
        /*
        If balence - amount is more than -100 and machine and machine have enough funds then balance is changes.
        Otherwise shows not enough funds in the accounts
         */
        if (machine.allocate(-amount) && balance - amount >= -100) {
            changeBalance(user, -amount);
        } else {JOptionPane.showMessageDialog(null, "Not enough funds.");}
    }
}


