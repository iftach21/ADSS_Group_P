package DomainTest;

import Domain.Enums.WindowType;
import Domain.Employee.Workers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WorkersTest {

    @Test
    public void testAddingProf(){
        Workers w = new Workers(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234);
        w.addprof(0);
        //making sure added
        Assertions.assertTrue(w.caniworkatprofindx(0));

        //checking if he cant do something he cant.
        Assertions.assertFalse(w.caniworkatprofindx(1));

        //removing
        w.removePro(0);

        Assertions.assertFalse(w.caniworkatprofindx(0));
    }

    @Test
    void testRemovingProf(){
        Workers w = new Workers(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234);
        w.addprof(0);
        w.removePro(0);
        Assertions.assertFalse(w.caniworkatprofindx(0));
    }


    @Test
    public void testAddingAndRemovingWindows(){
        Workers w = new Workers(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234);

        //making sure he cant
        Assertions.assertFalse(w.canIworkat(WindowType.night1));

        //adding
        w.addwindow(WindowType.night1);
        //making sure he can
        Assertions.assertTrue(w.canIworkat(WindowType.night1));

        //removing
        w.removewindow(WindowType.night1);
        Assertions.assertFalse(w.canIworkat(WindowType.night1));
    }

}