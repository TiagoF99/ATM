package Model;

import javax.swing.*;

public class Asset extends Account {

    public void transferOut(User user, int transferInNum) {
        if (balance >= transferInNum) {changeBalance(user, -transferInNum);}
        else {JOptionPane.showMessageDialog(null, "This account does not have enough funds");}}

}
