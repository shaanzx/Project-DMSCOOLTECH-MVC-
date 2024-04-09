package lk.ijse.dmscooltech.model.tm;

public class CustomerTm {
    private String customerId;
    private String customerName;
    private String address;
    private String contact;


    public CustomerTm() {}

    public CustomerTm(String customerId, String customerName, String address, String contact) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contact = contact;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "CustomerTm{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}

