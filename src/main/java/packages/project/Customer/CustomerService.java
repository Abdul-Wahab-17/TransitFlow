package packages.project.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packages.project.Admin.Admin;

import java.util.List;

@Service
public class CustomerService {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    public Customer getCustomer(Integer loginId){
        Customer customer = customerRepository.getCustomerByCustomerId(customerRepository.getCustomerIdByLoginId(loginId));
        return customer;
    }

    public void save(Customer existingCustomer) {
        customerRepository.save(existingCustomer);
    }

    public List<Customer> getCustomersForDriver(Integer driverId) {
        return customerRepository.findCustomersByDriverId(driverId);
    }
}
