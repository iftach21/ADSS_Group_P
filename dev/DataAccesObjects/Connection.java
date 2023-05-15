package DataAccesObjects;

import Domain.Employee.WeeklyShiftAndWorkersManager;
import Domain.Enums.TempLevel;
import Domain.Enums.WindowType;
import Domain.Enums.weightType;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static java.sql.Connection conn = null;
    private Connection(){}

    public static java.sql.Connection getConnectionToDatabase() throws SQLException {
        if(conn == null){
            conn = DriverManager.getConnection("jdbc:sqlite:Transfer_Employee.db");
        }
        return conn;
    }

    public static void CreateTables(){
        String sql = "create table Item_mock\n" +
                "(\n" +
                "    catalogNum TEXT\n" +
                "        primary key\n" +
                "        unique,\n" +
                "    name       TEXT,\n" +
                "    tempLevel  TEXT\n" +
                ");\n" +
                "\n" +
                "create table Shift\n" +
                "(\n" +
                "    shift_id         INT AUTO_INCREMENT\n" +
                "        primary key,\n" +
                "    date             DATE not null,\n" +
                "    shift_manager_id INT,\n" +
                "    log              TEXT,\n" +
                "    start_time       TIME,\n" +
                "    req_1            INT,\n" +
                "    req_2            INT,\n" +
                "    req_3            INT,\n" +
                "    req_4            INT,\n" +
                "    req_5            INT,\n" +
                "    req_6            INT,\n" +
                "    req_7            INT\n" +
                ");\n" +
                "\n" +
                "create table Site\n" +
                "(\n" +
                "    siteID       INTEGER\n" +
                "        primary key\n" +
                "        unique,\n" +
                "    siteName     TEXT,\n" +
                "    address      TEXT,\n" +
                "    phoneNumber  TEXT,\n" +
                "    contactName  TEXT,\n" +
                "    x_Coordinate REAL,\n" +
                "    y_Coordinate REAL\n" +
                ");\n" +
                "\n" +
                "create table Truck\n" +
                "(\n" +
                "    licenseNumber   INTEGER\n" +
                "        primary key\n" +
                "        unique,\n" +
                "    model           TEXT,\n" +
                "    netWeight       INTEGER,\n" +
                "    maxWeight       INTEGER,\n" +
                "    currentWeight   INTEGER,\n" +
                "    coolingCapacity TEXT\n" +
                ");\n" +
                "\n" +
                "create table Transfer\n" +
                "(\n" +
                "    transferId         INTEGER\n" +
                "        primary key\n" +
                "        unique,\n" +
                "    dateOfTransfer     TEXT,\n" +
                "    leavingTime        TEXT,\n" +
                "    arrivingDate       TEXT,\n" +
                "    arrivingTime       TEXT,\n" +
                "    truckLicenseNumber INTEGER\n" +
                "        references Truck,\n" +
                "    driverName         TEXT,\n" +
                "    sourceId           INTEGER\n" +
                "        references Site,\n" +
                "    weightInSource     INTEGER\n" +
                ");\n" +
                "\n" +
                "create table TransferDestinations\n" +
                "(\n" +
                "    transferId   INTEGER\n" +
                "        references Transfer,\n" +
                "    siteId       INTEGER\n" +
                "        references Site,\n" +
                "    weightInSite INTEGER,\n" +
                "    unique (transferId, siteId)\n" +
                ");\n" +
                "\n" +
                "create table TransferItems\n" +
                "(\n" +
                "    transferId INTEGER\n" +
                "        references Transfer,\n" +
                "    catalogNum TEXT\n" +
                "        references Item_mock,\n" +
                "    siteId     INTEGER\n" +
                "        references Site,\n" +
                "    quantity   INTEGER,\n" +
                "    unique (transferId, catalogNum, siteId)\n" +
                ");\n" +
                "\n" +
                "create table TransferTrucks\n" +
                "(\n" +
                "    licenseNumber INTEGER\n" +
                "        references Truck,\n" +
                "    transferId    INTEGER\n" +
                "        references Transfer,\n" +
                "    unique (licenseNumber, transferId)\n" +
                ");\n" +
                "\n" +
                "create table sqlite_master\n" +
                "(\n" +
                "    type     TEXT,\n" +
                "    name     TEXT,\n" +
                "    tbl_name TEXT,\n" +
                "    rootpage INT,\n" +
                "    sql      TEXT\n" +
                ");\n" +
                "\n" +
                "create table weekly_shift\n" +
                "(\n" +
                "    week_num      INT not null,\n" +
                "    year          INT not null,\n" +
                "    supernum      INT not null,\n" +
                "    day_shift_1   INT\n" +
                "        constraint day_shift_1\n" +
                "            references Shift,\n" +
                "    day_shift_2   INT\n" +
                "        constraint day_shift_2\n" +
                "            references Shift,\n" +
                "    day_shift_3   INT\n" +
                "        constraint day_shift_3\n" +
                "            references Shift,\n" +
                "    day_shift_4   INT\n" +
                "        constraint day_shift_4\n" +
                "            references Shift,\n" +
                "    day_shift_5   INT\n" +
                "        constraint day_shift_5\n" +
                "            references Shift,\n" +
                "    day_shift_6   INT\n" +
                "        constraint day_shift_6\n" +
                "            references Shift,\n" +
                "    day_shift_7   INT\n" +
                "        constraint day_shift_7\n" +
                "            references Shift,\n" +
                "    night_shift_1 INT\n" +
                "        constraint night_shift_1\n" +
                "            references Shift,\n" +
                "    night_shift_2 INT\n" +
                "        constraint night_shift_2\n" +
                "            references Shift,\n" +
                "    night_shift_3 INT\n" +
                "        constraint night_shift_3\n" +
                "            references Shift,\n" +
                "    night_shift_4 INT\n" +
                "        constraint night_shift_4\n" +
                "            references Shift,\n" +
                "    night_shift_5 INT\n" +
                "        constraint night_shift_5\n" +
                "            references Shift,\n" +
                "    night_shift_6 INT\n" +
                "        constraint night_shift_6\n" +
                "            references Shift,\n" +
                "    night_shift_7 INT\n" +
                "        constraint night_shift_7\n" +
                "            references Shift,\n" +
                "    primary key (week_num, year, supernum)\n" +
                ");\n" +
                "\n" +
                "create table workers\n" +
                "(\n" +
                "    id           INTEGER\n" +
                "        primary key,\n" +
                "    name         TEXT,\n" +
                "    contract     TEXT,\n" +
                "    start_date   TEXT,\n" +
                "    wage         INTEGER,\n" +
                "    phoneNUM     INTEGER,\n" +
                "    personalinfo TEXT,\n" +
                "    bankNum      INTEGER,\n" +
                "    pro0         BOOLEAN,\n" +
                "    pro1         BOOLEAN,\n" +
                "    pro2         BOOLEAN,\n" +
                "    pro3         BOOLEAN,\n" +
                "    pro4         BOOLEAN,\n" +
                "    pro5         BOOLEAN,\n" +
                "    pro6         BOOLEAN,\n" +
                "    weightType   TEXT,\n" +
                "    TempType     TEXT,\n" +
                "    day1         BOOLEAN,\n" +
                "    day2         BOOLEAN,\n" +
                "    day3         BOOLEAN,\n" +
                "    day4         BOOLEAN,\n" +
                "    day5         BOOLEAN,\n" +
                "    day6         BOOLEAN,\n" +
                "    day7         BOOLEAN,\n" +
                "    night1       BOOLEAN,\n" +
                "    night2       BOOLEAN,\n" +
                "    night3       BOOLEAN,\n" +
                "    night4       BOOLEAN,\n" +
                "    night5       BOOLEAN,\n" +
                "    night6       BOOLEAN,\n" +
                "    night7       BOOLEAN\n" +
                ");\n" +
                "\n" +
                "create table shift_worker_in_shift\n" +
                "(\n" +
                "    id          INT AUTO_INCREMENT\n" +
                "        primary key,\n" +
                "    shift_id    INT not null\n" +
                "        constraint fk_shift_worker\n" +
                "            references Shift,\n" +
                "    worker_id   INT not null\n" +
                "        constraint fk_worker_in_shift\n" +
                "            references workers,\n" +
                "    workers_pro INT not null,\n" +
                "    constraint uc_shift_worker\n" +
                "        unique (shift_id, worker_id, workers_pro)\n" +
                ");";

        try {
            var c =   getConnectionToDatabase();
            var stet = c.prepareStatement(sql);
            stet.execute();
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void DeleteALL(){
        try {
            var c =   getConnectionToDatabase();
            var stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] weekly_shift ;");
            stet.execute();
            stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] item_Mock;");
            stet.execute();
            stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] Shift;");
            stet.execute();
             stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] Site;");
            stet.execute();
            stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] Transfar;");
            stet.execute();
            stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] TransfarDestination;");
            stet.execute();
            stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] TransferItems;");
            stet.execute();
            stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] Truck;");
            stet.execute();
            stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] weekly_shift;");
            stet.execute();
            stet = c.prepareStatement("DROP TABLE [ IF EXISTS ] workers;");
            stet.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection DeleteRows(){
        try {
            var c =   getConnectionToDatabase();
            extracted(c, "weekly_shift");
            extracted(c, "shift_worker_in_shift");
            extracted(c, "item_Mock");
            extracted(c, "Shift");
            extracted(c, "Site");
            //extracted(c, "Transfer");
            //extracted(c, "TransferDestinations");
            //extracted(c, "TransferItems");
            //extracted(c, "Truck");
            extracted(c, "workers");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static void extracted(java.sql.Connection c, String name) throws SQLException {
        var stet = c.prepareStatement("delete\nfrom "+ name +"\nwhere 1=1; ;");
        stet.execute();
    }

    public void databaseReboot() throws SQLException {
        WeeklyShiftAndWorkersManager wscontroller = WeeklyShiftAndWorkersManager.getInstance();

        //create 4 drivers :

        wscontroller.addNewDriver(111,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234, TempLevel.cold, weightType.lightWeight);

        wscontroller.addNewDriver(222,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.mediumWeight);

        wscontroller.addNewDriver(333,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.frozen,weightType.heavyWeight);

        wscontroller.addNewDriver(444,"iftach","lotsofmoney","23.2.23",90,12345,"student",1234,TempLevel.regular,weightType.lightWeight);

        wscontroller.addemployee(555,"iftach","lotsofmoney", "23.2.23",90,12345,"student",1234);

        wscontroller.addemployee(666,"iftach","lotsofmoney", "23.2.23",90,12345,"student",1234);

        //creating weeklyshift for the stokes:
        wscontroller.createnewweeklyshift(11,1999,12);

        wscontroller.createnewweeklyshift(11,1999,0);


        // all of the data is for week num 1 and year 1:

        //stokes will be available for day1, day 2 on day shift:
        wscontroller.addtoexistingweeklyshift(11,1999,12, WindowType.day1,555,2);
        wscontroller.addtoexistingweeklyshift(11,1999,12,WindowType.day1,666,2);

        wscontroller.addtoexistingweeklyshift(1,1,12, WindowType.day3,555,2);
        wscontroller.addtoexistingweeklyshift(1,1,12,WindowType.day3,666,2);

        //available for drivers:
        //for day 3: all of them
        wscontroller.addtoexistingweeklyshift(11,1999,0, WindowType.day3,111,7);
        wscontroller.addtoexistingweeklyshift(11,1999,0, WindowType.day3,222,7);
        wscontroller.addtoexistingweeklyshift(11,1999,0, WindowType.day3,333,7);
        wscontroller.addtoexistingweeklyshift(11,1999,0, WindowType.day3,444,7);

        // for day 1 just driver 1 and 2.
        wscontroller.addtoexistingweeklyshift(11,1999,0, WindowType.day1,111,7);
        wscontroller.addtoexistingweeklyshift(11,1999,0, WindowType.day1,222,7);
    }
}
