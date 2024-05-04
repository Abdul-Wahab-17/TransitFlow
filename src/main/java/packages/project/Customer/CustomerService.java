package packages.project.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import packages.project.Admin.Admin;

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

    public void save(Customer existingCustomer) {
        try {
            // Attempt to insert the customer record
            customerRepository.save(existingCustomer);
        } catch (DataIntegrityViolationException e) {
            // Handle the constraint violation exception
            // For example, provide a meaningful error message to the user
            logger.error("Failed to save customer record: " + e.getMessage());
            // Or rollback the transaction if necessary
            // transactionManager.rollback(transactionStatus);
        }

    }

    public List<Customer> getCustomersForDriver(Integer driverId) {
        return customerRepository.findCustomersByDriverId(driverId);
    }
}
