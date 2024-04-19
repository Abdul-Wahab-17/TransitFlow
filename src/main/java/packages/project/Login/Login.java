package packages.project.Login;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "login") // Specify the table name if different from the default
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id") // Specify the column name if different from the field name
    private int loginId; // Use camelCase for field names

    @Column(name = "pin")
    private int pin;

    @Column(name = "c_id")
    private int cId;

    // Constructors, getters, and setters
    public Login() {
    }

    public Login(int pin, int loginId, int cId) {
        this.pin = pin;
        this.loginId = loginId;
        this.cId = cId;
    }
}
