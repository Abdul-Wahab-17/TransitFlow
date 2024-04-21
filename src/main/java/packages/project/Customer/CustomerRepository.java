package packages.project.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import packages.project.Driver.Driver;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query ("SELECT c FROM Customer c WHERE c.customerId = :customerId")
    Customer getCustomerById(Long customerId);

    @Query("SELECT c FROM Customer c WHERE c.login.loginId = :loginId")
    Customer getCustomerByloginId(Integer loginId);


}
