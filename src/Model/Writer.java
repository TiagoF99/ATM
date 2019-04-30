package Model;
import javax.swing.*;
import java.io.*;

/*
   This class is parent class of all the classes in the program.
   This class has methods read and write files in order to change the content in the file.
   This is done so that any class can do these basic operations on the files.
    */
public class Writer {

    public void write(String location, String original, String replace) {
         /*
     This method replaces line in a file with a new line
     */
        try {
            BufferedReader file = new BufferedReader(new FileReader(location));
            String line;
            StringBuilder inputBuffer = new StringBuilder();
            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');}
            String inputStr = inputBuffer.toString();
            file.close();
            inputStr = inputStr.replace(original, replace);
            // write the new String with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(location);
            fileOut.write(inputStr.getBytes());
            fileOut.close();
        } catch (Exception e) {System.out.println("Problem reading file.");}}

    protected void delete(String location, String original) {
        /*
     this method delete any element in the file provided the element and the location of the file
     */
        try {
            BufferedReader file = new BufferedReader(new FileReader(location));
            String line;
            StringBuilder inputBuffer = new StringBuilder();

            while ((line = file.readLine()) != null) {
                if (!line.equals(original)) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');}}
            String inputStr = inputBuffer.toString();
            file.close();
            // write the new String with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(location);
            fileOut.write(inputStr.getBytes());
            fileOut.close();
        } catch (Exception e) {System.out.println("Problem reading file.");}}

    void machineWrite(String location, String line1, String line2, String line3, String line4) {
        /*
     this method is responsible for keeping track of restocking, withdraw and insert.
     It updates the bills files
     */
        try {
            //write to file the updated amounts of each bill
            File file = new File(location);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line1);
            bw.newLine();
            bw.write(line2);
            bw.newLine();
            bw.write(line3);
            bw.newLine();
            bw.write(line4);
            bw.newLine();

            bw.flush();
            bw.close();
        } catch (java.io.IOException e) {System.out.println("Something wrong with reading the file");}
    }

    protected void append(String location, String line) {
        /*
     This method is responsible for appending new lines to the file.
     */
        File file = new File(location);

        try {
            FileWriter fr = new FileWriter(file, true);
            fr.write(line);
            fr.close();
        } catch (java.io.IOException e) {System.out.println("Problem reading file.");}
    }

    protected String checkUser() {
        /*
     This method helps in identifying if user who another user is transferring money to already has an account or not.
     */
        String input1 = JOptionPane.showInputDialog(null, "Enter the username for the other user: ");

        boolean found = false;
        //checks if the user you want to send money to exists
        while (!found) {

            try {
                BufferedReader br = new BufferedReader(new FileReader("phase2/info/login.txt"));

                String st;
                while ((st = br.readLine()) != null) {
                    String[] data = st.split(" ");
                    if (data[0].equals(input1)) {found = true;}
                }
            } catch (IOException e) {System.out.println("Something wrong with reading the file");}
            if (!found) {
                input1 = JOptionPane.showInputDialog(null, "The username you entered does not exist." +
                        "Enter the username for the user you want to send money to (Type 'QUIT' to quit)");
                if (input1.equals("QUIT")) {
                    System.exit(0);
                }
            }
        }
        return input1;
    }

    protected String getUsers(int id) {
        /*
     finds and returns user Id from the files id.txt.
     */
        String Users = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("phase2/info/Accounts.txt"));

            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split(" ");
                if (Integer.parseInt(data[3]) == id) {
                    Users = data[0];
                }
            }
        } catch (IOException e) {System.out.println("Something wrong with reading the file");}
        return Users;
    }


}
