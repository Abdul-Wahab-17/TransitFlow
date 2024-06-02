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
