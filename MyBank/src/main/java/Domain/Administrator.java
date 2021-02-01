package Domain;

public class Administrator extends Entity {

    private String username;
    private String password;

    private String name;
    private String surname;
    private String cnp;
    private String idSeries;
    private int idNumber;
    private  Address address;

    private  DateTime dateTime;

    public Administrator(String username, String password, String name, String surname, String cnp, String idSeries, int idNumber, Address address, DateTime dateTime) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cnp = cnp;
        this.idSeries = idSeries;
        this.idNumber = idNumber;
        this.address = address;
        this.dateTime = dateTime;
    }

    public Administrator(int id, String username, String password, String name, String surname, String cnp, String idSeries, int idNumber, Address address, DateTime dateTime) {
        super(id);
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cnp = cnp;
        this.idSeries = idSeries;
        this.idNumber = idNumber;
        this.address = address;
        this.dateTime = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getIdSeries() {
        return idSeries;
    }

    public void setIdSeries(String idSeries) {
        this.idSeries = idSeries;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "id='" + super.getId() + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cnp=" + cnp +
                ", idSeries='" + idSeries + '\'' +
                ", idNumber=" + idNumber +
                ", address=" + address +
                ", dateTime=" + dateTime +
                '}';
    }
}
