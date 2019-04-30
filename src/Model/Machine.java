package Model;
import java.io.*;
public class Machine extends Model.Writer {
    /*
        This class contains the information about the number of bill it has.
        machine when created would read the bill.txt so that it can tell how many of the bills it has.
        */
    // machine has these amounts of each bill
    private int fifty;
    private int twenty;
    private int ten;
    private int five;

    //constructor
    /*
    Reads the bills file and sets the machines bills to amount it had recorded in the file
     */
    public Machine(){
        /*
     This method is always called so that it sets up number of bills recorded in bills.txt
     */
        //reads through bills.txt and sets the variables of this machine to the amount of each bill recorded
        //specific to machine class!
        try {
            File file = new File("phase2/info/bills.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int count = 0;
            while ((st = br.readLine()) != null) {
                if (count == 0) {five = new Integer(st);count++;
                } else if (count == 1) {ten = new Integer(st);count++;
                } else if (count == 2) {twenty = new Integer(st);count++;
                } else {fifty = new Integer(st);count++;}}
        } catch (java.io.IOException e) {System.out.println("Problem reading file.");}
    }

    private void restockMachine() {
        // set each one to its initial amount
        fifty = 50;twenty = 50;ten = 50;five = 50;
        machineWrite("phase2/info/bills.txt", "50", "50", "50", "50");
    }

    private void alert() {

        if (five < 4 || ten < 2 || twenty < 1 || fifty < 1) {
            append("phase2/info/alerts.txt", "Denomination fell below 20 for one of the bills. " +
                    "Restock the whole machine" + "\n");
            restockMachine();}
    }

    private void update(int num_five, int num_ten, int num_twenty, int num_fifty){
        /*
     Adds the new bills to the existing number. this method is used whenever machine restocked of person withdraws money or puts in money into the machine.
     */
        fifty += num_fifty;twenty += num_twenty;ten += num_ten;five += num_five;
        machineWrite("phase2/info/bills.txt", Integer.toString(five), Integer.toString(ten),
                Integer.toString(twenty), Integer.toString(fifty) );
        alert();
    }

    void receive_money(int num_five, int num_ten, int num_twenty, int num_fifty){
         /*
     calls the update method whenever the machine receives money from a user
     */
        update(num_five, num_ten, num_twenty, num_fifty);
    }

    private boolean check_enough(int num_five, int num_ten, int num_twenty, int num_fifty){
        /*
    checks if a user entered number of bills are even there in the machine or not.
    */
        return five < num_five || ten < num_ten || twenty < num_twenty || fifty < num_fifty;
    }

    boolean allocate(int number)
                        /*
     If number of bills in the machine is grater than the bills needed then the machine is updated i.e. transaction is succesfull and the return value is True.
     If number of bills in the machine is less than the bills needed then machine is not updated and the transaction is not succesfull and returns a value False.
     When transaction is not succesfull then bill number becoums the maximum amount of bills preasent in the machine.
     This is done to inform the user how much user can withdraw from the machine.
     */
    {
        int num_fifty; int num_twenty = 0; int num_ten = 0; int num_five = 0;
        num_fifty = (number / 50);
        while(check_enough(num_fifty, num_twenty, num_ten, num_five)){num_fifty -= 1;}
        number -= 50 * num_fifty; num_twenty = (number / 20);
        while(check_enough(num_fifty, num_twenty, num_ten, num_five)){num_twenty -= 1;}
        number -= 20 * num_twenty; num_ten = (number / 10);
        while(check_enough(num_fifty, num_twenty, num_ten, num_five)){num_ten -= 1;}
        number -= 10 * num_ten;num_five = (number / 5);
        while(check_enough(num_fifty, num_twenty, num_ten, num_five)){num_five -= 1;}

        if (number == 0){
            //This means the machine can give what user wants.
            update(num_five, num_ten, num_twenty, num_fifty); return true;
        }
        return false;
    }
}
