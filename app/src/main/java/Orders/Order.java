package Orders;

public class Order {
    private int id;
    private String fullname;
    private String address;
    private String date;
    private String filling;
    private String confectionaryname;
    private String quantity;

    public Order(){

    }

    public Order(int id, String fullname, String address, String date, String filling, String confectionaryname, String quantity) {
        this.id = id;
        this.fullname = fullname;
        this.address = address;
        this.date = date;
        this.filling = filling;
        this.confectionaryname = confectionaryname;
        this.quantity = quantity;
    }

    public Order(String fullname, String address, String date, String filling, String confectionaryname, String quantity) {
        this.fullname = fullname;
        this.address = address;
        this.date = date;
        this.filling = filling;
        this.confectionaryname = confectionaryname;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilling() {
        return filling;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }

    public String getConfectionaryname() {
        return confectionaryname;
    }

    public void setConfectionaryname(String confectionaryname) {
        this.confectionaryname = confectionaryname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
