package packages.project.Admin;

import packages.project.Login.Login;

public class AdminFormData {

    int adminId;
    String name;
    int phone;
    String email;
    String address;
    Login login;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public AdminFormData(int adminId, String name, int phone, String email, String address, Login login) {
        this.adminId = adminId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.login = login;
    }

    public AdminFormData() {
    }
}
