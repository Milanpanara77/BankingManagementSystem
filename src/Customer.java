/**
 * Stores customer information such as name, age, phone number, and address.
 * Demonstrates encapsulation: fields are private and exposed via getters/setters.
 */
public class Customer {
    private String name;
    private int age;
    private String phoneNumber;
    private String address;

    public Customer(String name, int age, String phoneNumber, String address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Serializes this customer to a pipe-delimited line for file storage.
     * Format: name|age|phoneNumber|address
     */
    public String toFileString() {
        return name + "|" + age + "|" + phoneNumber + "|" + address;
    }

    public static Customer fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        String name = parts[0];
        int age = Integer.parseInt(parts[1]);
        String phone = parts[2];
        String address = parts[3];
        return new Customer(name, age, phone, address);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Phone: " + phoneNumber + ", Address: " + address;
    }
}
