package packages.project.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import packages.project.Driver.Driver;
import packages.project.Vehicle.Vehicle;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c.customerId FROM Customer c WHERE c.login.loginId = :loginId")
    Integer getCustomerIdByLoginId( @Param("loginId")Integer loginId);
    @Query("SELECT c FROM Customer c WHERE c.vehicle = (SELECT d.vehicle FROM Driver d WHERE d.id = :driverId)")
    List<Customer> findCustomersByDriverId(@Param("driverId") Integer driverId);

    @Query("select C from Customer C where C.customerId=:customerId")
    Customer getCustomerByCustomerId(Integer customerId);

    @Query("select C.vehicle from Customer C where C.login.loginId = :loginId")
    Vehicle getVehicleByLoginId(Integer loginId);

    @Query("select c from Customer c where c.paidStatus=false")
    List<Customer> getCustomerByPendingFee();

    @Query("select C.login.loginId from Customer C where C.customerId = :customerId" )
    Integer getCustomerLoginId(int customerId);

}

