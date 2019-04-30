package Model;
public class Employee  extends Worker {
    /*
    employee calculates the number of euros in terms of CAD
    */


    public Employee() {super();}

    /*
    employee can take in an amount in euros and figure out how many bills in CAD to give
     */
    public int[] exchange(int number) {
        int[] data = new int[4];
        number =  (int) Math.round(number*.66);

        int many = number/50;
        number -= 50*many;
        int many2 = number/20;
        number -= 20*many2;
        int many3 = number/10;
        number -= 10*many3;
        int many4 = number/5;
        data[0] = many4;
        data[1] = many3;
        data[2] = many2;
        data[3] = many;
        return data;
    }





}
