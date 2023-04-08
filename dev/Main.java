import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean connectionActive = true;

        while(connectionActive){
        try {
            System.out.println("are you employee or Manager?");
            System.out.println("1 = employee");
            System.out.println("2 = Manager ");

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            int ans = myObj.nextInt();
            BossInterface in = new BossInterface();
            WorkerInterface win = new WorkerInterface();
            initiation();
            myObj.nextLine();
            //worker log in
            if (ans == 1) {

                win.logInWorker();
            }

            //manager login
            if(ans==2){in.logIn();}

            else{connectionActive=false;}
        }
        catch(ExitExeption idnt){
            continue;
        }
        catch (Exception exception){
            System.out.println("Oh no! something went wrong! error 404.\n please try again and contact the IT-team");
        }


        }
    }

    public static void initiation(){
        WeeklyShiftAndWorkersManager controller = WeeklyShiftAndWorkersManager.getInstance();
        //creation of 6 workers:
        Workers work1=new Workers(1,"work1","q","e",1,586012012,"o",11);
        Workers work2=new Workers(2,"work2","q","e",1,586012012,"o",11);
        Workers work3=new Workers(3,"work3","q","e",1,586012012,"o",11);
        Workers work4=new Workers(4,"work4","q","e",1,586012012,"o",11);
        Workers work5=new Workers(5,"work5","q","e",1,586012012,"o",11);
        Workers work6=new Workers(6,"work6","q","e",1,586012012,"o",11);

        //creating 2 weeklyshift in the system
        controller.createnewweeklyshift(1,1,1);
        controller.createnewweeklyshift(2,1,1);

        //adding the workers into the system
        controller.addemployee(work1);
        controller.addemployee(work2);
        controller.addemployee(work3);
        controller.addemployee(work4);
        controller.addemployee(work5);
        controller.addemployee(work6);

        //adding prof for the workers in the system
        //adding to worker 1
        controller.addnewproforemployee(1,0);
        controller.addnewproforemployee(1,1);
        controller.addnewproforemployee(1,2);
        controller.addnewproforemployee(1,3);
        controller.addnewproforemployee(1,4);
        controller.addnewproforemployee(1,5);
        controller.addnewproforemployee(1,6);
        //adding to worker 2
        controller.addnewproforemployee(2,0);
        controller.addnewproforemployee(2,1);
        controller.addnewproforemployee(2,2);
        //adding to worker 3
        controller.addnewproforemployee(3,3);
        //adding to worker 4
        controller.addnewproforemployee(4,4);
        //adding to worker 5
        controller.addnewproforemployee(5,5);
        //adding to worker 6
        controller.addnewproforemployee(6,6);

        //adding avialble for empl:

        //addin for work1
        controller.addavilableforemployee(1,WindowType.night1);
        controller.addavilableforemployee(1,WindowType.day5);

        //woerker2
        controller.addavilableforemployee(2,WindowType.night1);
        controller.addavilableforemployee(2,WindowType.day5);

        //worker3
        controller.addavilableforemployee(3,WindowType.night1);


        //worker5
        controller.addavilableforemployee(5,WindowType.night1);

        //creatingnew weeklyshift:
        controller.createnewweeklyshift(1,1,1);

        //adding emp to it:
        //for night1:
        controller.addtoexistingweeklyshift(1,1,1,WindowType.night1,work1,0);
        controller.addtoexistingweeklyshift(1,1,1,WindowType.night1,work2,1);
        controller.addtoexistingweeklyshift(1,1,1,WindowType.night1,work5,5);
        controller.addtoexistingweeklyshift(1,1,1,WindowType.night1,work3,3);

        //for day5:
        controller.addtoexistingweeklyshift(1,1,1,WindowType.day5,work1,0);
        controller.addtoexistingweeklyshift(1,1,1,WindowType.day5,work2,2);



    }
}



