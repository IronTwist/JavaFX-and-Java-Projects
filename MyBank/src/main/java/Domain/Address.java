package Domain;

public class Address {
    private String county;
    private String city;
    private String street;
    private int number;
    private String otherInfo;

    public Address(String county, String city, String street, int number, String otherInfo) {
        this.county = county;
        this.city = city;
        this.street = street;
        this.number = number;
        this.otherInfo = otherInfo;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    @Override
    public String toString() {
        return county + ',' +
                city + ',' +
                "str:" + street + ',' +
                "nr:" + number +
                "," + otherInfo;
    }
}
