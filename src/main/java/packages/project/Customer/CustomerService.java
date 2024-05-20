package packages.project.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import packages.project.Admin.Admin;
import packages.project.Vehicle.Vehicle;

import java.util.List;

@Service
public class CustomerService {

    CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    public Customer getCustomer(Integer loginId){
        Customer customer = customerRepository.getCustomerByCustomerId(customerRepository.getCustomerIdByLoginId(loginId));
        return customer;
    }


    public int getloginId(int customerId){
        return customerRepository.getCustomerLoginId(customerId);
    }

    public void save(Customer existingCustomer) {
        try {
            customerRepository.save(existingCustomer);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save customer record: " + e.getMessage());
        }

    }

    public List<Customer> getCustomersForDriver(Integer driverId) {
        return customerRepository.findCustomersByDriverId(driverId);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findCustomersWithPendingFee(){
        return customerRepository.getCustomerByPendingFee();
    }

    public Vehicle getVehicle(Integer loginId) {
        return customerRepository.getVehicleByLoginId(loginId);
    }
}
