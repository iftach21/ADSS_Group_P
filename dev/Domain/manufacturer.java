package Domain;

public class manufacturer {

    private String name;
    private String manufacturerID;

    public manufacturer(String name, String manufacturerID) {
        this.name = name;
        this.manufacturerID = manufacturerID;
    }

    public String getName() {
        return name;
    }

    public String getManufacturerID() {
        return manufacturerID;
    }

}
