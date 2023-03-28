public class Main {
    public static void main(String[] args) {
        Interface in = new Interface();
        in.logIn();
    }
    public static void initiation(Interface in){
        Workers work1=new Workers(1,"work1","q","e",1,586012012,"o",11);
        Workers work2=new Workers(2,"work2","q","e",1,586012012,"o",11);
        Workers work3=new Workers(3,"work3","q","e",1,586012012,"o",11);
        Workers work4=new Workers(4,"work4","q","e",1,586012012,"o",11);
        Workers work5=new Workers(5,"work5","q","e",1,586012012,"o",11);
        Workers work6=new Workers(6,"work6","q","e",1,586012012,"o",11);
        in.createnewweeklyshift(1,1,1);
        in.createnewweeklyshift(1,1,1);
        in.addemployee(work1);
        in.addemployee(work2);
        in.addemployee(work3);
        in.addemployee(work4);
        in.addemployee(work5);
        in.addemployee(work6);
        in.addnewproforemployee(1,0);
        in.addnewproforemployee(1,1);
        in.addnewproforemployee(1,2);
        in.addnewproforemployee(1,3);
        in.addnewproforemployee(1,4);
        in.addnewproforemployee(1,5);
        in.addnewproforemployee(1,6);
        in.addnewproforemployee(2,0);
        in.addnewproforemployee(2,1);
        in.addnewproforemployee(2,2);
        in.addnewproforemployee(3,3);
        in.addnewproforemployee(4,4);
        in.addnewproforemployee(5,5);
        in.addnewproforemployee(6,6);





    }
}

