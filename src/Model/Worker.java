package Model;

abstract class Worker extends Writer {
     /*
     this is an abstract class which has child class as the Bank Manager and Employee. Both are implimented seperately.
     */

    public void setPassword(User user, String password, String old) {
        user.password = password;
        //modifies login file to include new password for the user with the given username
        write("phase2/info/login.txt", user.username + " " + old, user.username + " " + password);
    }

}
