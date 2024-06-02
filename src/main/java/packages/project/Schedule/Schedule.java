package packages.project.Schedule;

import lombok.Data;
import packages.project.Customer.Customer;

import javax.persistence.*;

@Data
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "monday_morning")
    private boolean mondayMorning;

    @Column(name = "monday_evening")
    private boolean mondayEvening;

    @Column(name = "tuesday_morning")
    private boolean tuesdayMorning;

    @Column(name = "tuesday_evening")
    private boolean tuesdayEvening;

    @Column(name = "wednesday_morning")
    private boolean wednesdayMorning;

    @Column(name = "wednesday_evening")
    private boolean wednesdayEvening;

    @Column(name = "thursday_morning")
    private boolean thursdayMorning;

    @Column(name = "thursday_evening")
    private boolean thursdayEvening;
    @Column(name = "friday_morning")
    private boolean fridayMorning;

    @Column(name = "friday_evening")
    private boolean fridayEvening;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    public Schedule(Integer id, boolean mondayMorning, boolean mondayEvening, boolean tuesdayMorning,
                    boolean tuesdayEvening, boolean wednesdayMorning, boolean wednesdayEvening,
                    boolean thursdayMorning, boolean thursdayEvening, boolean fridayMorning,
                    boolean fridayEvening, Customer customer) {
        this.id = id;
        this.mondayMorning = mondayMorning;
        this.mondayEvening = mondayEvening;
        this.tuesdayMorning = tuesdayMorning;
        this.tuesdayEvening = tuesdayEvening;
        this.wednesdayMorning = wednesdayMorning;
        this.wednesdayEvening = wednesdayEvening;
        this.thursdayMorning = thursdayMorning;
        this.thursdayEvening = thursdayEvening;
        this.fridayMorning = fridayMorning;
        this.fridayEvening = fridayEvening;
        this.customer = customer;
    }


    public Schedule() {
    }
}

