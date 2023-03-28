public class Contact_Person {
    private String name;
    private String Phone_number;

    public Contact_Person(String name,String phone_number) {
        this.name = name;
        this.Phone_number=phone_number;    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }
}
