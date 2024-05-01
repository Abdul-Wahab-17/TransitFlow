package packages.project.Salary;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    int salaryId;

    int amount;

    @Column(name="due_date")
    Date dueDate;

    @Column(name = "paid_status")
    boolean paidStatus;

    public Salary() {
    }

    public Salary(int salaryId, int amount, Date dueDate, boolean paidStatus) {
        this.salaryId = salaryId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paidStatus = paidStatus;
    }
}
