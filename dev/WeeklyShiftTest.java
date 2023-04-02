import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyShiftTest {


    @Test
    void howMuchShiftWorkerDid() {
        //adding worker and cheking if he is in
        WeeklyShift ws = new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        ws.addworkertoshift(w,WindowType.day1,0);

        assertEquals(ws.howMuchShiftWorkerDid(1),1);

        ws.addworkertoshift(w,WindowType.night3,0);

        assertEquals(ws.howMuchShiftWorkerDid(1),2);
    }

    @Test
    void addworkertoshift() {
        //adding worker and cheking if he is in
        WeeklyShift ws = new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        ws.addworkertoshift(w,WindowType.day1,0);

        assertTrue(ws.checkifworkallready(1,WindowType.day1));
        assertFalse(ws.checkifworkallready(1,WindowType.night1));

    }

    @Test
    void checkifworkallready() {
        //reguraly checking
        WeeklyShift ws = new WeeklyShift(1,1,1);
        assertFalse(ws.checkifworkallready(1,WindowType.night1));

    }
    @Test
    void changeWorker() {
        //changing a worker and checking
        //adding worker and cheking if he is in
        WeeklyShift ws = new WeeklyShift(1,1,1);
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        ws.addworkertoshift(w,WindowType.day1,0);

        Workers w2 = new Workers(2,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);

        ws.changeWorker(w,w2,0,WindowType.day1);
        assertTrue(ws.checkifworkallready(2,WindowType.day1));
        assertFalse(ws.checkifworkallready(1,WindowType.day1));
    }
}