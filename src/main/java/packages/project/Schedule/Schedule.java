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

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "monday_morning", nullable = false)
    private boolean mondayMorning = false;

    @Column(name = "monday_evening", nullable = false)
    private boolean mondayEvening = false;

    @Column(name = "tuesday_morning", nullable = false)
    private boolean tuesdayMorning = false;

    @Column(name = "tuesday_evening", nullable = false)
    private boolean tuesdayEvening = false;

    @Column(name = "wednesday_morning", nullable = false)
    private boolean wednesdayMorning = false;

    @Column(name = "wednesday_evening", nullable = false)
    private boolean wednesdayEvening = false;

    @Column(name = "thursday_morning", nullable = false)
    private boolean thursdayMorning = false;

    @Column(name = "thursday_evening", nullable = false)
    private boolean thursdayEvening = false;

    @Column(name = "friday_morning", nullable = false)
    private boolean fridayMorning = false;

    @Column(name = "friday_evening", nullable = false)
    private boolean fridayEvening = false;

    @OneToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;


    public Schedule(int id, int customerId, boolean mondayMorning, boolean mondayEvening, boolean tuesdayMorning,
                    boolean tuesdayEvening, boolean wednesdayMorning, boolean wednesdayEvening,
                    boolean thursdayMorning, boolean thursdayEvening, boolean fridayMorning,
                    boolean fridayEvening, Customer customer) {
        this.id = id;
        this.customerId = customerId;
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

