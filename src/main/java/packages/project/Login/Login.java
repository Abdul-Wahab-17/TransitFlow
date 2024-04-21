package packages.project.Login;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "login")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private int loginId;

    @Column(name = "pin")
    private int pin;

    @Column(name = "role")
    private String role;

    public Login() {
    }

    public Login(int pin, int loginId, String role) {
        this.pin = pin;
        this.loginId = loginId;
        this.role = role;
    }
}
