package DomainTest;

import Domain.Employee.Shift;
import Domain.Employee.Workers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTest {
    @Test
    public void TestInsertToShift() {
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        Shift s = new Shift("22.2.22");
        //checking if a non existing is in shift
        s.insertToShift(w, 0);
        Assertions.assertFalse(s.checkIfWorkerInShift(12));
        Assertions.assertTrue(s.checkIfWorkerInShift(1));
    }
    @Test
    public void TestRemoveToShift() {
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        Shift s = new Shift("22.2.22");
        //checking if a non existing is in shift
        s.insertToShift(w, 0);
        s.removalWorker(w);
        Assertions.assertFalse(s.checkIfWorkerInShift(1));
    }

    @Test
    public void TestPrintShift() {
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        Shift s = new Shift("22.2.22");
        s.insertToShift(w, 0);
        StringBuilder print= s.printShift();
        System.out.println(print);
        StringBuilder o=new StringBuilder();
        o.append("name: iftach id: 1");
        assertEquals(print.toString(),o.toString());

    }
}