package packages.project.Customer;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    public Customer(int customerId, String customerName, String address, Time pick, Time drop) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.pick = pick;
        this.drop = drop;
    }

    public Customer() {
    }
}
