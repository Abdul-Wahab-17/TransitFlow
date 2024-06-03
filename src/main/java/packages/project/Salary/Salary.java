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

    @Column(name = "amount")
    int amount;



    @ManyToOne
    @JoinColumn(name = "areaId")
    Area area;

    public Salary() {
    }

    public Salary(int salaryId, int amount, Area area) {
        this.salaryId = salaryId;
        this.amount = amount;
        this.area = area;
    }

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
