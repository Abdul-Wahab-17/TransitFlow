package packages.project.Area;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="area_id")
    int areaId;

    @Column(name = "area_name")
    String areaName;

    @Column(name="distance_from_nust")
    int distanceFromNust;

    @Column(name="customer_population")
    Integer customerPopulation;

    public Area() {
    }

    public Area(int areaId, String areaName, int distanceFromNust, int customerPopulation) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.distanceFromNust = distanceFromNust;
        this.customerPopulation = customerPopulation;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getDistanceFromNust() {
        return distanceFromNust;
    }

    public void setDistanceFromNust(int distanceFromNust) {
        this.distanceFromNust = distanceFromNust;
    }

    public Integer getCustomerPopulation() {
        return customerPopulation;
    }

    public void setCustomerPopulation(Integer customerPopulation) {
        this.customerPopulation = customerPopulation;
    }
}
