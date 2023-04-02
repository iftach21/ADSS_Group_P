import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTest {
    @Test
    public void TestInsertToShift() {
        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        Shift s = new Shift("22.2.22");
        //checking if a non existing is in shift
        s.insertToShift(w, 0);
        assertFalse(s.checkIfWorkerInShift(12));
    }
}