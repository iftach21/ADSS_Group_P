package DomainTest;

import Domain.Employee.WeeklyShift;
import Domain.Enums.WindowType;
import Domain.Employee.Workers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyShiftTest {


    @Test
    void howMuchShiftWorkerDid() {
        //adding worker and cheking if he is in
        WeeklyShift ws = new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        ws.addworkertoshift(w, WindowType.day1,0);

        Assertions.assertEquals(ws.howMuchShiftWorkerDid(1),1);

        ws.addworkertoshift(w, WindowType.night3,0);

        Assertions.assertEquals(ws.howMuchShiftWorkerDid(1),2);
    }

    @Test
    void addworkertoshift() {
        //adding worker and cheking if he is in
        WeeklyShift ws = new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        ws.addworkertoshift(w, WindowType.day1,0);

        Assertions.assertTrue(ws.checkifworkallready(1, WindowType.day1));
        Assertions.assertFalse(ws.checkifworkallready(1, WindowType.night1));

    }

    @Test
    void checkifworkallready() {
        //reguraly checking
        WeeklyShift ws = new WeeklyShift(1,1,1);
        Assertions.assertFalse(ws.checkifworkallready(1, WindowType.night1));

    }
    @Test
    void changeWorker() {
        //changing a worker and checking
        //adding worker and cheking if he is in
        WeeklyShift ws = new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        ws.addworkertoshift(w, WindowType.day1,0);

        Workers w2 = new Workers(2,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);

        ws.changeWorker(w,w2,0, WindowType.day1);
        Assertions.assertTrue(ws.checkifworkallready(2, WindowType.day1));
        Assertions.assertFalse(ws.checkifworkallready(1, WindowType.day1));
    }
    @Test
    void printWeeklyShift() {
        //adding worker and cheking if he is in
        WeeklyShift ws = new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        ws.addworkertoshift(w, WindowType.day1,0);
        ws.addworkertoshift(w, WindowType.night3,0);
        StringBuilder print= ws.printSpesific();
        StringBuilder o=new StringBuilder();
        o.append(" Day 1: name: iftach id: 1, Night 1:  Day 2: , Night 2:  Day 3: , Night 3: name: iftach id: 1 Day 4: , Night 4:  Day 5: , Night 5:  Day 6: , Night 6:  Day 7: , Night 7: ");
        assertEquals(print.toString(),o.toString());

    }
}