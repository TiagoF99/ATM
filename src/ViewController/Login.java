package ViewController;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import Model.*;

/*
this class is for user and employee login's as well as username and password front end and input
 */
class Login {

    /*
     *Checks if you have an account with the machine
     *If yes then it will prompt you to login and will check that your login info is in login.txt
     *If no then it will prompt you to create a unique username and a password and add it to login.txt
     */
    private void loginHelper(User user, File location) {
        /*
        it is a helper method for userLogin
        Method is created to be used to ask for username and password from  the user is logging in.
        Compares username and password from the files and if found then would input the username and password into user as a instance variables.
        user would log into the account if the username and password is correct
        If username and password is not correct then user would have to enter both again or user could quit.
         */
        String username = JOptionPane.showInputDialog(null,
                "Username:");
        String password = JOptionPane.showInputDialog(null,
                "Password:");
        boolean not_found = true;
        while (not_found) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(location));
                String st;
                boolean found2 = false;
                while ((st = br.readLine()) != null) {
                    String[] data = st.split(" ");
                    if (data[0].equals(username) && data[1].equals(password)) {
                        found2 = true;
                    }
                }
                if (found2) {
                    not_found = false;
                } else {
                    username = JOptionPane.showInputDialog(null,
                            "Your username or password is incorrect. Username:");
                    password = JOptionPane.showInputDialog(null,
                            "Password: (type 'QUIT' to quit)");
                    if (password.equals("QUIT")) {
                        System.exit(0);
                    }
                }
            } catch (java.io.IOException e) {
                System.out.println("Something wrong with reading the file");
            }
        }
        //user already has a username and password in the system so we just have to set it here
        user.username = username;
        user.password = password;
        user.AccountSetup();
    }

    private String newAccountUsername(File location) {
        /*
        When this method is called then user have to set the username of a new account.
        Files would be read and the username in the file would be updated with the new one.
        Finally the method returns the username so that it could be used to set it to the user.
         */
        String username = JOptionPane.showInputDialog(null,
                "Set your username here: (do not set to 'QUIT')");

        while (username.equals("QUIT")) {
            username = JOptionPane.showInputDialog(null,
                    "Set your username here: (do not set to 'QUIT')");
        }
        boolean not_done = true;
        while (not_done) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(location));
                String st;
                boolean found = false;
                while ((st = br.readLine()) != null) {
                    String[] data = st.split(" ");
                    if (data[0].equals(username)) {
                        username = JOptionPane.showInputDialog(null, "This username is taken. Set your username here:");
                        found = true;
                    }
                }
                if (!found) {
                    not_done = false;
                }
            } catch (java.io.IOException e) {System.out.println("Something wrong with reading the file");}
        }
        return username;
    }

    private boolean checkNewEmpolyeeStatus(File location, Manager manager) {
        String username = JOptionPane.showInputDialog(null, "Username:");
        String password = JOptionPane.showInputDialog(null,
                "Your Temporary Password(you can find it in your work offer):");

        while (password.equals("000"))
        {
            username = JOptionPane.showInputDialog(null,
                    "Your name is incorrect or your temporary password is incorrect. Please retry, Name:");
            password = JOptionPane.showInputDialog(null,
                    "Temporary Password: (type 'QUIT' to quit)");
            if (password.equals("QUIT")) {
                System.exit(0);
            }
        }
        boolean not_found = true;
        while (not_found) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(location));
                String st;
                boolean found2 = false;
                while ((st = br.readLine()) != null) {
                    String[] data = st.split(" ");
                    if (data[0].equals(username) && data[1].equals(password)) {
                        found2 = true;
                        manager.write("phase2/info/newEmpolyee.txt", username + " " + password, username + " " + "000"); //000 means the new empolyee has registered.
                    }
                }
                if (found2) {not_found = false;} else {
                    username = JOptionPane.showInputDialog(null,
                            "Your name is incorrect or your temporary password is incorrect. Please retry, Name:");
                    password = JOptionPane.showInputDialog(null,
                            "Temporary Password: (type 'QUIT' to quit)");
                    if (password.equals("QUIT")) {
                        System.exit(0);
                    }
                }
            } catch (java.io.IOException e) {System.out.println("Something wrong with reading the file");}
        }
        //user already has a username and password in the system so we just have to set it here
        return false;
    }

    void login(User user, Manager manager) {
        /*
        initial login screen for user or employee
         */
        File file = new File("phase2/info/login.txt");
        File newEmployeeFile = new File("phase2/info/newEmpolyee.txt");
        String newEmployee = JOptionPane.showInputDialog(null,
                "Are you a new employee at TofU bank? (Type 'yes' or 'no')");
        while (!newEmployee.equals("yes") && !newEmployee.equals("no")) {
            newEmployee = JOptionPane.showInputDialog(null,
                    "Invalid input. Are you a new employee at TofU bank? (Type 'yes' or 'no')");
        }
        if (newEmployee.equals("yes") && !checkNewEmpolyeeStatus(newEmployeeFile, manager)) {
            String username = newAccountUsername(file);
            String password = JOptionPane.showInputDialog(null, "Password: (Do not set to 'QUIT')");
            while (password.equals("QUIT")) {
                password = JOptionPane.showInputDialog(null, "Password: (Do not set to 'QUIT')");
            }
            manager.createLogin(user, username, password);
            user.AccountSetup();
        } else {
            userLogin(user, file, manager);
        }
    }

    private void userLogin(User user, File file, Manager manager) {
        /*
        If the user says that he has a account with with the bank then loginHelper is called.
        This results in user to login to the account.
        otherwise new user account can be created.
        Or the person quits.
         */
        String validate = JOptionPane.showInputDialog(null, "Do you have an account with us? (yes or no)");
        if (validate.equals("yes")) {
            loginHelper(user, file);
        } else {
            String username = newAccountUsername(file);
            String password = JOptionPane.showInputDialog(null, "Password: (Do not set to 'QUIT')");
            while (password.equals("QUIT")) {
                password = JOptionPane.showInputDialog(null, "Password: (Do not set to 'QUIT')");
            }
            manager.createLogin(user, username, password);
            user.AccountSetup();
        }
    }
}
