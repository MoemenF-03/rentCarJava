public class Client {
    private String clientId;
    private String firstName;
    private String lastName;
    private int phone;
    private String address;

    public Client(String clientId, String firstName, String lastName, int phone, String address) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters for each field
    public String getClientId() { return clientId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getPhone() { return phone; }
    public String getAddress() { return address; }

    public void setClientId(String clientId) { this.clientId = clientId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhone(int phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
}
