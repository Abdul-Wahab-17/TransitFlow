package packages.project.Salary;

import lombok.Data;
import packages.project.Area.Area;

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

    @ManyToOne
    @JoinColumn(name = "areaId")
    Area area;

    public Salary() {
    }

    public Salary(int salaryId, int amount, Date dueDate, Area area) {
        this.salaryId = salaryId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.area = area;
    }
}
