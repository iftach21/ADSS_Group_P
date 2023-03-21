abstract class Workers {
    private int id;
    private String name;
    private String contract;
    private String start_date;
    private WindowType[] available;
    private int wage;
    private int phoneNUM;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContract() {
        return contract;
    }

    public String getStart_date() {
        return start_date;
    }

    public WindowType[] getAvailable() {
        return available;
    }

    public int getWage() {
        return wage;
    }

    public int getPhoneNUM() {
        return phoneNUM;
    }

    @Override
    public String toString() {
        //todo: string it later
        return "";
    }

    public Workers(int id, String name, String contract, String start_date, WindowType[] available, int wage, int phoneNUM) {
        this.id = id;
        this.name = name;
        this.contract = contract;
        this.start_date = start_date;
        this.available = available;
        this.wage = wage;
        this.phoneNUM = phoneNUM;
    }
}
