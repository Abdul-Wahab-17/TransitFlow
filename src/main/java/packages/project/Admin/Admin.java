package packages.project.Admin;

import lombok.Data;
import packages.project.Login.Login;

import javax.persistence.*;

@Entity
@Table
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "name")
    private int name;

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

    public Admin(int adminId, int name, int phone, String email, String address, Login login) {
        this.adminId = adminId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.login = login;
    }
}
