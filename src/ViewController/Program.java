package ViewController;
import Model.*;
import javax.swing.JOptionPane;

/*
Starting program class that dictates initial login's, setups, inputs, etc.
 */
class Program extends Writer {

    /*
    Helper for main method, this method displays the information user asks for or makes a transaction, or does whatever user says.
    */
    private static void helper(int value, Controller controller, User user, Manager manager, Machine machine, Employee employee) {
        if (value == 7) {
            JOptionPane.showMessageDialog(null, "" +
                    "1 CAD = .75 USD \n 1 CAD = .66 EURO \n 1 CAD = 0.00019 BITCOIN \n 1 CAD = 5.02 YAUN" +
                    "\n 1 CAD = 82.67 YEN \n 1 CAD = 2.90 REAL");
        } else if (value == 9){
            for (int i = 0; i < user.accounts.size(); i++) {
                if (user.accounts.get(i) instanceof SuperSavings) {
                    ((SuperSavings) user.accounts.get(i)).updateAccount(user);
                }
            }
        } else if (!(value == 2)) {
            controller.main(controller, user, manager, machine, value, employee);
        } else {
            String between = JOptionPane.showInputDialog(null, user.Summary() + "Type the account number" +
                    "for which you want to your most recent transaction from");
            int number = new Integer(between);
            while (!(number <= user.accounts.size() && number >= 0)) {
                between = JOptionPane.showInputDialog(null, "You typed an invalid number. \n" + user.Summary() + "Type the account number" +
                        "for which you want your most recent transaction from");
                number = new Integer(between);
            }
            controller.recentTransaction(user, number);
        }
    }

    /*
    creating objects of all the objects of classes.
    displays all the options after logging in.
    for incorrect input display the correct possible inputs.
    If person says no to he don't want to to make another transaction then programme quits.
    If a user unable to login catch the error.
    */
    public static void main (String[] args) {

        Manager manager = new Manager();
        Machine machine = new Machine();
        User user = new User();
        Controller controller = new Controller();
        Login log = new Login();
        Employee employee = new Employee();

        try {
            //user login
            try {log.login(user, manager);} catch (java.lang.NullPointerException e) {
                System.out.println("You quit the program");
            }

            boolean not_done = true;
            while (not_done) {
                String input = JOptionPane.showInputDialog(null, "You typed an invalid number. \n Type \n" + "     1 for: Summary of your account " +
                        "balances \n     2 for: the most recent transaction on any account \n     3 for: your net total \n     " +
                        "4 for: to change your password \n     5 for: To do an account transaction \n     6 for: To create a new account      \n " +
                        "7 for: To get information on some of the latest currencies \n       8 for: to add another " +
                        "user to an account you own. \n 9 for: DEMO SUPER SAVINGS");
                int value = new Integer(input);
                while (!(value <= 9 && value >= 1)) {

                    input = JOptionPane.showInputDialog(null, "You typed an invalid number. \n Type \n" + "     1 for: Summary of your account " +
                            "balances \n     2 for: the most recent transaction on any account \n     3 for: your net total \n     " +
                            "4 for: to change your password \n     5 for: To do an account transaction \n     6 for: To create a new account      \n " +
                            "7 for: To get information on some of the latest currencies \n       8 for: to add another " +
                            "user to an account you own. \n 9 for: DEMO SUPER SAVINGS");
                    value = new Integer(input);
                }
                helper(value, controller, user, manager, machine, employee);
                //check if user wants to continue or quit the program after a transaction
                String cont = JOptionPane.showInputDialog(null, "Would you like to do another Transaction? (yes or no)");
                while (!cont.equals("yes") && !cont.equals("no")) {
                    cont = JOptionPane.showInputDialog(null, "invalid input. Would you like to do another Transaction? (yes or no)");
                }
                if (cont.equals("no")) {not_done = false;}
            }
        } catch (Exception e) {JOptionPane.showMessageDialog(null, "You Quit the program. Have a good day!");}
    }
}

