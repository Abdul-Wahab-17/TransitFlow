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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "customer_id")
    int customerId;
    @Column (name = "customer_name")
    String customerName;

    @Column
    int phone_no;
    @Column
    String address;

    @Column (name = "pick_up")
    Time pick;

    @Column (name = "drop_time")
    Time drop;

    @Column
    String drop_loc;

    @Column
    String pick_loc;
    @ManyToOne
    @JoinColumn(name = "login_id")
    Login login;

    public Customer(int customerId, String customerName, int phone_no, String address, Time pick, Time drop, String drop_loc, String pick_loc, Login login) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phone_no = phone_no;
        this.address = address;
        this.pick = pick;
        this.drop = drop;
        this.drop_loc = drop_loc;
        this.pick_loc = pick_loc;
        this.login = login;
    }

    public Customer() {
    }
}
