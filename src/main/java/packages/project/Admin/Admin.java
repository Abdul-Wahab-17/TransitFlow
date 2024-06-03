package packages.project.Admin;

import lombok.Data;
import packages.project.Login.Login;

import javax.persistence.*;

@Entity
@Table
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "admin_id")
     int adminId;

    @Column(name = "name")
     String name;

    @Column(name = "phone")
    int phone;

    @Column(name = "email")
    String email;

    @Column(name = "address")
    String address;
    @ManyToOne
    @JoinColumn(name = "login_id")
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

    public Admin() {
    }

    public Admin(int adminId, String name, int phone, String email, String address, Login login) {
        this.adminId = adminId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.login = login;
    }
}
