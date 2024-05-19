package packages.project.Maintainence;

import lombok.Data;
import packages.project.Vehicle.Vehicle;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "maintainance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    Integer id;

    @Column(name="place")
    String place;

    @ManyToOne
    @JoinTable( name = "vehicleId")
    Vehicle vehicle;

    @Column(name = "due_date")
    Date dueDate;

    @Column(name= "schedule_date")
    Date scheduleDate;

    public Maintenance(Integer id, String place, Vehicle vehicle, Date dueDate, Date scheduleDate) {
        this.id = id;
        this.place = place;
        this.vehicle = vehicle;
        this.dueDate = dueDate;
        this.scheduleDate = scheduleDate;
    }

    public Maintenance() {
    }
}
