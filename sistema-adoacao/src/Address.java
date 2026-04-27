public class Address {
    private String num;
    private String street;
    private String city;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "numero='" + num + '\'' +
                ", rua='" + street + '\'' +
                ", cidade='" + city + '\'' +
                '}';
    }
}
