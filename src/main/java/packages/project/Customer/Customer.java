package packages.project.Customer;

import lombok.Data;
import packages.project.Login.Login;

import javax.persistence.*;
import java.sql.Time;

@Data
@Entity
@Table
public class Customer {

    @Id
    int customerId;
    String customerName;
    String address;
    Time pick;
    Time drop;
    @ManyToOne
    @JoinColumn(name = "login_id")
    Login login;

    public Customer(int customerId, String customerName, String address, Time pick, Time drop , Login login) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.pick = pick;
        this.drop = drop;
        this.login=login;
    }

    public Customer() {
    }
}
