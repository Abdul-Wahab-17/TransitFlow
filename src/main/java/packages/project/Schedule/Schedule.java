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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isMondayMorning() {
        return mondayMorning;
    }

    public void setMondayMorning(boolean mondayMorning) {
        this.mondayMorning = mondayMorning;
    }

    public boolean isMondayEvening() {
        return mondayEvening;
    }

    public void setMondayEvening(boolean mondayEvening) {
        this.mondayEvening = mondayEvening;
    }

    public boolean isTuesdayMorning() {
        return tuesdayMorning;
    }

    public void setTuesdayMorning(boolean tuesdayMorning) {
        this.tuesdayMorning = tuesdayMorning;
    }

    public boolean isTuesdayEvening() {
        return tuesdayEvening;
    }

    public void setTuesdayEvening(boolean tuesdayEvening) {
        this.tuesdayEvening = tuesdayEvening;
    }

    public boolean isWednesdayMorning() {
        return wednesdayMorning;
    }

    public void setWednesdayMorning(boolean wednesdayMorning) {
        this.wednesdayMorning = wednesdayMorning;
    }

    public boolean isWednesdayEvening() {
        return wednesdayEvening;
    }

    public void setWednesdayEvening(boolean wednesdayEvening) {
        this.wednesdayEvening = wednesdayEvening;
    }

    public boolean isThursdayMorning() {
        return thursdayMorning;
    }

    public void setThursdayMorning(boolean thursdayMorning) {
        this.thursdayMorning = thursdayMorning;
    }

    public boolean isThursdayEvening() {
        return thursdayEvening;
    }

    public void setThursdayEvening(boolean thursdayEvening) {
        this.thursdayEvening = thursdayEvening;
    }

    public boolean isFridayMorning() {
        return fridayMorning;
    }

    public void setFridayMorning(boolean fridayMorning) {
        this.fridayMorning = fridayMorning;
    }

    public boolean isFridayEvening() {
        return fridayEvening;
    }

    public void setFridayEvening(boolean fridayEvening) {
        this.fridayEvening = fridayEvening;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

