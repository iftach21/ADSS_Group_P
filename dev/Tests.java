class Tests {
    public Tests() {
        this.TestWorkers();
        this.TestShift();
        this.TestWeeklyShift();
        this.TestInterface();
    }

    //function that will test workers class
    public void TestWorkers(){
        System.out.println("=======================");
        System.out.println("    Test for Workers");
        System.out.println("=======================");
        Workers w = new Workers(1,"iftach","lotsofmoney",
                "23.2.23",90,12345,"student",1234);

        //--------------------
        //    test 1
        //-------------------
        //adding him to be shiftmanager:
        w.addprof(0);
        //checking if he got it:
        if(w.caniworkatprofindx(0)){
            System.out.println("Test 1 has passed!");
        }
        else{System.out.println("Test 1 has failed!");}

        //--------------------
        //    test 2
        //-------------------

        //cheking if he cant do something
        if(! w.caniworkatprofindx(1)){
            System.out.println("Test 2 has passed!");
        }
        else{System.out.println("Test 2 has failed!");}

        //--------------------
        //    test 3
        //-------------------
        //removing his shiftmanege and check again
        w.removePro(0);
        if(! w.caniworkatprofindx(0)){
            System.out.println("Test 3 has passed!");
        }
        else{System.out.println("Test 3 has failed!");}

        //--------------------
        //    test 4
        //--------------------
        //checking winodw he cant work at
        WindowType wt = WindowType.day1;
        if(! w.canIworkat(wt)){System.out.println("Test 4 has passed!");}
        else{System.out.println("Test 4 has failed!");}

        //--------------------
        //    test 5
        //--------------------
        //adding him and checking if he can work then
        w.addwindow(wt);
        if(w.canIworkat(wt)){System.out.println("Test 5 has passed!");}
        else{System.out.println("Test 5 has failed!");}
    }



    public void TestShift(){
        System.out.println("=======================");
        System.out.println("    Test for Shift");
        System.out.println("=======================");

        Workers w = new Workers(1,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234);
        Shift s = new Shift("22.2.22");


        //--------------------
        //    test 1
        //--------------------
        //checking if a non existing is in shift
        if(s.checkIfWorkerInShift(12)){System.out.println("Test 1 has failed!");}
        else{System.out.println("Test 1 has passed!");}

        //--------------------
        //    test 2
        //--------------------
        //adding some 1 and check if he really there
        s.insertToShift(w,0);
        if(s.checkIfWorkerInShift(1)){System.out.println("Test 2 has passed!");}
        else{System.out.println("Test 2 has failed!");}

        //--------------------
        //    test 3
        //--------------------

        //removing him and cheking again
        s.removalWorker(w);
        if(s.checkIfWorkerInShift(1)){System.out.println("Test 3 has failed!");}
        else{System.out.println("Test 3 has passed!");}
    }

    public void TestWeeklyShift(){
        //todo:complete
    }

    public void TestInterface(){
        //todo:complete
    }








}
