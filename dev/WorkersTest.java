import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkersTest {

    @Test
    public void testAddingAndRemovingProf(){
        Workers w = new Workers(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234);
        w.addprof(0);
        //making sure added
        assertTrue(w.caniworkatprofindx(0));

        //checking if he cant do something he cant.
        assertFalse(w.caniworkatprofindx(1));

        //removing
        w.removePro(0);

        assertFalse(w.caniworkatprofindx(0));
    }

    @Test
    public void testAddingAndRemovingWindows(){
        Workers w = new Workers(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234);

        //making sure he cant
        assertFalse(w.canIworkat(WindowType.night1));

        //adding
        w.addwindow(WindowType.night1);
        //making sure he can
        assertTrue(w.canIworkat(WindowType.night1));

        //removing
        w.removewindow(WindowType.night1);
        assertFalse(w.canIworkat(WindowType.night1));
    }

}